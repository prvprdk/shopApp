package com.example.shop.service;

import com.example.shop.domain.*;
import com.example.shop.dto.EventType;
import com.example.shop.dto.MetaDto;
import com.example.shop.dto.ObjectType;
import com.example.shop.dto.ProductPageDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserSubscriptionRepo;
import com.example.shop.util.WsSender;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ProductService {

    private static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static final String IMG_PATTERN = "\\.(jpeg|jpg|gif|png)$";
    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMG_REGEX = Pattern.compile(IMG_PATTERN, Pattern.CASE_INSENSITIVE);
    private final UserSubscriptionRepo userSubscriptionRepo;

    private final CustomUserService customUserService;
    private final ProductRepository productRepository;
    private final BiConsumer<EventType, Product> wsSender;

    @Autowired
    public ProductService(UserSubscriptionRepo userSubscriptionRepo,
                          CustomUserService customUserService,
                          ProductRepository productRepository,
                          WsSender wsSender) {
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.customUserService = customUserService;
        this.productRepository = productRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullProduct.class);
    }

    public Product create(Product product,
                          OidcUser oidcUser) throws IOException {

        product.setLocalDateTime(LocalDateTime.now());
        product.setAuthor(customUserService.convertedUser(oidcUser));
        fillMeta(product);
        wsSender.accept(EventType.CREATE, product);
        return productRepository.save(product);
    }

    public ProductPageDto findForUser(Pageable pageable,
                                      User userFromDb) {
        List<User> channels = new java.util.ArrayList<>(userSubscriptionRepo.findBySubscriber(userFromDb)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .toList());
        channels.add(userFromDb);
        Page<Product> page = productRepository.findByAuthorIn(channels, pageable);
        return new ProductPageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    public Product update(Product product,
                          Product productFromDb) throws IOException {

        productFromDb.setName(product.getName());
        fillMeta(productFromDb);
        Product updatedProduct = productRepository.save(productFromDb);
        wsSender.accept(EventType.UPDATE, updatedProduct);
        return updatedProduct;
    }

    public void delete(Product product) {
        productRepository.delete(product);
        wsSender.accept(EventType.REMOVE, product);

    }

    private void fillMeta(Product product) throws IOException {
        String name = product.getName();
        Matcher matcher = URL_REGEX.matcher(name);
        if (matcher.find()) {
            String url = name.substring(matcher.start(), matcher.end());
            matcher = IMG_REGEX.matcher(url);
            product.setLink(url);

            if (matcher.find()) {
                product.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);
                product.setLinkCover(meta.getTitle());
                product.setLinkTitle(meta.getDescription());
                product.setLinkDescription(meta.getCover());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements title = doc.select("meta[name$=title], meta[property$=title]"); // a with href
        Elements description = doc.select("meta[name$=description], meta[property$=title]");
        Elements cover = doc.select("meta[name$=image], meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

}

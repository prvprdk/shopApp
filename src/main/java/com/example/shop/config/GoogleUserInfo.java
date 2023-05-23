package com.example.shop.config;



import java.util.Map;

public class GoogleUserInfo {

    private final Map <String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId (){
        return (String) attributes.get("sub");
    }

    public String getEmail(){
        return (String) attributes.get("email");
    }
    public String getName(){
        return (String) attributes.get("name");
    }
    public String getPicture(){
        return (String) attributes.get("picture");
    }
    public String getLocale(){
        return (String) attributes.get("locale");
    }

}

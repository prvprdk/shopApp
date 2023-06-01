<template>
<v-container class="pa-1">
           <product-form :productAttr="product" />

 <v-layout  align-space-around justify-start column>

              <product-row v-for="product in sortedProducts"
                                  :key="product.id"
                                  :product="product"
                                  :editProduct="editProduct"
                                   />
                                   <lazy-loader></lazy-loader>

  </v-layout>

</v-container>
</template>

<script>
import LazyLoader from 'components/LazyLoader.vue'
import ProductRow from 'components/products/ProductRow.vue'
import ProductForm from 'components/products/ProductForm.vue'
import productsApi from 'api/products'
import { mapGetters } from 'vuex'

export default {
        components: {
            ProductRow,
            ProductForm,
            LazyLoader
        },
        data (){
                  return{
                  product: null
            }
        },
        computed:  mapGetters ([ 'sortedProducts' ]),

          methods: {
                  editProduct(product){
                      this.product = product
             },
          }
    }

</script>

<style>
</style>
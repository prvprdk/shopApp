<template>
<v-container>
  <v-layout  column justify-start align-space-around>
              <product-form :productAttr="product" />
              <product-row v-for="product in sortedProducts"
                                  :key="product.id"
                                  :product="product"
                                  :editProduct="editProduct"
                                   />
  </v-layout>
</v-container>
</template>

<script>
import ProductRow from 'components/products/ProductRow.vue'
import ProductForm from 'components/products/ProductForm.vue'
import productsApi from 'api/products'
import { mapGetters } from 'vuex'

export default {
        components: {
            ProductRow,
            ProductForm
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
<template>

 <v-layout>
              <v-card-text class="my-2" primary-title><i>({{ product.id }}) </i> {{ product.name }}
                <media v-if="product.link" :product="product"> </media>
              </v-card-text>

              <v-card-actions>
                        <v-btn icon  @click="edit"  x-small >
                                  <v-icon>edit</v-icon>
                        </v-btn>
                        <v-btn icon @click="del"  x-small>
                                   <v-icon>clear</v-icon>
                        </v-btn>
              </v-card-actions>
 </v-layout >
</template>
<script>
 import {mapActions} from 'vuex'
 import Media from 'components/media/Media.vue'
 export default {
 props: ['product', 'editProduct'],
 components: {Media},

 methods: {
             ...mapActions(['removeProductAction']),
             edit() {
             this.editProduct(this.product)
             },
             del() {
             this.removeProductAction(this.product)
             }
         }
    }
</script>


<style>


</style>


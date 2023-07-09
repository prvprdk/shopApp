<template>

 <v-card class="my-5" >
              <v-card-text class="my-2" primary-title>
                    <user-link
                            :user="product.author"
                            :size="48"
                        ></user-link>
                   <v-card-title> {{ product.name }}</v-card-title>
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
              <comment-list
                    :comments = "product.comments"
                    :product-id = "product.id"
              ></comment-list>
 </v-card>
</template>
<script>
     import {mapActions} from 'vuex'
     import Media from 'components/media/Media.vue'
     import CommentList from '../comment/CommentList.vue'
     import UserLink from 'components/UserLink.vue'

     export default {
     props: ['product', 'editProduct'],
     components: { CommentList, Media, UserLink },


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


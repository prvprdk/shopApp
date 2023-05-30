<template>
          <v-layout row class="px-3">
              <v-text-field
                   label="Add comment"
                   placeholder="name product"
                   v-model="text"
                   @keyup.enter="save"
               />
              <v-btn class="mx-2" fab dark color="yellow" variant="plain"  x-small @click="save">
                            <v-icon dark> mdi-plus</v-icon>
              </v-btn></v-btn>
           </v-layout>
</template>


<script>
import { mapActions } from 'vuex'
export default {
        name: 'CommentForm',
        props: ['productId'],
        data () {
          return {
            text: ''
          }
        },
        methods: {
                ...mapActions(['addCommentAction']),
                async save () {
                   await this.addCommentAction({
                        text: this.text,
                        product: {
                                id: this.productId
                            }
                   })
                    this.text = ''
                }
            },
    }
</script>


<style scoped>
</style>
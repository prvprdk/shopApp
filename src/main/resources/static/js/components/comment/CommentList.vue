<template>

        <v-list three-line>
            <v-list-item-subtitle>
                Comment
            </v-list-item-subtitle>

                 <template  v-for="(item, index) in comments" >
                         <v-divider v-if= "index > 0"
                                      :key = "index" >
                         </v-divider>
                         <comment-item :comment= "item"
                                       :key = "'item' + index" >

                         </comment-item>

                 </template>
             <comment-form :product-id="productId"></comment-form>
        </v-list>

</template>

<script>
import CommentItem from './CommentItem.vue'
import CommentForm from './CommentForm.vue'

export default {
        name: 'CommentList',
        props: ['comments', 'productId'],
        components: { CommentItem ,  CommentForm }

        }

</script>


<style scoped>
</style>
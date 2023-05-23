<template>
    <v-card>
        <v-flex v-if="type === 'href'" xs12 sm6 offset-sm3>
            <v-img  v-if="product.linkCover" :src="product.linkCover" aspect-ratio="2.75"></v-img>
               <v-card-title>
                    <div>
                    <h3> <a :href="product.link">{{product.linkTitle || product.link}}></a> </h3>
                    <div v-if="product.linkDescription"> {{product.linkDescription}} </div>
                    </div>
               </v-card-title>
         </v-flex>
         <v-flex v-if="type === 'image' "  xs12 sm6 offset-sm3>
             <a :href="product.link">
                 <v-img v-if="product.linkCover" :src="product.linkCover" aspect-ratio="2.75"></v-img>
                 {{ product.link}}
             </a>
        </v-flex>

         <v-flex v-if="type === 'youtube' "  xs12 sm6 offset-sm3>
                <you-tube :src="product.link"></you-tube>
        </v-flex>
    </v-card>
</template>


<script>
import YouTube from './Youtube.vue'
export default {
    name: 'Media',
    props: ['product'],
    components: {YouTube},
    data () {
        return {
        type: 'href'
            }
        },
    beforeMount(){
            if (this.product.link.indexOf('youtu') > -1){
                    this.type = 'youtube'
               }else if (this.product.link.match(/\.(jpeg|jpg|gif|png)$/) !== null){
                this.type = 'image'
               }else {
                this.type = 'href'
               }
        },

}
</script>


<style>
</style>
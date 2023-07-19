<template>
          <v-layout >
              <v-text-field label="name product"
               placeholder="name product"
               v-model="name"
               @keyup.enter="save"
               />
              <v-btn @click="save"> save </v-btn>
           </v-layout>
</template>


<script>
import {mapActions} from 'vuex'
export default {
    props: [ 'productAttr'],
    data() {
        return {
           id: null,
           name:  ''
        }
    },
    watch: {
        productAttr: function(newVal, oldVal){
        this.name = newVal.name;
        this.id = newVal.id;
        }
    },
    methods: {
          ...mapActions(['updateProductAction', 'addProductAction']),
        save() {
               const product = {
                   id: this.id,
                   name: this.name
               }

               if (this.id) {
               this.updateProductAction(product)
               }else {
               this.addProductAction(product)
            }
             this.name = ''
             this.id = null
            }
        }
    }
</script>


<style>
</style>
<template>

  <v-app>
    <v-app-bar app color="orange" elevation="5" flat>
            <v-toolbar-title> Shop </v-toolbar-title>
            <v-btn flat v-if="profile"
                :disabled = "$route.path === '/'"
                    @click="showProducts">
                Products
             </v-btn>
            <v-spacer></v-spacer>
            <v-btn v-if="profile" flat
                :disabled="$route.path === '/user'"
                 @click="showProfile">
                    {{profile.name}}
             </v-btn>
            <span v-if="profile">  {{profile.name}}</span>
            <v-btn icon v-if="profile" href="/logout" >
                 <v-icon>exit_to_app</v-icon>
            </v-btn>
    </v-app-bar>
            <v-main>
                 <router-view> </router-view>
             </v-main>
 </v-app>

</template>

<script>
        import { mapState, mapMutations } from 'vuex'
        import { addHandler } from 'util/ws'


        export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations ([ 'addProductMutation',
                               'updateProductMutation',
                               'removeProductMutation',
                               'addCommentMutation' ]),
            showProducts () {
                this.$router.push('/')
                },
            showProfile () {
                this.$router.push('/user')
                }
        },
        created () {
                addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType){
                        case 'CREATE':
                        this.addProductMutation(data.body)
                        break
                        case 'UPDATE':
                        this.updateProductMutation(data.body)
                        case 'REMOVE':
                        this.removeProductMutation(data.body)
                            break
                        default:
                            console.error ('Looks like the event type if unknown "${data.objectType}"')
                            }
                    } else if (data.objectType === 'COMMENT') {
                    switch (data.eventType){
                         case 'CREATE':
                         this.addCommentMutation(data.body)
                            break
                         default:
                            console.error ('Looks like the event type if unknown "${data.objectType}"')
                            }
                    } else{
                        console.error ('Looks like the object type if unknown "${data.objectType}"')
                    }
                })
          },

          beforeMount () {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
          }
        }


</script>

<style>
</style>
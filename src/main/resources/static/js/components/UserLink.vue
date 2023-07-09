<template>
                        <router-link
                         :to="`/user/${user.id}`"
                         class="px-3"
                        >
                                  <v-avatar
                                v-if = "user && user.picture"
                                 :size = "`${size || 36}px`"
                          >
                                <img
                                   :src="user.picture"
                                   :alt="user.name" >
                          </v-avatar>
                          <v-avatar
                                    v-else
                                    color="yellow"
                                         :size = "`${size || 36}px`"
                          >
                            <v-icon dark> account_circle</v-icon>
                          </v-avatar>
                          {{userName}}

                          </router-link>
</template>
<script>
export default {
        name : 'UserLink',
        props: ['user', 'size'],
        computed: {
            userName() {
             return this.user ? this.user.name : 'unknown'
        }
     },
    }

</script>


<style>
</style>


<template>
    <v-container class="pa-1">
         <v-layout justify-start column>
          <v-list-item>
               <v-list-item-title v-for="item in subscriptions"   :key="item">
                   <user-link
                           :user="item.subscriber"
                   ></user-link>
                   <v-btn @click = "changeSubscriptionsStatus(item.subscriber.id)">
                        {{item.active ? "Dismiss" : "Approve"}}
                   </v-btn>
                </v-list-item-title>
          </v-list-item>
         </v-layout>
    </v-container>
</template>

<script>
import UserLink from 'components/UserLink.vue'
import profileApi from 'api/profile'
    export default {
        components: { UserLink },
        name: 'Subscriptions',
        data (){
            return {
                    subscriptions: []
                }
        },
        methods: {
            async changeSubscriptionsStatus(subscriberId) {
                await profileApi.changeSubscriptionsStatus(subscriberId)
                this.subscriptions.findIndex (item => item.subscriber.id === subscriberId)
                const subscription = this.subscriptions[subscriptionIndex]
                this.subscriptions = [
                    ...this.subscriptions.slice (0, subscriptionIndex),
                    {
                        ...subscription,
                        active: !subscription.active
                    },
                    ...this.subscriptions.slice(subscriptionIndex + 1)
                ]
            }
        },
        async beforeMount (){
            const resp = await profileApi.subscriberList(this.$store.state.profile.id)
            this.subscriptions = await resp.json()
        }
}
</script>

<style scoped>

</style>
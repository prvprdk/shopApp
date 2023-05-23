import Vue from 'vue'
import VueRouter from 'vue-router'
import ProductsList from 'pages/ProductList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'

Vue.use (VueRouter)

const routes = [
    { path: '/', component: ProductsList},
    { path: '/auth', component: Auth},
     { path: '/profile', component: Profile},
     { path: '*', component: ProductsList}
]

export default new VueRouter({
    mode: 'history',
    routes
    })
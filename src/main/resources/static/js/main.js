import Vue from 'vue'
import vuetify from 'plugins/vuetify'
import 'api/resource'
import '@babel/polyfill'
import App from 'pages/App.vue'
import store from 'store/store'
import {connect} from 'util/ws'
import router from 'router/router'

if (frontendData.profile){
    connect()
}

new Vue ({
el: '#app',
store,
vuetify,
router,
render: a => a(App)
})



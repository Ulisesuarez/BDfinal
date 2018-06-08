import Vue from 'vue'
import Router from 'vue-router'
import MainView from '../components/MainView'
import Wrap from '../wrap.vue'
import Vuetify from 'vuetify'

export const EventBus = new Vue()

Vue.use(Vuetify)
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/verified/',
      name: 'MainView',
      component: MainView,
      props: true
    },
    {
      path: '/',
      name: 'wrap',
      component: Wrap
    }
  ]
})

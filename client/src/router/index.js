import Vue from 'vue'
import Router from 'vue-router'
import Landing from '@/components/Landing'
import Analyses from '@/components/Analyses'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Landing',
      component: Landing
    },
    {
      path: '/analyses',
      name: 'Analyses',
      component: Analyses
    }
  ]
})

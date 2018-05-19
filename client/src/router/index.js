import Vue from 'vue'
import Router from 'vue-router'
import Landing from '../pages/Landing'
import Analyses from '../pages/Analyses'
import DetailAnalyse from '../pages/DetailAnalyse'
import BaseSignUpForm from '../components/TheSignUpForm'
import BaseSignInForm from '../components/TheSignInForm'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Landing',
      component: Landing,
      meta: {auth: undefined}
    },
    {
      path: '/analyses',
      name: 'Analyses',
      component: Analyses,
      meta: {auth: ['ROLE_USER']}
    },
    {
      path: '/analyses/:id',
      name: 'DetailAnalyse',
      component: DetailAnalyse,
      meta: {auth: ['ROLE_USER']}
    },
    {
      path: '/user',
      name: 'User',
      children: [
        {
          path: '/user/sign-up',
          name: 'SignUp',
          component: BaseSignUpForm,
          meta: {auth: false}
        },
        {
          path: '/user/sign-in',
          name: 'SignIn',
          component: BaseSignInForm,
          meta: {auth: false}
        }
      ]
    }
  ]
})

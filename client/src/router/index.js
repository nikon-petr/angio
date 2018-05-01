import Vue from 'vue'
import Router from 'vue-router'
import Landing from '@/components/Landing'
import Analyses from '@/components/Analyses'
import DetailAnalyse from '@/components/DetailAnalyse'
import BaseSignUpForm from '@/containers/BaseSignUpForm'
import BaseSignInForm from '@/containers/BaseSignInForm'

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
    },
    {
      path: '/analyses/1',
      name: 'DetailAnalyse',
      component: DetailAnalyse
    },
    {
      path: '/user',
      name: 'User',
      children: [
        {
          path: '/user/sign-up',
          name: 'SignUp',
          component: BaseSignUpForm,
          meta: {
            showSignUpForm: true,
            showSignInForm: false
          }
        },
        {
          path: '/user/sign-in',
          name: 'SignIn',
          component: BaseSignInForm,
          meta: {
            showSignUpForm: false,
            showSignInForm: true
          }
        }
      ]
    }
  ]
})

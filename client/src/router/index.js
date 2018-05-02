import Vue from 'vue'
import Router from 'vue-router'
import Landing from '../components/Landing'
import Analyses from '../components/Analyses'
import DetailAnalyse from '../components/DetailAnalyse'
import BaseSignUpForm from '../containers/BaseSignUpForm'
import BaseSignInForm from '../containers/BaseSignInForm'

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
      path: '/analyses/1',
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
          meta: {
            auth: false
          }
        },
        {
          path: '/user/sign-in',
          name: 'SignIn',
          component: BaseSignInForm,
          meta: {
            auth: false
          }
        }
      ]
    }
  ]
})

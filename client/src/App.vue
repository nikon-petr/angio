<template>
  <v-app id="inspire" dark>
    <BaseNavigation ref="drawer"></BaseNavigation>
    <BaseHeader @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer"></BaseHeader>
    <TheContent></TheContent>
    <TheFooter></TheFooter>
    <BaseSignUpForm></BaseSignUpForm>
    <BaseSignInForm></BaseSignInForm>
    <BaseAlert></BaseAlert>
  </v-app>
</template>

<script>
import BaseHeader from './containers/BaseHeader'
import BaseNavigation from './containers/BaseNavigation'
import TheContent from './containers/TheContent'
import TheFooter from './containers/TheFooter'
import BaseAlert from './containers/BaseAlert'
import BaseSignUpForm from './containers/BaseSignUpForm'
import BaseSignInForm from './containers/BaseSignInForm'

export default {
  components: {BaseSignInForm, BaseSignUpForm, BaseAlert, TheFooter, TheContent, BaseNavigation, BaseHeader}
}
</script>

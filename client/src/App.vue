<template>
  <v-app id="inspire" dark>
    <TheDrawer ref="drawer"></TheDrawer>
    <TheHeader @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer"></TheHeader>
    <TheContent></TheContent>
    <TheFooter></TheFooter>
    <TheSignUpForm></TheSignUpForm>
    <TheSignInForm></TheSignInForm>
    <TheAlert></TheAlert>
  </v-app>
</template>

<script>
import TheHeader from './components/TheHeader'
import TheDrawer from './components/TheDrawer'
import TheContent from './components/TheContent'
import TheFooter from './components/TheFooter'
import TheAlert from './components/TheAlert'
import TheSignUpForm from './components/TheSignUpForm'
import TheSignInForm from './components/TheSignInForm'

export default {
  components: {TheSignInForm, TheSignUpForm, TheAlert, TheFooter, TheContent, TheDrawer, TheHeader}
}
</script>

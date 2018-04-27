<template>
  <v-app id="inspire" dark>
    <BaseNavigation ref="drawer"></BaseNavigation>
    <BaseHeader @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer"></BaseHeader>
    <TheContent></TheContent>
    <TheFooter></TheFooter>
  </v-app>
</template>

<script>
import BaseHeader from './containers/BaseHeader'
import BaseNavigation from './containers/BaseNavigation'
import TheContent from './containers/TheContent'
import TheFooter from './containers/TheFooter'
export default {
  components: {TheFooter, TheContent, BaseNavigation, BaseHeader}
}
</script>

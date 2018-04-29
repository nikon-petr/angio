<template>
  <v-toolbar app fixed clipped-left>
    <v-toolbar-side-icon @click.stop="$emit('toggle-drawer')"></v-toolbar-side-icon>
    <v-toolbar-title>AngioVision</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn @click="$root.$emit('showSignIn')" flat large color="white">Вход</v-btn>
    <v-btn @click="$root.$emit('showSignUp')" flat large color="white">Регистрация</v-btn>
  </v-toolbar>
</template>

<script>
import BaseSignUpForm from './BaseSignUpForm'
export default {
  name: 'BaseHeader',
  components: {BaseSignUpForm}
}
</script>

<style scoped>

</style>

<template>
  <v-toolbar app fixed clipped-left>
    <v-toolbar-side-icon @click.stop="$emit('toggle-drawer')"></v-toolbar-side-icon>
    <v-toolbar-title>AngioVision</v-toolbar-title>
    <v-spacer></v-spacer>
    <div v-show="$auth.check()">
      <v-btn @click="logout()" flat large color="white">Выход</v-btn>
    </div>
    <div v-show="!$auth.check()">
      <v-btn @click="$root.$emit('showSignIn')" flat large color="white">Вход</v-btn>
      <v-btn @click="$root.$emit('showSignUp')" flat large color="white">Регистрация</v-btn>
    </div>
  </v-toolbar>
</template>

<script>
import BaseSignUpForm from './BaseSignUpForm'

export default {
  name: 'BaseHeader',
  components: {BaseSignUpForm},
  methods: {
    logout () {
      this.$auth.logout({
        makeRequest: true,
        success () {
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Вы вышли',
              timeout: 5000
            })
        },
        error () {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Произошла нерпедвиденная ошибка',
              timeout: 5000
            })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

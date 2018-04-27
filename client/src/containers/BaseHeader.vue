<template>
  <v-toolbar app fixed clipped-left>
    <v-toolbar-side-icon @click.stop="$emit('toggle-drawer')"></v-toolbar-side-icon>
    <v-toolbar-title>AngioVision</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn flat large color="white">Вход</v-btn>
    <v-btn flat large color="white">Регистрация</v-btn>
  </v-toolbar>
</template>

<script>
export default {
  name: 'BaseHeader'
}
</script>

<style scoped>

</style>

<template>
  <v-snackbar
    :timeout="timeout"
    :color="color"
    v-model="show"
    bottom
  >
    {{ message }}
    <v-btn dark flat @click.native="show = false">Закрыть</v-btn>
  </v-snackbar>
</template>

<script>
export default {
  name: 'TheAlert',
  data () {
    return {
      color: undefined,
      message: '',
      timeout: 10000,
      show: false
    }
  },
  mounted () {
    this.$root.$on('showAlert', (alertData) => {
      this.color = alertData.color
      this.message = alertData.message
      this.timeout = alertData.timeout
      this.show = true
    })
  }
}
</script>

<style scoped>

</style>

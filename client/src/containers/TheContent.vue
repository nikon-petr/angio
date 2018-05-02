<template>
  <v-content>
    <v-container fluid fill-height>
      <v-layout align-center>
          <router-view v-if="$auth.ready()">

          </router-view>
        <div v-if="!$auth.ready()">
          Loading ...
        </div>
      </v-layout>
    </v-container>
  </v-content>
</template>

<script>
export default {
  name: 'TheContent'
}
</script>

<style scoped>

</style>

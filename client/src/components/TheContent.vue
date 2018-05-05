<template>
  <v-content>
    <v-container fluid fill-height>
      <v-layout align-center>
          <router-view v-if="$auth.ready()"></router-view>

          <v-layout v-if="!$auth.ready()" row wrap align-center>
            <v-flex class="text-xs-center">
              <v-progress-circular  indeterminate color="primary"></v-progress-circular>
            </v-flex>
          </v-layout>
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

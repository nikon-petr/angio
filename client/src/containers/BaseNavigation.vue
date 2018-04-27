<template>
  <v-navigation-drawer
    temporary
    clipped
    fixed
    v-model="drawer"
    app
  >
    <v-list dense>
      <v-list-tile :to="{path: '/'}">
        <v-list-tile-action>
          <v-icon>home</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Главная</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
      <v-list-tile :to="{path: '/analyses'}">
        <v-list-tile-action>
          <v-icon>list</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Список анализов</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
      <v-list-tile>
        <v-list-tile-action>
          <v-icon>info_outline</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>О приложении</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: 'BaseNavigation',
  data: () => ({
    drawer: false
  })
}
</script>

<style scoped>

</style>

<template>
  <v-navigation-drawer
    temporary
    clipped
    fixed
    v-model="drawer"
    app
  >
    <v-list dense>
      <v-list-tile avatar v-show="$auth.check()">
        <v-list-tile-avatar size="25">
          <v-icon size="50">account_circle</v-icon>
        </v-list-tile-avatar>
        <v-list-tile-content>
          <v-list-tile-title>{{ $auth.user().lastName }} {{ $auth.user().firstName }}</v-list-tile-title>
          <v-list-tile-sub-title>{{ $auth.user().email }}</v-list-tile-sub-title>
        </v-list-tile-content>
        <!--<v-spacer></v-spacer>-->
      </v-list-tile>
      <!--<v-divider v-show="$auth.check()"></v-divider>-->

      <v-list-tile :to="{path: '/'}">
        <v-list-tile-action>
          <v-icon>home</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Главная</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
      <v-list-tile :to="{path: '/analyses'}" v-show="$auth.check('ROLE_USER')">
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

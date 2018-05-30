<template>
  <v-navigation-drawer
    temporary
    clipped
    fixed
    v-model="drawer"
    app
  >
    <v-list dense>
      <v-list-tile avatar :to="{path: '/user/me'}" exact v-show="$auth.check()">
        <v-list-tile-avatar size="25">
          <v-icon size="50">account_circle</v-icon>
        </v-list-tile-avatar>
        <v-list-tile-content>
          <v-list-tile-title>{{ $auth.user().lastName }} {{ $auth.user().firstName }}</v-list-tile-title>
          <v-list-tile-sub-title>{{ $auth.user().email }}</v-list-tile-sub-title>
        </v-list-tile-content>
      </v-list-tile>

      <v-list-tile :to="{path: '/'}" exact>
        <v-list-tile-action>
          <v-icon>home</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Главная</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
      <v-list-tile :to="{path: '/analyses'}" exact v-show="$auth.check('ROLE_USER')">
        <v-list-tile-action>
          <v-icon>list</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Список анализов</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
      <v-list-tile :to="{path: '/user/list'}" exact v-show="$auth.check('ROLE_ADMIN')">
        <v-list-tile-action>
          <v-icon>list</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Список пользователей</v-list-tile-title>
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
  name: 'TheDrawer',
  data: () => ({
    drawer: false
  })
}
</script>

<style scoped>

</style>

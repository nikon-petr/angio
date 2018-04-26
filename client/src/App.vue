<template>
  <v-app id="inspire" dark>
    <v-navigation-drawer
      temporary
      clipped
      fixed
      v-model="drawer"
      app
    >
      <v-list dense>
        <v-list-tile :to="{name: 'Landing'}" @click="">
          <v-list-tile-action>
            <v-icon>home</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>Главная</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-list-tile :to="{name: 'Analyses'}" @click="">
          <v-list-tile-action>
            <v-icon>list</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>Список анализов</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-list-tile @click="">
          <v-list-tile-action>
            <v-icon>info_outline</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>О приложении</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar app fixed clipped-left>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title>AngioVision</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn flat large color="white">Вход</v-btn>
      <v-btn flat large color="white">Регистрация</v-btn>
    </v-toolbar>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center>
          <router-view>

          </router-view>
        </v-layout>
      </v-container>
    </v-content>
    <v-footer app class="text-xs-center">
      <span style="width: 100%;text-align: center">&copy; 2017</span>
    </v-footer>
  </v-app>
</template>

<script>
  export default {
    data: () => ({
      drawer: false
    })
  }
</script>

<template>
  <v-jumbotron color="dark lighten-2">
    <v-container fill-height>
      <v-layout align-center>
        <v-flex>
          <h3 class="display-3">Добро пожаловать!</h3>
          <span class="subheading">AngioVision предназначен для компьютерного анализа сосудистой системы и степени кровоснабжения сетчатки глаза.</span>
          <v-divider class="my-3"></v-divider>
          <div class="title mb-3" v-if="!$auth.check()">Для начала работы войдите под своей учётной записью</div>
          <div class="title mb-3" v-if="$auth.check()">Для начала работы перейдите к списку анализов</div>
          <v-btn v-if="!$auth.check()" large color="primary" class="mx-0" @click="$root.$emit('showSignIn')">Начать</v-btn>
          <v-btn v-if="$auth.check()" large color="primary" class="mx-0" :to="{path: '/analyses'}">Список анализов</v-btn>
        </v-flex>
      </v-layout>
    </v-container>
  </v-jumbotron>
</template>

<script>
export default {
  name: 'landing'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

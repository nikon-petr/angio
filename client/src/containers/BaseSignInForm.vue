<template>
  <v-layout row justify-center>
    <v-dialog v-bind:value="showSignInForm || showSignInPath" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Авторизация</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form v-model="valid" ref="form">
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field
                    v-model="email"
                    label="Email"
                    :rules="[v => !!v || 'E-mail не должен быть пустым']"
                    required
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="password"
                    label="Пароль"
                    type="password"
                    :rules="[v => !!v || 'Пароль не должен быть пустым']"
                    required
                  ></v-text-field>
                </v-flex>
              </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            flat
            @click.native="closeSignIn()"
          >Назад</v-btn>
          <v-btn
            color="blue darken-1"
            flat
            @click="submit()"
            :disabled="!valid"
          >Войти</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
// import {getToken} from '../api/auth'

export default {
  name: 'BaseSignInForm',
  data () {
    return {
      showSignInForm: false,
      showSignInPath: this.$route.fullPath === '/user/sign-in',
      valid: true,
      email: '',
      password: ''
    }
  },
  watch: {
    '$route': function (value) {
      if (value.fullPath === '/user/sign-in') {
        this.showSignInPath = true
        this.$refs.form.reset()
      } else {
        this.showSignInPath = false
      }
    }
  },
  mounted () {
    this.$root.$on('showSignIn', () => {
      this.showSignInForm = true
      this.$refs.form.reset()
    })
  },
  methods: {
    submit () {
      this.$auth.login({
        data: {username: this.email, password: this.password}
      })
        .then(() => {
          if (this.showSignInPath) {
            this.$router.push({path: '/'})
          }
          if (this.showSignInForm) {
            this.showSignInForm = false
          }
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Добро пожаловать',
              timeout: 5000
            })
        }, (res) => {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Неверный логин или пароль',
              timeout: 5000
            })
        })
    },
    closeSignIn () {
      if (this.showSignInPath) {
        this.$router.push({path: '/'})
      }
      if (this.showSignInForm) {
        this.showSignInForm = false
      }
      this.$refs.form.reset()
    }
  }
}
</script>

<style scoped>

</style>

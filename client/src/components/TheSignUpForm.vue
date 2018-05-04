<template>
  <v-layout row justify-center>
    <v-dialog v-bind:value="showSignUpForm || showSignUpPath" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Регистрация</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form v-model="valid" ref="form">
              <v-layout wrap>
                <v-flex xs12 sm6 md6>
                  <v-text-field
                    v-model="lastName"
                    label="Фамилия"
                    :rules="lastNameRules"
                    required
                    ></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-text-field
                    v-model="firstName"
                    label="Имя"
                    :rules="firstNameRules"
                    required
                    ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="email"
                    label="Email"
                    v-bind:loading="checkingEmail"
                    v-bind:error="emailError"
                    v-bind:error-messages="emailErrorMessages"
                    @input="validateEmail()"
                    :rules="emailRules"
                    required
                    ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="password"
                    label="Пароль"
                    type="password"
                    :rules="passwordRules"
                    required
                    ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="passwordConfirm"
                    label="Подтверждение пароля"
                    type="password"
                    v-bind:error="passwordConfirmError"
                    v-bind:error-messages="passwordConfirmErrorMessages"
                    @input="checkPasswordConfirm()"
                    :rules="passwordConfirmRules"
                    required
                    ></v-text-field>
                </v-flex>
              </v-layout>
            </v-form>
          </v-container>
          <small>* - обязательное поле</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            flat
            @click.native="closeSignUp()"
            >Назад</v-btn>
          <v-btn
            color="blue darken-1"
            flat
            @click="submit"
            :disabled="!valid || emailError || passwordConfirmError"
            >Зарегистрироваться</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
export default {
  name: 'TheSignUpForm',
  data () {
    return {
      showSignUpForm: false,
      showSignUpPath: this.$route.fullPath === '/user/sign-up',
      valid: true,
      lastName: '',
      lastNameRules: [
        v => !!v || 'Введите фамилию',
        v => (v && v.length <= 30) || 'Длинна должна быть менее 30 символов'
      ],
      firstName: '',
      firstNameRules: [
        v => !!v || 'Введите имя',
        v => (v && v.length <= 30) || 'Длинна должна быть менее 30 символов'
      ],
      email: '',
      checkingEmail: false,
      emailError: false,
      emailErrorMessages: [],
      emailRules: [
        v => !!v || 'Введите E-mail',
        v => (v && v.length <= 30) || 'Длинна должна быть менее 30 символов',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'Введите корректный E-mail'
      ],
      password: '',
      passwordRules: [
        v => !!v || 'Введите пароль',
        v => /(?=.*[a-z])/.test(v) || 'Пароль должен содержать как минимум одну букву латинского алфавита в нижнем регистре',
        v => /(?=.*[A-Z])/.test(v) || 'Пароль должен содержать как минимум одну букву латинского алфавита в верхнем регистре',
        v => /(?=.*\d)/.test(v) || 'Пароль должен содержать как минимум одну цифру',
        v => /(?=.*[@#$%])/.test(v) || 'Пароль должен содержать как минимум один спец символ: @#$%',
        v => (v && v.length >= 6 && v.length <= 120) || 'Длинна должна быть менее 30 символов'
      ],
      passwordConfirm: '',
      passwordConfirmError: false,
      passwordConfirmErrorMessages: [],
      passwordConfirmRules: [
        v => !!v || 'Подтвердите пароль',
        v => v === this.password || 'Пароли должны совпадать'
      ]
    }
  },
  watch: {
    '$route': function (value) {
      if (value === '/user/sign-up') {
        this.showSignUpPath = true
        this.$refs.form.reset()
      } else {
        this.showSignUpPath = false
      }
    }
  },
  mounted () {
    this.$root.$on('showSignUp', () => {
      this.showSignUpForm = true
      this.reset()
    })
  },
  methods: {
    validateEmail () {
      this.checkingEmail = true
      this.axios.post('/v1/user/register/check-username', {username: this.email})
        .then((response) => {
          if (response.data.status === 'used') {
            this.emailError = true
            this.emailErrorMessages = ['Данный E-mail уже зарегистрирован']
          } else {
            this.emailError = false
            this.emailErrorMessages = []
          }
        })
      this.checkingEmail = false
    },
    checkPasswordConfirm () {
      if (this.password === this.passwordConfirm) {
        this.passwordConfirmError = false
        this.passwordConfirmErrorMessages = []
      } else {
        this.passwordConfirmError = true
        this.passwordConfirmErrorMessages = ['Пароли дожны совпадать']
      }
    },
    reset () {
      this.$refs.form.reset()
      this.passwordConfirmError = false
      this.passwordConfirmErrorMessages = []
      this.emailError = false
      this.emailErrorMessages = []
    },
    submit () {
      if (this.$refs.form.validate()) {
        this.$auth.register({
          data: {
            username: this.email,
            lastName: this.lastName,
            firstName: this.firstName,
            password: this.password
          }
        })
          .then(() => {
            if (this.showSignUpPath) {
              this.$router.push({path: '/user/sign-in'})
            }
            if (this.showSignUpForm) {
              this.showSignUpForm = false
              this.$root.$emit('showSignIn')
            }
            this.$root.$emit(
              'showAlert',
              {
                color: 'success',
                message: 'Вы успешно прошли регистрацию, на ваш E-mail отправлено письмо для подтверждения.',
                timeout: 5000
              })
          }, (res) => {
            this.reset()
            this.$root.$emit(
              'showAlert',
              {
                color: 'error',
                message: 'Во время регистрации произшла ошибка.',
                timeout: 5000
              })
          })
      }
    },
    closeSignUp () {
      if (this.showSignUpPath) {
        this.$router.push({path: '/'})
      }
      if (this.showSignUpForm) {
        this.showSignUpForm = false
      }
      this.reset()
    }
  }
}
</script>

<style scoped>

</style>

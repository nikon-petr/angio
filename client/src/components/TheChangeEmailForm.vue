<template>
  <v-layout row justify-center>
    <v-dialog v-bind:value="showChangeEmailForm" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Смена адреса электронной почты</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form v-model="valid" ref="form">
              <v-layout wrap>
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
            @click.native="close()"
          >Назад</v-btn>
          <v-btn
            color="blue darken-1"
            flat
            @click="submit()"
            :disabled="!valid || emailError"
          >Сменить адрес</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
export default {
  name: 'TheChangeEmailForm',
  data: () => ({
    valid: true,
    showChangeEmailForm: false,
    email: '',
    checkingEmail: false,
    emailError: false,
    emailErrorMessages: [],
    emailRules: [
      v => !!v || 'Введите E-mail',
      v => (v && v.length <= 30) || 'Длинна должна быть менее 30 символов',
      v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'Введите корректный E-mail'
    ]
  }),
  mounted () {
    this.$root.$on('showChangeEmail', () => {
      this.showChangeEmailForm = true
      this.reset()
    })
  },
  methods: {
    validateEmail () {
      this.checkingEmail = true
      if (this.email != null && this.email !== '') {
        this.axios.get('v1/user/username-exists/' + this.email)
          .then((response) => {
            if (response.data.exists === true) {
              this.emailError = true
              this.emailErrorMessages = ['Данный E-mail уже зарегистрирован']
            } else {
              this.emailError = false
              this.emailErrorMessages = []
            }
          })
      }
      this.checkingEmail = false
    },
    submit () {
      if (this.$refs.form.validate()) {
        this.axios.post('v1/user/change-email', {newEmail: this.email})
          .then(() => {
            this.close()
            this.$emit('email-changed')
            this.$root.$emit(
              'showAlert',
              {
                color: 'success',
                message: 'Для продолжения смены E-mail подтвердите его, перейдя по ссылке в письме',
                timeout: 7000
              })
          }).catch((res) => {
            this.$root.$emit(
              'showAlert',
              {
                color: 'error',
                message: 'Во время смена E-mail произошла ошибка',
                timeout: 5000
              })
          })
      }
    },
    close () {
      this.showChangeEmailForm = false
      this.reset()
    },
    reset () {
      this.$refs.form.reset()
      this.emailError = false
      this.emailErrorMessages = []
    }
  }
}
</script>

<style scoped>

</style>

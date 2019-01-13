<template>
  <v-layout row justify-center>
    <v-dialog v-bind:value="showChangePasswordForm" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Смена пароля</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form v-model="valid" ref="form">
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field
                    v-model="password"
                    label="Старый пароль"
                    type="password"
                    :rules="[v => !!v || 'Введите старый пароль']"
                    required
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="newPassword"
                    label="Новый пароль"
                    type="password"
                    :rules="newPasswordRules"
                    required
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="newPasswordConfirm"
                    label="Подтверждение новго пароля"
                    type="password"
                    v-bind:error="newPasswordConfirmError"
                    v-bind:error-messages="newPasswordConfirmErrorMessages"
                    @input="checkNewPasswordConfirm()"
                    :rules="newPasswordConfirmRules"
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
            :disabled="!valid || newPasswordConfirmError"
          >Сменить пароль</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
export default {
  name: 'TheChangePasswordForm',
  data: () => ({
    showChangePasswordForm: false,
    valid: true,
    password: '',
    newPassword: '',
    newPasswordRules: [
      v => !!v || 'Введите пароль',
      v => /(?=.*[a-z])/.test(v) || 'Пароль должен содержать как минимум одну букву латинского алфавита в нижнем регистре',
      v => /(?=.*[A-Z])/.test(v) || 'Пароль должен содержать как минимум одну букву латинского алфавита в верхнем регистре',
      v => /(?=.*\d)/.test(v) || 'Пароль должен содержать как минимум одну цифру',
      v => /(?=.*[@#$%])/.test(v) || 'Пароль должен содержать как минимум один спец символ: @#$%',
      v => (v && v.length >= 6 && v.length <= 120) || 'Длинна должна быть менее 30 символов'
    ],
    newPasswordConfirm: '',
    newPasswordConfirmError: false,
    newPasswordConfirmErrorMessages: [],
    newPasswordConfirmRules: [
      v => !!v || 'Подтвердите пароль'
    ]
  }),
  mounted () {
    this.$root.$on('showChangePassword', () => {
      this.showChangePasswordForm = true
      this.reset()
    })
  },
  methods: {
    checkNewPasswordConfirm () {
      if (this.newPassword === this.newPasswordConfirm) {
        this.newPasswordConfirmError = false
        this.newPasswordConfirmErrorMessages = []
      } else {
        this.newPasswordConfirmError = true
        this.newPasswordConfirmErrorMessages = ['Пароли дожны совпадать']
      }
    },
    submit () {
      if (this.$refs.form.validate()) {
        this.axios.post('v1/user/change-password', {password: this.password, newPassword: this.newPassword})
          .then(() => {
            this.close()
            this.$emit('password-changed')
            this.$root.$emit(
              'showAlert',
              {
                color: 'success',
                message: 'Пароль успешно изменен',
                timeout: 5000
              })
          }).catch((res) => {
            if (res.status === 422) {
              this.$root.$emit(
                'showAlert',
                {
                  color: 'error',
                  message: 'Старый пароль введен неверно',
                  timeout: 5000
                })
            } else {
              this.$root.$emit(
                'showAlert',
                {
                  color: 'error',
                  message: 'Во время смены пароля произошла ошибка.',
                  timeout: 5000
                })
            }
          })
      }
    },
    close () {
      this.showChangePasswordForm = false
      this.reset()
    },
    reset () {
      this.$refs.form.reset()
      this.newPasswordConfirmError = false
      this.newPasswordConfirmErrorMessages = []
    }
  }
}
</script>

<style scoped>

</style>

<template lang="html">
  <v-layout row wrap>
    <v-flex xs12>
      <v-list subheader>
        <v-subheader inset class="title">Информация о пользователе</v-subheader>
        <v-list-tile justify-center>
          <v-list-tile-avatar>
            <v-icon>person</v-icon>
          </v-list-tile-avatar>
          <v-list-tile-content>
              {{ $auth.user().lastName }} {{ $auth.user().firstName }}
          </v-list-tile-content>
        </v-list-tile>
        <v-list-tile>
          <v-list-tile-avatar>
            <v-icon>alternate_email</v-icon>
          </v-list-tile-avatar>
          <v-list-tile-content class="d-inline-block">
            {{ $auth.user().email }} <v-btn @click="$root.$emit('showChangeEmail')" icon ripple><v-icon color="grey lighten-1">edit</v-icon></v-btn>
          </v-list-tile-content>
        </v-list-tile>
        <v-list-tile>
          <v-list-tile-avatar>
            <v-icon>lock</v-icon>
          </v-list-tile-avatar>
          <v-list-tile-content class="d-inline-block">
            Пароль <v-btn @click="$root.$emit('showChangePassword')" icon ripple><v-icon color="grey lighten-1">edit</v-icon></v-btn>
          </v-list-tile-content>
        </v-list-tile>

        <v-subheader inset class="title">Открытые сессии</v-subheader>
          <v-data-table
            :headers="sessionHeaders"
            :loading="sessionLoading"
            :items="sessions"
            must-sort
            hide-actions
            flat
            class="elevation-0"
            no-data-text="Нет открытых сессий"
          >
            <template slot="items" slot-scope="props">
              <td><v-icon>{{ getDeviceIcon(props.item.device) }}</v-icon><span class="pl-3" v-if="props.item.id === $auth.user().activeToken">(текущая сессия)</span></td>
              <td class="text-xs-right">{{ formatDate(props.item.issuedAt) }}</td>
              <td class="text-xs-left">{{ formatTime(props.item.issuedAt) }}</td>
              <td>{{ props.item.os }}</td>
              <td>{{ props.item.browser }}</td>
              <td class="justify-center layout px-0">
                <v-btn :disabled="props.item.id === $auth.user().activeToken" icon class="mx-0" @click="revoke(props.item.id)">
                  <v-icon>close</v-icon>
                </v-btn>
              </td>
            </template>
          </v-data-table>
      </v-list>
    </v-flex>
    <TheChangePasswordForm v-on:password-changed="getSessions()"></TheChangePasswordForm>
    <TheChangeEmailForm v-on:email-changed="getSessions()"></TheChangeEmailForm>
  </v-layout>
</template>

<script>
import dateFormat from 'dateformat'
import TheChangePasswordForm from '../components/TheChangePasswordForm'
import TheChangeEmailForm from '../components/TheChangeEmailForm'

export default {
  name: 'User',
  components: {TheChangeEmailForm, TheChangePasswordForm},
  data: () => ({
    sessionLoading: false,
    sessionHeaders: [
      {text: 'Тип устройства', value: 'device', sortable: false, width: '30%'},
      {text: 'Дата', value: 'issuedAt', sortable: true, width: '5%'},
      {text: 'Время', value: 'issuedAt', sortable: false, width: '25%'},
      {text: 'Операционная система', value: 'os', sortable: true, width: '20%'},
      {text: 'Браузер', value: 'browser', sortable: true, width: '17%'},
      {text: '', sortable: false, width: '3%'}
    ],
    sessions: []
  }),
  mounted () {
    this.getSessions()
  },
  methods: {
    getSessions () {
      this.sessionLoading = true
      this.$auth.fetch()
      this.axios.get('v1/user/sessions')
        .then((res) => {
          this.sessions = res.data
        })
      this.sessionLoading = false
    },
    formatDate (date) {
      return dateFormat(new Date(date), 'dd.mm.yyyy')
    },
    formatTime (date) {
      return dateFormat(new Date(date), 'H:MM')
    },
    getDeviceIcon (deviceString) {
      switch (deviceString) {
        case 'Computer': return 'computer'
        case 'Tablet': return 'tablet'
        case 'Mobile': return 'smartphone'
        case 'Unknown': return 'device_unknown'
        default: return 'devices_other'
      }
    },
    revoke (id) {
      this.axios.post('v1/auth/close-session/' + id)
        .then(() => {
          this.sessions = this.sessions.filter(obj => obj.id !== id)
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Сессия закрыта',
              timeout: 1000
            })
        })
        .catch(() => {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Ошибка закрытия сессии',
              timeout: 1000
            })
        })
    }
  }
}
</script>

<style scoped>

</style>

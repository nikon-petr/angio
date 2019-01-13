<template>
  <v-layout>
    <v-flex>
      <div class="display-1 mb-2">Открытые сессии </div>
      <div class="title mb-3 text--secondary">{{ this.$route.params.username }}</div>
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
          <td>{{ formatDate(props.item.issuedAt) }}</td>
          <td>{{ formatTime(props.item.issuedAt) }}</td>
          <td>{{ props.item.os }}</td>
          <td>{{ props.item.browser }}</td>
          <td class="justify-center layout px-0">
            <v-btn :disabled="props.item.id === $auth.user().activeToken" icon class="mx-0" @click="revoke(props.item.id)">
              <v-icon>close</v-icon>
            </v-btn>
          </td>
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</template>

<script>
import dateFormat from 'dateformat'

export default {
  name: 'UserSessionList',
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
    this.getSessions(this.$route.params.username)
  },
  methods: {
    getSessions (username) {
      this.sessionLoading = true
      this.$auth.fetch()
      this.axios.get(`v1/user/${username}/sessions`)
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
      this.axios.post(`v1/auth/revoke/${id}`)
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

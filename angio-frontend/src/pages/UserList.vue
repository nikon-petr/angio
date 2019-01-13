<template>
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
        <div class="display-1 mb-3">Список пользователей</div>
        <v-card-title>
          <v-spacer></v-spacer>
          <v-text-field
            v-model="query"
            append-icon="search"
            label="Поиск"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
          :headers="headers"
          :items="users"
          :loading="loading"
          :pagination.sync="pagination"
          :total-items="totalUsers"
          must-sort
          no-data-text="Нет данных"
          no-results-text="Поиск не дал результатов"
          rows-per-page-text="Количество строк на странице: "
          :rows-per-page-items="rowNumbers"
        >
          <template slot="items" slot-scope="props">
            <td>{{ props.item.username }}</td>
            <td>
              <v-menu offset-y transition="scale-transition">
                <v-btn slot="activator" flat icon><v-icon>more_horiz</v-icon></v-btn>
                <v-list>
                  <v-list-tile :to="{path: '/user/' + props.item.username + '/sessions'}">
                    <v-list-tile-title>Активные сессии</v-list-tile-title>
                  </v-list-tile>
                  <v-list-tile v-if="props.item.enabled" @click="disableUser(props.item.username)" :disabled="props.item.username === $auth.user().email">
                    <v-list-tile-title>Запретить доступ</v-list-tile-title>
                  </v-list-tile>
                  <v-list-tile v-if="!props.item.enabled" @click="enableUser(props.item.username)">
                    <v-list-tile-title>Разрешить доступ</v-list-tile-title>
                  </v-list-tile>
                </v-list>
              </v-menu>
            </td>
            <td>{{ props.item.lastName }}</td>
            <td>{{ props.item.firstName }}</td>
            <td class="text-xs-center">
              <v-icon>{{ getActiveIcon(props.item.enabled) }}</v-icon>
            </td>
          </template>
          <template slot="pageText" slot-scope="props">
            Строки {{ props.pageStart }} - {{ props.pageStop }} из {{ props.itemsLength }}
          </template>
        </v-data-table>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: 'UserList',
  data: () => ({
    query: '',
    pagination: {},
    rowNumbers: [15, 25, 50],
    loading: true,
    headers: [
      {text: 'E-mail', value: 'username', sortable: true},
      {text: '', sortable: false, width: '3%'},
      {text: 'Фамилия', value: 'lastName', sortable: false, width: '15%'},
      {text: 'Имя', value: 'firstName', sortable: false, width: '30%'},
      {text: 'Доступ разрешен', value: 'enabled', sortable: true, width: '15%', align: 'center'}
    ],
    totalUsers: 0,
    users: []
  }),
  watch: {
    pagination: {
      handler () {
        this.loading = true
        this.getDataFromApi()
        this.loading = false
      },
      deep: true
    },
    query (newQuery) {
      this.query = newQuery

      this.loading = true
      this.getDataFromApi()
      this.loading = false
    },
    'users.enabled' (newVal) {
      this.users.enabled = newVal
    }
  },
  mounted () {
    this.getDataFromApi()
    this.loading = false
  },
  methods: {
    getDataFromApi () {
      const {sortBy, descending, page, rowsPerPage} = this.pagination

      let params = {
        sort: sortBy + ',' + (descending ? 'desc' : 'asc'),
        page: page - 1,
        size: rowsPerPage
      }

      if (this.query !== '') {
        params = {...params, query: this.query.replace(/\s+/g, ' ').replace(/^\s+|\s+$/, '')}
      }

      this.axios.get('v1/user', {params: params})
        .then(response => {
          this.users = response.data.content
          this.totalUsers = response.data.totalElements
        })
    },
    getActiveIcon (active) {
      if (active) {
        return 'check'
      } else {
        return 'close'
      }
    },
    disableUser (username) {
      this.axios.post(`v1/user/${username}/disable`)
        .then(res => {
          let userIndex = this.users.findIndex(u => u.username === username)
          this.users[userIndex].enabled = false
        })
    },
    enableUser (username) {
      this.axios.post(`v1/user/${username}/enable`)
        .then(res => {
          let userIndex = this.users.findIndex(u => u.username === username)
          this.users[userIndex].enabled = true
        })
    }
  }
}
</script>

<style scoped>

</style>

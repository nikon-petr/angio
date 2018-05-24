<template lang="html">
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
        <h1>Список анализов</h1>
        <v-card-title>
          <v-spacer></v-spacer>
          <div>
          <v-menu
            :close-on-content-click="false"
            v-model="search_date_visibility"
            transition="scale-transition"
            offset-y
          >
            <v-text-field
              slot="activator"
              v-model="computedDateFormatted"
              label="Поиск по дате"
              hint=""
              persistent-hint
              prepend-icon="event"
              readonly
            ></v-text-field>
            <v-date-picker v-model="search_date" no-title @input="search_date_visibility = false"></v-date-picker>
          </v-menu>
          <v-btn
            color="blue accent-3"
            class="white--text"
            @click="clearDate()"
            :disabled="search_date.length<=0"
          >Очистить дату</v-btn>
          <v-spacer></v-spacer>
          <v-text-field
            prepend-icon="search"
            label="Поиск"
            single-line
            hide-details
            v-model="search"
          ></v-text-field>
        </div>
        </v-card-title>
        <v-data-table
          :headers="headers"
          :items="analyses"
          :loading="loading_analyses"
          :total-items="totalSize"
          :pagination.sync="pagination"
          class="elevation-1"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <td>{{ props.item.name }}</td>
            <td class="text-xs-right">{{ props.item.shortDescription }}</td>
            <td class="text-xs-right">{{ props.item.analyseType }}</td>
            <td class="text-xs-right">{{ props.item.patient }}</td>
            <td class="text-xs-right">{{ props.item.policy }}</td>
            <td class="text-xs-right">{{ props.item.user }}</td>
            <td class="text-xs-right">{{ formatFullDate(props.item.analyseDate) }}</td>
            <td class="text-xs-center px-0">
              <v-btn
                :loading=!props.item.analyseFinished
                :to="{ path: '/analyses/' + props.item.id }"
                :disabled=!props.item.analyseFinished
                color="transparent"
                class="white--text"
              >
                Подробнее
                <v-icon white dark>chevron-right</v-icon>
              </v-btn>
            </td>
          </template>
        </v-data-table>
        <v-btn style="margin-bottom: 40px" fixed fab bottom right dark large color="blue accent-3" @click="$root.$emit('showNewAnalyseDialog')">
          <v-icon dark>add</v-icon>
        </v-btn>
        <div style="flex: 1 1 auto;"></div>
      </v-app>
    </v-flex>
    <TheNewAnalyseForm></TheNewAnalyseForm>
  </v-layout>
</template>

<script>
import TheNewAnalyseForm from '../components/TheNewAnalyseForm'

export default {
  name: 'Analyses',
  components: {TheNewAnalyseForm},
  data: () => ({
    search: '',
    search_date: '',
    search_date_formatted: '',
    search_date_visibility: false,
    pagination: {
      sortBy: 'analyseDate',
      descending: true
    },
    totalSize: 0,
    headers: [
      { text: 'Наименование', align: 'left', value: 'name', width: '15%' },
      { text: 'Краткое описание', align: 'left', value: 'shortDescription', width: '30%' },
      { text: 'Тип анализа', align: 'left', value: 'analyseType', width: '5%' },
      { text: 'Пациент (ФИО)', align: 'left', value: 'patient', width: '10%' },
      { text: '№ полиса', align: 'left', value: 'policy', width: '10%' },
      { text: 'Диагност (ФИО)', align: 'left', value: 'user', width: '10%' },
      { text: 'Дата', align: 'left', value: 'analyseDate', width: '10%' },
      { text: 'Действия', align: 'left', value: 'actions', sortable: false, width: '10%' }
    ],
    analyses: [],
    defaultItem: {
      id: '',
      name: '',
      shortDescription: '',
      analyseType: '',
      patient: '',
      policy: '',
      user: '',
      analyseDate: '',
      analyseFinished: true
    },
    loading_analyses: true
  }),
  computed: {
    computedDateFormatted () {
      return this.formatDate(this.search_date)
    }
  },
  watch: {
    search_date (val) {
      this.search_date_formatted = this.formatDate(this.search_date)
      this.load()
    },
    search (filter) {
      this.search = filter
      this.load()
    },
    pagination: {
      handler () {
        this.load()
      },
      deep: true
    }
  },
  mounted () {
    this.load()
    this.$root.$on('refreshAnalyses', () => {
      this.load()
    })
  },
  methods: {
    load () {
      this.loading_analyses = true
      // pagination
      const rowsPerPage = this.pagination.rowsPerPage
      const page = this.pagination.page - 1
      // sorting
      const sortBy = this.pagination.sortBy
      var descending = ''
      if (this.pagination.descending === true) {
        descending = 'desc'
      }
      if (this.pagination.descending === false) {
        descending = 'asc'
      }
      var sorting = '&sort=' + sortBy + ',' + descending
      if (sortBy === null || descending === '') {
        sorting = ''
      }
      this.axios.get('v1/analyse?search=' + this.search + '&date=' + this.search_date_formatted + '&page=' + page + '&size=' + rowsPerPage + sorting)
        .then((response) => {
          console.log(response)
          this.analyses = response.data.data
          this.totalSize = response.data.size
          this.loading_analyses = false
        })
        .catch(() => {
          this.loading_analyses = false
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Ошибка получения списка анализов',
              timeout: 5000
            })
        })
    },
    formatFullDate (date) {
      if (!date) return ''

      const [day, month, year, hour, minutes, seconds] = date.split('-')
      return `${day}.${month}.${year} ${hour}:${minutes}:${seconds}`
    },
    formatDate (date) {
      if (!date) return ''

      const [year, month, day] = date.split('-')
      return `${day}-${month}-${year}`
    },
    parseDate (date) {
      if (!date) return ''

      const [month, day, year] = date.split('/')
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    },
    clearDate () {
      this.search_date = ''
      this.search_date_formatted = ''
    }
  }
}
</script>

<style scoped>

</style>

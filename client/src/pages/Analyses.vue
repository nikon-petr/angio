<template lang="html">
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
        <h1>Список анализов</h1>
        <v-card-title>
          <v-spacer></v-spacer>
          <v-text-field
            append-icon="search"
            label="Search"
            single-line
            hide-details
            v-model="search"
          ></v-text-field>
        </v-card-title>
        <v-data-table
          :headers="headers"
          :items="analyses"
          :search="search"
          :loading="loading_analyses"
          hide-actions
          class="elevation-1"
        >
          <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
          <template slot="items" slot-scope="props">
            <td>{{ props.item.name }}</td>
            <td class="text-xs-right">{{ props.item.short_description }}</td>
            <td class="text-xs-right">{{ props.item.analyse_type }}</td>
            <td class="text-xs-right">{{ props.item.patient }}</td>
            <td class="text-xs-right">{{ props.item.policy }}</td>
            <td class="text-xs-right">{{ props.item.diagnost }}</td>
            <td class="text-xs-right">{{ props.item.date }}</td>
            <td class="justify-center layout px-0">
              <v-btn
                :loading=!props.item._analyse_finished
                :to="{ path: '/analyses/' + props.item.id }"
                :disabled=!props.item._analyse_finished
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
    headers: [
      { text: 'Наименование', align: 'left', value: 'name', width: '15%' },
      { text: 'Краткое описание', align: 'left', value: 'short_description', width: '30%' },
      { text: 'Тип анализа', align: 'left', value: 'analyse_type', width: '5%' },
      { text: 'Пациент (ФИО)', align: 'left', value: 'patient', width: '10%' },
      { text: '№ полиса', align: 'left', value: 'policy', width: '10%' },
      { text: 'Диагност (ФИО)', align: 'left', value: 'diagnost', width: '10%' },
      { text: 'Дата', align: 'left', value: 'date', width: '10%' },
      { text: 'Действия', align: 'left', value: 'actions', sortable: false, width: '10%' }
    ],
    analyses: [],
    defaultItem: {
      id: '',
      name: '',
      short_description: '',
      analyse_type: '',
      patient: '',
      policy: '',
      diagnost: '',
      date: '',
      _analyse_finished: true
    },
    loading_analyses: true
  }),
  mounted () {
    this.load()
    this.$root.$on('refreshAnalyses', () => {
      this.load()
    })
  },
  methods: {
    load () {
      this.axios.get('v1/analyse')
        .then((response) => {
          console.log(response)
          this.analyses = response.data
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
    }
  }
}
</script>

<style scoped>

</style>

<template lang="html">
  <v-layout v-bind="binding">
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
            <td class="text-xs-right">{{ props.item.diagnost }}</td>
            <td class="text-xs-right">{{ props.item.date | moment("DD:MM:YYYY HH:MM") }}</td>
            <td class="justify-center layout px-0">
              <v-btn
                :loading=!props.item.is_analyse_finished
                @click="detailItem(props.item)"
                :disabled=!props.item.is_analyse_finished
                color="transparent"
                class="white--text"
              >
                Подробнее
                <v-icon white dark>chevron-right</v-icon>
              </v-btn>
            </td>
          </template>
        </v-data-table>

        <v-btn style="margin-bottom: 40px" fixed fab bottom right dark large color="blue accent-3">
          <v-icon dark>add</v-icon>
        </v-btn>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
  import API from '@/lib/API'

  export default {
  data: () => ({
      dialog: false,
      search: '',
      headers: [
        { text: 'Наименование', align: 'left' , value: 'name', width: '20%' },
        { text: 'Краткое описание', align: 'left', value: 'short_description', width: '35%' },
        { text: 'Первичный анализ', align: 'left', value: 'analyse_type', width: '5%' },
        { text: 'Пациент (ФИО)', align: 'left', value: 'patient', width: '10%' },
        { text: 'Диагност (ФИО)', align: 'left', value: 'diagnost', width: '10%' },
        { text: 'Дата', align: 'left', value: 'date', width: '10%' },
        { text: 'Действия', align: 'left', value: 'actions', sortable: false, width: '10%' }
      ],
      analyses: [],
      defaultItem: {
        name: '',
        short_description: '',
        analyse_type: '',
        patient: '',
        diagnost: '',
        date: '',
        is_analyse_finished: true
      },
      loading_analyses: true
    }),

    mounted(){
      this.load()
    },

    methods: {
      load(){
        API.getAnalyses()
          .then(analyses => {
              this.analyses = analyses
              this.loading_analyses = false
            })
      },

      detailItem (item) {
        console.log('Detail item: ' + item)
      },

      close () {
        this.dialog = false
      }
    },

    computed: {
      formatted(){
        return Vue.filter('date')(this.analyses.date)
      }
    }
  }
</script>

<style lang="css">

</style>

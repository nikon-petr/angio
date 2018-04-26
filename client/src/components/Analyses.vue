<template lang="html">
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
      :items="items"
      :search="search"
      hide-actions
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td>{{ props.item.name }}</td>
        <td class="text-xs-right">{{ props.item.short_description }}</td>
        <td class="text-xs-right">{{ props.item.first }}</td>
        <td class="text-xs-right">{{ props.item.patient }}</td>
        <td class="text-xs-right">{{ props.item.diagnost }}</td>
        <td class="text-xs-right">{{ props.item.date }}</td>
        <td class="justify-center layout px-0">
          <v-btn icon class="mx-0" @click="deleteItem(props.item)">
            <v-icon color="pink">delete</v-icon>
          </v-btn>
        </td>
      </template>
    </v-data-table>

    <v-btn style="margin-bottom: 40px" fixed fab bottom right dark large color="blue accent-3">
      <v-icon dark>add</v-icon>
    </v-btn>
  </v-app>
</template>

<script>
import API from '@/lib/API'

export default {
  data: () => ({
    dialog: false,
    search: '',
    headers: [
      {
        text: 'Наименование',
        align: 'left',
        value: 'name'
      },
      {text: 'Краткое описание', value: 'short_description'},
      {text: 'Первичный анализ', value: 'first'},
      {text: 'Пациент (ФИО)', value: 'patient'},
      {text: 'Диагност (ФИО)', value: 'diagnost'},
      {text: 'Дата', value: 'date'},
      {text: 'Действия', value: 'actions', sortable: false}
    ],
    items: [],
    defaultItem: {
      name: '-',
      short_description: '-',
      first: '-',
      patient: '-',
      diagnost: '',
      date: ''
    }
  }),

  created () {
    this.initialize()
  },

  mounted () {
    this.load()
  },

  methods: {
    load () {
      API.getAnalyses()
        .then((analyses) => {
          console.log(analyses)
        })
    },
    initialize () {
      this.items = [
        {
          date: '24.04.2017 19:31',
          name: 'Первичный анализ перенесённого тромбоза',
          short_description: 'Акцент при анализе сделан на повышенную степень извилистости сосудистой системы глаза',
          first: true,
          patient: 'Заболевальная Е.В.',
          diagnost: 'Врачебный А.В.'
        }
      ]
    },
    deleteItem (item) {
      const index = this.items.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.items.splice(index, 1)
    },

    close () {
      this.dialog = false
    }
  }
}
</script>

<style lang="css">

</style>

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
            <td class="text-xs-right">{{ props.item.policy }}</td>
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

        <v-btn style="margin-bottom: 40px" fixed fab bottom right dark large color="blue accent-3" @click.stop="dialog = true">
          <v-icon dark>add</v-icon>
        </v-btn>

        <v-dialog
          v-model="dialog"
          fullscreen
          hide-overlay
          transition="dialog-bottom-transition"
          scrollable
        >
          <v-card tile>
           <v-toolbar card dark color="primary">
             <v-btn icon @click.native="dialog = false" dark>
               <v-icon>close</v-icon>
             </v-btn>
             <v-toolbar-title>Новый анализ</v-toolbar-title>
             <v-spacer></v-spacer>
             <v-toolbar-items>
               <v-btn dark flat @click.negative="startNewAnalyse()">Запустить</v-btn>
             </v-toolbar-items>
           </v-toolbar>
           <v-card-text>
            <v-form v-model="valid">
            <v-layout row justify-space-between>
            <v-flex xs5>
              <h2>Информация о пациенте</h2>
             <v-text-field
               label="Фамилия"
               v-model="new_analyse.patient.lastname"
               :rules="new_analyse_rules.patient.default"
               required
              ></v-text-field>
              <v-text-field
                label="Имя"
                v-model="new_analyse.patient.firstname"
                :rules="new_analyse_rules.patient.default"
                required
               ></v-text-field>
               <v-text-field
                 label="Отчество"
                 v-model="new_analyse.patient.patronymic"
                ></v-text-field>
              <v-text-field
               label="E-mail"
               v-model="new_analyse.patient.email"
               :rules="new_analyse_rules.patient.email"
             ></v-text-field>
             <v-text-field
              label="Телефон"
              v-model="new_analyse.patient.phone"
              :rules="new_analyse_rules.patient.phone"
              required
            ></v-text-field>
            <v-text-field
             label="Страховой полис (номер)"
             v-model="new_analyse.patient.policy"
             :rules="new_analyse_rules.patient.policy"
             required
           ></v-text-field>
           <v-menu
             ref="menu2"
             lazy
             :close-on-content-click="false"
             v-model="menu2"
             transition="scale-transition"
             offset-y
             full-width
             :nudge-right="40"
             min-width="290px"
             :return-value.sync="date"
           >
             <v-text-field
              slot="activator"
              label="Укажите дату рождения"
              v-model="new_analyse.patient.bday"
              :rules="new_analyse_rules.patient.default"
              readonly
              required
            ></v-text-field>
            <v-date-picker v-model="new_analyse.patient.bday" @input="$refs.menu_bday.save(new_analyse.patient.bday)"></v-date-picker>
          </v-menu>
            <v-text-field
             label="Место работы/учёбы"
             v-model="new_analyse.patient.address"
             :rules="new_analyse_rules.patient.default"
             required
           ></v-text-field>
           <v-text-field
            label="Место работы/учёбы"
            v-model="new_analyse.patient.work"
            :rules="new_analyse_rules.patient.default"
            required
            ></v-text-field>
            <v-text-field
            name="input-3-2"
            multi-line
             label="Комментарий"
             v-model="new_analyse.patient.comments"
             ></v-text-field>
             </v-flex>
             <v-flex xs6>
             <h2>Информация о болезни</h2>
             <v-text-field
               label="Название"
               v-model="new_analyse.info.name"
               :rules="new_analyse_rules.info.default"
               required
              ></v-text-field>
              <v-text-field
                label="Краткое описание"
                v-model="new_analyse.info.short_description"
                :rules="new_analyse_rules.info.default"
                required
               ></v-text-field>
               <v-text-field
                 label="Подробное описание"
                 v-model="new_analyse.info.full_description"
                ></v-text-field>
                <v-select
                  label="Тип анализа"
                  v-model="new_analyse.info.analyse_type"
                  :rules="new_analyse_rules.info.default"
                  :items="items_analyse_type"
                  data-vv-name="new_analyse.info.analyse_type"
                  required
                 ></v-select>
            </v-flex>
            </v-layout>
            </v-form>
            </v-card-text>
           <div style="flex: 1 1 auto;"></div>
           </v-card>
          </v-dialog>
         <div style="flex: 1 1 auto;"></div>
       </v-card>
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
        { text: 'Наименование', align: 'left' , value: 'name', width: '15%' },
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
        name: '',
        short_description: '',
        analyse_type: '',
        patient: '',
        policy: '',
        diagnost: '',
        date: '',
        is_analyse_finished: true
      },
      loading_analyses: true,
      new_analyse: {
        patient: {
          firstname: '',
          lastname: '',
          patronymic: '',
          email: '',
          phone: '',
          policy: '',
          bday: '',
          address: '',
          work: '',
          comments: ''
        },
        info: {
          name: '',
          short_description: '',
          full_description: '',
          analyse_type: '',
          comments: ''
        },
        img: ''
      },
      valid: false,
      new_analyse_rules: {
        patient: {
          default: [
            v => !!v || 'Поле обязательно для заполнения'
          ],
          email: [
            v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'Неверный формат почты'
          ],
          phone: [
            v => !!v || 'Поле обязательно для заполнения',
            v => /^\+?1?\s*?\(?\d{3}(?:\)|[-|\s])?\s*?\d{3}[-|\s]?\d{5}$/.test(v) || 'Неверный формат номера телефона'
          ],
          policy: [
            v => !!v || 'Поле обязательно для заполнения',
            v => /^\d{16}/.test(v) || 'Номер должен содержать 16 цифр',
          ]
        },
        info: {
          default: [
            v => !!v || 'Поле обязательно для заполнения'
          ]
        }
      },
      menu_bday: false,
      items_analyse_type: [
        'Первичный анализ',
        'Последующий анализ'
      ]
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

      startNewAnalyse (){
        console.log(this.new_analyse)
        this.dialog = false
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

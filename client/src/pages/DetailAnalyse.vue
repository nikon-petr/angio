<template lang="html">
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
        <h1>Подробный анализ</h1>
        <h2>Основные сведения</h2>
        <v-card id="inspire" dark>
          <v-card-title primary-title>
            <v-layout row justify-space-between>
              <v-flex xs5 md5 lg5>
                <h3>Информация о пациенте</h3>
                <h3 class="headline mb-0">ФИО:</h3>
                <div>{{ analyse.analyse_base_info.patient.lastname + ' '
                  + analyse.analyse_base_info.patient.firstname + ' '
                  + analyse.analyse_base_info.patient.patronymic }}</div>
                <h3 class="headline mb-0">E-mail:</h3>
                <div>{{ analyse.analyse_base_info.patient.email }}</div>
                <h3 class="headline mb-0">Телефон:</h3>
                <div>{{ analyse.analyse_base_info.patient.phone }}</div>
                <h3 class="headline mb-0">Страховой полис (номер):</h3>
                <div>{{ analyse.analyse_base_info.patient.policy }}</div>
                <h3 class="headline mb-0">Дата рождения:</h3>
                <div>{{ analyse.analyse_base_info.patient.bday }}</div>
                <h3 class="headline mb-0">Место жительства:</h3>
                <div>{{ analyse.analyse_base_info.patient.address }}</div>
                <h3 class="headline mb-0">Место работы/учёбы:</h3>
                <div>{{ analyse.analyse_base_info.patient.work }}</div>
                <h3 class="headline mb-0" v-if="analyse.analyse_base_info.patient.comments.length > 0">Комментарий:</h3>
                <div v-if="analyse.analyse_base_info.patient.comments.length > 0">{{ analyse.analyse_base_info.patient.comments }}</div>
              </v-flex>
              <v-flex xs6 md6 lg6>
                <h3>Информация о болезни</h3>
                <h3 class="headline mb-0">Название:</h3>
                <div>{{ analyse.analyse_base_info.info.name }}</div>
                <h3 class="headline mb-0">Краткое описание:</h3>
                <div>{{ analyse.analyse_base_info.info.short_description }}</div>
                <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.full_description.length > 0">Подробное описание:</h3>
                <div v-if="analyse.analyse_base_info.info.full_description.length > 0">{{ analyse.analyse_base_info.info.full_description }}</div>
                <h3 class="headline mb-0">Тип анализа:</h3>
                <div>{{ analyse.analyse_base_info.info.analyse_type }}</div>
                <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.comments.length > 0">Комментарий:</h3>
                <div v-if="analyse.analyse_base_info.info.comments.length > 0">{{ analyse.analyse_base_info.info.comments }}</div>
              </v-flex>
            </v-layout>
          </v-card-title>
        </v-card>
        <h2>Результаты анализа</h2>
        <div>
          <v-tabs v-model="active" dark centered>
            <v-tab ripple>
              Геометрическая характеристика СС
            </v-tab>
            <v-tab-item>
              <v-card flat>
                <v-card-text>Геометрическая характеристика сосудов</v-card-text>
              </v-card>
            </v-tab-item>

            <v-tab ripple>
              Анализ макулы
            </v-tab>
            <v-tab-item>
              <v-card flat>
                <v-card-text>Макула</v-card-text>
              </v-card>
            </v-tab-item>

            <v-tab ripple>
              Анализ зон ишемии
            </v-tab>
            <v-tab-item>
              <v-card flat>
                <v-card-text>Ишемия</v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </div>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
import {getDetailAnalyse} from '../api/analyses'
export default {
  name: 'DetailAnalyse',
  data: () => ({
    active: null,
    loading_analyse: true,
    analyse: {
      analyse_base_info: {
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
        }
      },
      result: {
        analyse_geometric_characteristic: {
          img: {
            original: '',
            binarized: '',
            skel: ''
          },
          vessels: []
        },
        conclusion: ''
      }
    }
  }),
  mounted () {
    this.loadDetailAnalyse()
  },
  methods: {
    loadDetailAnalyse () {
      getDetailAnalyse()
        .then((response) => {
          console.log(response)
          this.analyse.analyse_base_info.patient = response.data.analyse.analyse_base_info.patient
          this.analyse.analyse_base_info.info = response.data.analyse.analyse_base_info.info
          this.analyse.result = response.data.analyse.result
          this.loading_analyse = false
        })
        .catch(() => {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Ошибка получения анализа',
              timeout: 5000
            })
        })
    }
  }
}
</script>

<style scoped>

</style>

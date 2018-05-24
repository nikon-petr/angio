<template lang="html">
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
        <div class="display-1">Подробный анализ</div>
        <div class="headline my-3">Основные сведения</div>
        <v-card id="inspire" dark>
          <v-card-title primary-title>
            <v-progress-linear v-if="loading_analyse" :indeterminate="true"></v-progress-linear>
            <v-layout v-if="!loading_analyse" justify-space-between>
              <v-flex xs4 md4 lg4>
                <h3>Дата</h3>
                <div class="headline mb-0">{{ formatFullDate(analyse.analyse_base_info.info.analyseDate) }}</div>
              </v-flex>
            </v-layout>
          </v-card-title>
          <v-card-title primary-title>
          <v-layout v-if="!loading_analyse" row justify-space-between>
            <v-flex xs4 md4 lg4>
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
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.patient.comment != null && analyse.analyse_base_info.patient.comment.length > 0">Комментарий:</h3>
              <div v-if="analyse.analyse_base_info.patient.comment != null && analyse.analyse_base_info.patient.comment.length > 0">{{ analyse.analyse_base_info.patient.comment}}</div>
            </v-flex>
            <v-flex xs5 md5 lg5>
              <h3>Информация о болезни</h3>
              <h3 class="headline mb-0">Название:</h3>
              <div>{{ analyse.analyse_base_info.info.name }}</div>
              <h3 class="headline mb-0">Краткое описание:</h3>
              <div>{{ analyse.analyse_base_info.info.shortDescription }}</div>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.fullDescription != null && analyse.analyse_base_info.info.fullDescription.length > 0">Подробное описание:</h3>
              <div v-if="analyse.analyse_base_info.info.fullDescription != null && analyse.analyse_base_info.info.fullDescription.length > 0">{{ analyse.analyse_base_info.info.fullDescription }}</div>
              <h3 class="headline mb-0">Тип анализа:</h3>
              <div>{{ analyse.analyse_base_info.info.analyseType }}</div>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.comment != null && analyse.analyse_base_info.info.comment.length > 0">Комментарий:</h3>
              <div v-if="analyse.analyse_base_info.info.comment != null && analyse.analyse_base_info.info.comment.length > 0">{{ analyse.analyse_base_info.info.comment }}</div>
            </v-flex>
            <v-flex xs6 md6 lg6>
              <h3>Заключение о болезни</h3>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.conclusion == null || analyse.analyse_base_info.info.conclusion.length <= 0">Заключение отсутствует</h3>
              <div v-if="analyse.analyse_base_info.info.conclusion != null && analyse.analyse_base_info.info.conclusion.length > 0">{{ analyse.analyse_base_info.info.conclusion }}</div>
            </v-flex>
            <v-dialog v-model="dialogDeleteAnalyse" max-width="290">
              <v-card>
                <v-card-title class="headline">Подтвердите действие</v-card-title>
                <v-card-text>Вы действительно хотите удалить анализ?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" flat="flat" @click.native="dialogDeleteAnalyse = false">Отмена</v-btn>
                  <v-btn color="blue darken-1" flat="flat" @click="deleteAnalyseConfirmed()">Удалить</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogEditConclusion" max-width="800">
              <v-card>
                <v-card-title class="headline">Редактирование заключения</v-card-title>
                <v-container fluid>
                  <v-layout row>
                    <v-flex xs12>
                    <v-text-field
                      name="input-1"
                      textarea
                      v-model="editConclusionContent"
                      ></v-text-field>
                    </v-flex>
                  </v-layout>
                </v-container>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" flat="flat" @click.native="dialogEditConclusion = false">Отмена</v-btn>
                  <v-btn color="blue darken-1" flat="flat" @click="editConclusion()">Сохранить</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-layout>
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue accent-3" :disabled="$auth.user().email == analyse.username" @click.native="dialogEditConclusion = true" fab dark>
            <v-icon>add_comment</v-icon>
          </v-btn>
          <v-btn color="orange" fab dark>
            <v-icon>picture_as_pdf</v-icon>
          </v-btn>
          <v-btn color="red" :disabled="false" @click="dialogDeleteAnalyse = true" fab dark>
            <v-icon>delete</v-icon>
          </v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
      <div class="headline my-3">Результаты анализа</div>
      <div>
        <v-tabs v-model="active" dark centered>
          <v-tab ripple>
            Геометрическая характеристика СС
          </v-tab>
          <v-tab-item>
            <GeometricAnalyseTab
              v-bind:geometric="analyse.result.geometric_analyse"
            ></GeometricAnalyseTab>
          </v-tab-item>
          <v-tab ripple>
            Степень кровоснабжения сс
          </v-tab>
          <v-tab-item>
            <BloodFlowAnalyseTab
              v-bind:ishemia-src="analyse.result.bloodFlowAnalyse.ishemiaImageUrl"
              v-bind:density-src="analyse.result.bloodFlowAnalyse.densityImageUrl"
              v-bind:ishemia-arr="analyse.result.bloodFlowAnalyse.ischemias"
              v-bind:makula="analyse.result.bloodFlowAnalyse.makula"
              v-bind:density-arr="analyse.result.bloodFlowAnalyse.densities"
            ></BloodFlowAnalyseTab>
          </v-tab-item>
        </v-tabs>
      </div>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
import BloodFlowAnalyseTab from '../components/BloodFlowAnalyseTab'
import GeometricAnalyseTab from '../components/GeometricAnalyseTab'
export default {
  name: 'DetailAnalyse',
  components: {BloodFlowAnalyseTab, GeometricAnalyseTab},
  created () {
    this.detail_analyse_id = this.$route.params.id
    this.loadDetailAnalyse()
  },
  data: () => ({
    detail_analyse_id: 0,
    active: null,
    dialogEditConclusion: false,
    dialogDeleteAnalyse: false,
    editConclusionContent: '',
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
          comment: ''
        },
        info: {
          id: 0,
          name: '',
          shortDescription: '',
          fullDescription: '',
          analyseType: '',
          comment: '',
          analyseDate: '',
          conclusion: ''
        }
      },
      result: {
        geometric_analyse: {
          originalImage: '',
          binarizedImage: '',
          skelImage: '',
          vessels: []
        },
        bloodFlowAnalyse: {
          ishemiaImageUrl: '',
          densityImageUrl: '',
          ischemias: [],
          makula: {},
          densities: []
        }
      },
      username: ''
    }
  }),
  methods: {
    loadDetailAnalyse () {
      this.axios.get('v1/analyse/detail/' + this.detail_analyse_id)
        .then((response) => {
          console.log(response)
          this.analyse.analyse_base_info.patient = response.data.patient
          this.analyse.analyse_base_info.info = response.data.info
          this.analyse.result.geometric_analyse = response.data.geometricAnalyse
          this.analyse.result.bloodFlowAnalyse = response.data.analyseBloodFlowResponse
          // this.analyse.result.geometric_analyse.vessels = response.data.geometricAnalyse.vessels
          this.analyse.result.username = response.data.username
          this.editConclusionContent = this.analyse.analyse_base_info.info.conclusion
          // images
          // this.originalImageSrc = response.data.geometricAnalyse.originalImage
          // this.binarizedImageSrc = response.data.geometricAnalyse.binarizedImage
          // this.skelImageSrc = response.data.geometricAnalyse.skelImage
          // this.vessels = response.data.geometricAnalyse.vessels
          // stop loading
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
    },
    editConclusion () {
      this.axios.post('v1/analyse/detail/conclusion', {id: this.analyse.analyse_base_info.info.id, conclusion: this.editConclusionContent})
        .then((response) => {
          this.dialogEditConclusion = false
          this.analyse.analyse_base_info.info.conclusion = response.data.conclusion
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Изменения заключения сохранены',
              timeout: 3000
            })
        })
        .catch(() => {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Ошибка изменения заключения',
              timeout: 5000
            })
        })
    },
    deleteAnalyseConfirmed () {
      this.axios.delete('v1/analyse/' + this.analyse.analyse_base_info.info.id)
        .then(() => {
          this.dialogDeleteAnalyse = false
          this.$router.replace({path: '/analyses'})
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Анализ успешно удалён',
              timeout: 3000
            })
        })
        .catch(() => {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Ошибка изменения заключения',
              timeout: 5000
            })
        })
    },
    formatFullDate (date) {
      if (!date) return ''

      const [day, month, year, hour, minutes, seconds] = date.split('-')
      var thisHour = Number(hour) + 4
      return `${day}-${month}-${year} ${thisHour}:${minutes}:${seconds}`
    }
  }
}
</script>

<style scoped>

</style>

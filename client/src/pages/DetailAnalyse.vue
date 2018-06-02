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
                <h3>№ анализа</h3>
                <div class="headline mb-0">{{ analyse.analyse_base_info.info.id }}</div>
                <h3>Дата</h3>
                <div class="headline mb-0">{{ formatFullDate(analyse.analyse_base_info.info.analyseDate) }}</div>
              </v-flex>
            </v-layout>
          </v-card-title>
          <v-card-title primary-title>
          <v-layout id="pdf" v-if="!loading_analyse" row justify-space-between>
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
              <h3 class="headline mb-0">Тип анализа:</h3>
              <div>{{ analyse.analyse_base_info.info.analyseType }}</div>
              <h3 class="headline mb-0">Краткое описание:</h3>
              <div>{{ analyse.analyse_base_info.info.shortDescription }}</div>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.fullDescription != null && analyse.analyse_base_info.info.fullDescription.length > 0">Подробное описание:</h3>
              <div v-if="analyse.analyse_base_info.info.fullDescription != null && analyse.analyse_base_info.info.fullDescription.length > 0">{{ analyse.analyse_base_info.info.fullDescription }}</div>
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
            <v-dialog v-model="dialogSavePDF" max-width="290">
              <v-card>
                <v-card-title class="headline">Подтвердите действие</v-card-title>
                <v-card-text>Вы действительно хотите сохранить результат анализа в PDF?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" flat="flat" @click.native="dialogSavePDF = false">Отмена</v-btn>
                  <v-btn color="blue darken-1" flat="flat" @click="generateAndSavePDF()">Сохранить</v-btn>
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
                      :rules="rules.conclusion"
                      :counter="1000"
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
          <v-btn color="orange" @click="dialogSavePDF = true" fab dark>
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
import pdfMake from 'pdfmake/build/pdfmake'
import pdfFonts from 'pdfmake/build/vfs_fonts'
pdfMake.vfs = pdfFonts.pdfMake.vfs

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
    dialogSavePDF: false,
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
    },
    rules: {
      conclusion: [
        v => v.length <= 1000 || 'Превышена допустимая длина'
      ]
    },
    // data to pdf from tabs
    originalBase64Img: '',
    binarizedBase64Img: '',
    skelBase64Img: '',
    ishemiaBase64Img: '',
    densityBase64Img: '',
    result: [
      {
        sum_id: 0,
        sum_count_of_branches: 0,
        avg_tortuosity_degree: 0,
        avg_branching_degree: 0,
        sum_area: 0,
        sum_area_percent: 0
      }
    ]
  }),
  mounted () {
    this.$root.$on('original', (original) => {
      this.originalBase64Img = original
    })
    this.$root.$on('binarized', (binarized) => {
      this.binarizedBase64Img = binarized
    })
    this.$root.$on('skel', (skel) => {
      this.skelBase64Img = skel
    })
    this.$root.$on('ishemia', (skel) => {
      this.ishemiaBase64Img = skel
    })
    this.$root.$on('density', (skel) => {
      this.densityBase64Img = skel
    })
  },
  computed: {
    sumId: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumId, item) {
        return sumId + 1
      }, 0)
    },
    sumCountOfBranches: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumCountOfBranches, item) {
        return sumCountOfBranches + item.countOfBranches
      }, 0)
    },
    avgTortuosityDegree: function () {
      var total = this.analyse.result.geometric_analyse.vessels.length
      return this.analyse.result.geometric_analyse.vessels.reduce(function (avgTortuosityDegree, item) {
        return (avgTortuosityDegree + (item.tortuosityDegree / total))
      }, 0)
    },
    avgBranchingDegree: function () {
      var total = this.analyse.result.geometric_analyse.vessels.length
      return this.analyse.result.geometric_analyse.vessels.reduce(function (avgBranchingDegree, item) {
        return (avgBranchingDegree + (item.branchingDegree / total))
      }, 0)
    },
    sumArea: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumArea, item) {
        return (sumArea + item.area)
      }, 0)
    },
    sumAreaPercent: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumAreaPercent, item) {
        return (sumAreaPercent + item.areaPercent)
      }, 0)
    }
  },
  methods: {
    loadDetailAnalyse () {
      this.axios.get('v1/analyse/detail/' + this.detail_analyse_id)
        .then((response) => {
          console.log(response)
          this.analyse.analyse_base_info.patient = response.data.patient
          this.analyse.analyse_base_info.info = response.data.info
          this.analyse.result.geometric_analyse = response.data.geometricAnalyse
          this.analyse.result.bloodFlowAnalyse = response.data.analyseBloodFlowResponse
          this.analyse.result.username = response.data.username
          this.editConclusionContent = this.analyse.analyse_base_info.info.conclusion
          for (var i = 0; i < this.analyse.result.geometric_analyse.vessels.length; i++) {
            this.analyse.result.geometric_analyse.vessels[i].vesselImageBase64 = ''
            this.analyse.result.geometric_analyse.vessels[i].mainVesselImageBase64 = ''
          }
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
      if (this.editConclusionContent.length <= 1000) {
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
      }
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
              message: 'Ошибка при удалении',
              timeout: 5000
            })
        })
    },
    formatFullDate (date) {
      if (!date) return ''

      const [day, month, year, hour, minutes, seconds] = date.split('-')
      return `${day}.${month}.${year} ${hour}:${minutes}:${seconds}`
    },
    generateAndSavePDF () {
      this.dialogSavePDF = false
      // generate pdf
      var ishemiaTableData = []
      var dataRowIshemia = []
      dataRowIshemia.push('Номер ишемической зоны')
      dataRowIshemia.push('Площадь (px)')
      dataRowIshemia.push('x (px)')
      dataRowIshemia.push('y (px)')
      ishemiaTableData.push(dataRowIshemia)
      this.analyse.result.bloodFlowAnalyse.ischemias.forEach(function (row) {
        dataRowIshemia = []
        dataRowIshemia.push(row.zoneNumber)
        dataRowIshemia.push(row.area)
        dataRowIshemia.push(Math.round(row.x * 100) / 100)
        dataRowIshemia.push(Math.round(row.y * 100) / 100)
        ishemiaTableData.push(dataRowIshemia)
      })

      var densityTableData = []
      var dataRowDensity = []
      dataRowDensity.push('Номер сектора')
      dataRowDensity.push('Плотность (px)')
      densityTableData.push(dataRowDensity)
      this.analyse.result.bloodFlowAnalyse.densities.forEach(function (row) {
        dataRowDensity = []
        dataRowDensity.push(row.sectorNumber)
        dataRowDensity.push(Math.round(row.density * 100) / 100)
        densityTableData.push(dataRowDensity)
      })

      var docDefinition = {
        info: {
          title: 'Анализ № ' + this.analyse.analyse_base_info.info.id + ' от ' + this.formatFullDate(this.analyse.analyse_base_info.info.analyseDate),
          author: this.$auth.user().email,
          subject: '',
          keywords: ''
        },

        pageSize: 'A4',
        pageOrientation: 'portrait',
        pageMargins: [50, 50, 50, 50],

        header: function (currentPage, pageCount) {
          return {
            text: currentPage.toString() + ' из ' + pageCount,
            alignment: 'right',
            margin: [0, 25, 25, 0]
          }
        },

        footer: {
          text: '\u00A9' + ' AngioVision',
          alignment: 'center',
          margin: [0, 0, 0, 25]
        },

        content: [
          {
            text: 'Анализ № ' + this.analyse.analyse_base_info.info.id + ' от ' + this.formatFullDate(this.analyse.analyse_base_info.info.analyseDate),
            alignment: 'center',
            fontSize: 24
          },
          {
            text: 'Основные сведения',
            alignment: 'center',
            fontSize: 20,
            margin: [0, 5, 0, 0]
          },
          {
            margin: [0, 5, 0, 0],
            columns: [
              [
                {
                  width: '40%',
                  text: 'Информация о пациенте',
                  alignment: 'center',
                  fontSize: 16
                },
                '- ФИО: ' + this.analyse.analyse_base_info.patient.lastname + ' ' +
                this.analyse.analyse_base_info.patient.firstname + ' ' +
                this.analyse.analyse_base_info.patient.patronymic,
                '- E-mail: ' + this.analyse.analyse_base_info.patient.email,
                '- Телефон: ' + this.analyse.analyse_base_info.patient.phone,
                '- Страховой полис: ' + this.analyse.analyse_base_info.patient.policy,
                '- Дата рождения: ' + this.analyse.analyse_base_info.patient.bday,
                '- Место жительства: ' + this.analyse.analyse_base_info.patient.address,
                '- Место работы/учёбы: ' + this.analyse.analyse_base_info.patient.work,
                '- Комментарий: ' + this.analyse.analyse_base_info.patient.comment
              ],
              [
                {
                  width: '60%',
                  text: 'Информация о болезни',
                  alignment: 'center',
                  fontSize: 16
                },
                '- Название: ' + this.analyse.analyse_base_info.info.name,
                '- Тип анализа: ' + this.analyse.analyse_base_info.info.analyseType,
                '- Краткое описание: ' + this.analyse.analyse_base_info.info.shortDescription,
                '- Полное описание: ' + this.analyse.analyse_base_info.info.fullDescription,
                '- Комментарий: ' + this.analyse.analyse_base_info.info.comment
              ]
            ]
          },
          {
            text: 'Результаты',
            alignment: 'center',
            fontSize: 20,
            margin: [0, 15, 0, 0]
          },
          {
            text: 'Геометрическая характеристика СС',
            alignment: 'center',
            fontSize: 18,
            margin: [0, 5, 0, 0]
          },
          {
            margin: [0, 5, 0, 0],
            columns: [
              [
                {
                  width: '33%',
                  text: 'Оригинальное изображение',
                  alignment: 'center',
                  fontSize: 14
                },
                {
                  width: 150,
                  alignment: 'center',
                  image: this.originalBase64Img
                }
              ],
              [
                {
                  width: '33%',
                  text: 'Бинаризованное изображение',
                  alignment: 'center',
                  fontSize: 14
                },
                {
                  width: 150,
                  alignment: 'center',
                  image: this.binarizedBase64Img
                }
              ],
              [
                {
                  width: '33%',
                  text: 'Скелетизованное изображение',
                  alignment: 'center',
                  fontSize: 14
                },
                {
                  width: 150,
                  alignment: 'center',
                  image: this.skelBase64Img
                }
              ]
            ]
          },
          {
            text: 'Результаты анализа сосудистой системы глаза по отдельным сосудам',
            alignment: 'center',
            fontSize: 16,
            margin: [0, 5, 0, 0]
          },
          {
            margin: [0, 5, 0, 0],
            table: {
              widths: [ 'auto', 'auto', 'auto', 'auto', 'auto', 'auto' ],
              body: [
                [ 'Кол-во сосудов', 'Кол-во ветвей', 'Ср. извилистость', 'Ср. ветвистость', 'Общая S (px)', 'Общая S (%)' ],
                [ '' + this.sumId, '' + this.sumCountOfBranches, '' + this.avgTortuosityDegree.toFixed(4), '' + this.avgBranchingDegree.toFixed(4), '' + this.sumArea.toFixed(4), '' + this.sumAreaPercent.toFixed(4) ]
              ]
            },
            pageBreak: 'after'
          },
          {
            text: 'Степень кровоснабжения СС',
            alignment: 'center',
            fontSize: 18,
            margin: [0, 5, 0, 0]
          },
          {
            text: 'Ишемические зоны и макула',
            alignment: 'center',
            fontSize: 14,
            margin: [0, 5, 0, 0]
          },
          {
            margin: [0, 5, 0, 0],
            columns: [
              [
                {
                  width: 250,
                  alignment: 'center',
                  image: this.ishemiaBase64Img
                }
              ],
              [
                {
                  margin: [5, 0, 0, 0],
                  width: '*',
                  text: 'Площадь макулы: ' + this.analyse.result.bloodFlowAnalyse.makula.area
                },
                {
                  margin: [5, 0, 0, 0],
                  width: '*',
                  text: 'Радиус макулы: ' + (Math.round(this.analyse.result.bloodFlowAnalyse.makula.radius * 100) / 100)
                },
                {
                  margin: [5, 0, 0, 0],
                  width: '*',
                  text: 'Центр макулы: ' + (Math.round(this.analyse.result.bloodFlowAnalyse.makula.x * 100) / 100 + ' ; ' + Math.round(this.analyse.result.bloodFlowAnalyse.makula.y * 100) / 100)
                },
                {
                  margin: [5, 5, 0, 0],
                  table: {
                    widths: [ 'auto', 'auto', 'auto', 'auto' ],
                    body: ishemiaTableData
                  }
                }
              ]
            ]
          },
          {
            text: 'Плотность сосудов в области макулы',
            alignment: 'center',
            fontSize: 14,
            margin: [0, 5, 0, 0]
          },
          {
            margin: [0, 5, 0, 0],
            columns: [
              [
                {
                  width: 250,
                  alignment: 'center',
                  image: this.densityBase64Img
                }
              ],
              [
                {
                  margin: [5, 5, 0, 0],
                  table: {
                    widths: [ 'auto', 'auto' ],
                    alignment: 'center',
                    body: densityTableData
                  }
                }
              ]
            ]
          },
          {
            text: 'Заключение',
            alignment: 'center',
            fontSize: 20,
            margin: [0, 15, 0, 0]
          },
          {
            text: '' + this.analyse.analyse_base_info.info.conclusion,
            fontSize: 12,
            margin: [0, 5, 0, 0]
          }
        ]
      }
      pdfMake.createPdf(docDefinition).open()
    }
  }
}
</script>

<style scoped>

</style>

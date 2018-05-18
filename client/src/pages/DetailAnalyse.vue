<template lang="html">
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
        <h1>Подробный анализ</h1>
        <h2>Основные сведения</h2>
        <v-card id="inspire" dark>
          <v-card-title primary-title>
            <v-progress-linear v-if="loading_analyse" :indeterminate="true"></v-progress-linear>
            <v-layout v-if="!loading_analyse" justify-space-between>
              <v-flex xs4 md4 lg4>
                <h3>Дата</h3>
                <div class="headline mb-0">{{ analyse.analyse_base_info.info.analyse_date }}</div>
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
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.patient.comment != null">Комментарий:</h3>
              <div v-if="analyse.analyse_base_info.patient.comment != null">{{ analyse.analyse_base_info.patient.comment }}</div>
            </v-flex>
            <v-flex xs5 md5 lg5>
              <h3>Информация о болезни</h3>
              <h3 class="headline mb-0">Название:</h3>
              <div>{{ analyse.analyse_base_info.info.name }}</div>
              <h3 class="headline mb-0">Краткое описание:</h3>
              <div>{{ analyse.analyse_base_info.info.short_description }}</div>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.full_description != null">Подробное описание:</h3>
              <div v-if="analyse.analyse_base_info.info.full_description != null">{{ analyse.analyse_base_info.info.full_description }}</div>
              <h3 class="headline mb-0">Тип анализа:</h3>
              <div>{{ analyse.analyse_base_info.info.analyse_type }}</div>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.comment != null">Комментарий:</h3>
              <div v-if="analyse.analyse_base_info.info.comment != null">{{ analyse.analyse_base_info.info.comment }}</div>
            </v-flex>
            <v-flex xs6 md6 lg6>
              <h3>Заключение о болезни</h3>
              <h3 class="headline mb-0" v-if="analyse.analyse_base_info.info.conclusion == null || analyse.analyse_base_info.info.conclusion.length <= 0">Заключение отсутствует</h3>
              <div v-if="analyse.analyse_base_info.info.conclusion != null">{{ analyse.analyse_base_info.info.conclusion }}</div>
            </v-flex>
            <v-dialog v-model="dialogDelete" max-width="290">
              <v-card>
                <v-card-title class="headline">Подтвердите действие</v-card-title>
                <v-card-text>Вы действительно хотите удалить текущий сосуд из анализа?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" flat="flat" @click.native="dialogDelete = false">Отмена</v-btn>
                  <v-btn color="blue darken-1" flat="flat" @click="deleteItemConfirmed()">Удалить</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
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
        <v-btn block color="transparent" :disabled="$auth.user().email == analyse.username" @click.native="dialogEditConclusion = true" dark>Редактировать заключение</v-btn>
      </v-card>
      <h2>Результаты анализа</h2>
      <div>
        <v-tabs v-model="active" dark centered>
          <v-tab ripple>
            Геометрическая характеристика СС
          </v-tab>
          <!-- TODO move tab to a separate component -->
          <v-tab-item>
            <v-card flat>
              <v-container grid-list-md text-xs-center>
                <v-layout row wrap>
                  <v-flex xs4>
                    <v-card dark>
                      <v-card-text>Оригинальное изображение</v-card-text>
                      <img v-img style="width: 90%;" v-bind:src="'http://localhost/api/v1/image?filename=' + analyse.result.geometric_analyse.original_image">
                    </v-card>
                  </v-flex>
                  <v-flex xs4>
                    <v-card dark>
                      <v-card-text>Бинаризованное изображение</v-card-text>
                      <img v-img style="width: 90%;" v-bind:src="'http://localhost/api/v1/image?filename=' + analyse.result.geometric_analyse.binarized_image">
                    </v-card>
                  </v-flex>
                  <v-flex xs4>
                    <v-card dark>
                      <v-card-text>Скелетизованное изображение</v-card-text>
                      <img v-img style="width: 90%;" v-bind:src="'http://localhost/api/v1/image?filename=' + analyse.result.geometric_analyse.skel_image">
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-container>
              <v-container grid-list-md text-xs-center>
                <v-card-text>Анализ сосудистой системы глаза по отдельным сосудам</v-card-text>
                <v-data-table
                  :headers="headers"
                  :items="analyse.result.geometric_analyse.vessels"
                  hide-actions
                  class="elevation-1"
                  >
                  <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.id }}</td>
                    <td><img v-img style="width: 100px;" v-bind:src="'http://localhost/api/v1/image?filename=' + props.item.vessel_image"></td>
                    <td><img v-img style="width: 100px;" v-bind:src="'http://localhost/api/v1/image?filename=' + props.item.main_vessel_image"></td>
                    <td>{{ props.item.count_of_branches }}</td>
                    <td class="text-xs-right">{{ props.item.tortuosity_degree }}</td>
                    <td class="text-xs-right">{{ props.item.branching_degree }}</td>
                    <td class="text-xs-right">{{ props.item.area }}</td>
                    <td class="text-xs-right">{{ props.item.area_percent }}</td>
                    <td class="center">
                      <v-btn icon class="mx-0" @click="deleteItem(props.item)">
                        <v-icon color="pink">delete</v-icon>
                      </v-btn>
                    </td>
                  </template>
                </v-data-table>
                <v-card-text>Результаты анализа сосудистой системы глаза по отдельным сосудам </v-card-text>
                <v-data-table
                  :headers="headers_result"
                  :items="result"
                  hide-actions
                  class="elevation-1"
                  >
                  <template slot="items" slot-scope="props">
                    <td>{{ sumId }}</td>
                    <td>{{ sumCountOfBranches }}</td>
                    <td class="text-xs-right">{{ avgTortuosityDegree.toFixed(4) }}</td>
                    <td class="text-xs-right">{{ avgBranchingDegree.toFixed(4) }}</td>
                    <td class="text-xs-right">{{ sumArea.toFixed(4) }}</td>
                    <td class="text-xs-right">{{ sumAreaPercent.toFixed(4) }}</td>
                  </template>
                </v-data-table>
              </v-container>
            </v-card>
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
        <v-btn block color="red" :disabled="false" @click="dialogDeleteAnalyse = true" dark>Удалить анализ</v-btn>
      </div>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
import BloodFlowAnalyseTab from '../components/BloodFlowAnalyseTab'
export default {
  name: 'DetailAnalyse',
  components: {BloodFlowAnalyseTab},
  created () {
    this.detail_analyse_id = this.$store.test_id
    if (this.detail_analyse_id === undefined) {
      this.detail_analyse_id = parseInt(localStorage.getItem('detail_analyse_id'))
    } else {
      localStorage.setItem('detail_analyse_id', this.detail_analyse_id + '')
    }
    this.loadDetailAnalyse()
  },
  data: () => ({
    detail_analyse_id: 0,
    active: null,
    dialogDelete: false,
    dialogEditConclusion: false,
    dialogDeleteAnalyse: false,
    editConclusionContent: '',
    delete_index: -1,
    delete_vessel_id: -1,
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
          short_description: '',
          full_description: '',
          analyse_type: '',
          comment: '',
          analyse_date: '',
          conclusion: ''
        }
      },
      result: {
        geometric_analyse: {
          original_image: '',
          binarized_image: '',
          skel_image: '',
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
    result: [
      {
        sum_id: 0,
        sum_count_of_branches: 0,
        avg_tortuosity_degree: 0,
        avg_branching_degree: 0,
        sum_area: 0,
        sum_area_percent: 0
      }
    ],
    headers: [
      { text: '№', align: 'left', value: 'id', width: '5%' },
      { text: 'Сосуд', align: 'left', value: 'vessel_image', sortable: false, width: '20%' },
      { text: 'Русло', align: 'left', value: 'main_vessel_image', sortable: false, width: '20%' },
      { text: 'Ветви', align: 'left', value: 'count_of_branches', width: '10%' },
      { text: 'Извилистость', align: 'left', value: 'tortuosity_degree', width: '10%' },
      { text: 'Ветвистость', align: 'left', value: 'branching_degree', width: '10%' },
      { text: 'S (px)', align: 'left', value: 'area', width: '10%' },
      { text: 'S (%)', align: 'left', value: 'area_percent', width: '10%' },
      { text: 'Действия', align: 'left', value: 'actions', sortable: false, width: '5%' }
    ],
    headers_result: [
      { text: 'Количество сосудов', align: 'left', value: 'sum_id', sortable: false, width: '20%' },
      { text: 'Количество ветвей', align: 'left', value: 'sum_count_of_branches', sortable: false, width: '20%' },
      { text: 'Средняя извилистость', align: 'left', value: 'avg_tortuosity_degree', sortable: false, width: '20%' },
      { text: 'Средняя ветвистость', align: 'left', value: 'avg_branching_degree', sortable: false, width: '200%' },
      { text: 'Общая S (px)', align: 'left', value: 'sum_area', sortable: false, width: '10%' },
      { text: 'Общая S (%)', align: 'left', value: 'sum_area_percent', sortable: false, width: '10%' }
    ]
  }),
  methods: {
    loadDetailAnalyse () {
      this.axios.post('v1/analyse/detail', {id: this.detail_analyse_id})
        .then((response) => {
          this.analyse.analyse_base_info.patient = response.data.patient
          this.analyse.analyse_base_info.info = response.data.info
          this.analyse.result.geometric_analyse = response.data.geometric_analyse
          this.analyse.result.bloodFlowAnalyse = response.data.analyseBloodFlowResponse
          this.analyse.result.username = response.data.username
          this.editConclusionContent = this.analyse.analyse_base_info.info.conclusion
          this.loading_analyse = false
          console.log(response)
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
    deleteItem (item) {
      const index = this.analyse.result.geometric_analyse.vessels.indexOf(item)
      this.delete_index = index
      this.delete_vessel_id = item.id
      this.dialogDelete = true
    },
    deleteItemConfirmed () {
      this.axios.delete('v1/analyse/vessel/' + this.delete_vessel_id)
        .then(() => {
          this.analyse.result.geometric_analyse.vessels.splice(this.delete_index, 1)
          this.dialogDelete = false
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Сосуд успешно удалён',
              timeout: 3000
            })
        })
        .catch(() => {
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Ошибка удаления сосуда',
              timeout: 5000
            })
        })
    },
    editConclusion () {
      this.axios.put('v1/analyse/detail/conclusion', {id: this.analyse.analyse_base_info.info.id, conclusion: this.editConclusionContent})
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
    }
  },
  computed: {
    sumId: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumId, item) {
        return sumId + 1
      }, 0)
    },
    sumCountOfBranches: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumCountOfBranches, item) {
        return sumCountOfBranches + item.count_of_branches
      }, 0)
    },
    avgTortuosityDegree: function () {
      var total = this.analyse.result.geometric_analyse.vessels.length
      return this.analyse.result.geometric_analyse.vessels.reduce(function (avgTortuosityDegree, item) {
        return (avgTortuosityDegree + (item.tortuosity_degree / total))
      }, 0)
    },
    avgBranchingDegree: function () {
      var total = this.analyse.result.geometric_analyse.vessels.length
      return this.analyse.result.geometric_analyse.vessels.reduce(function (avgBranchingDegree, item) {
        return (avgBranchingDegree + (item.branching_degree / total))
      }, 0)
    },
    sumArea: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumArea, item) {
        return (sumArea + item.area)
      }, 0)
    },
    sumAreaPercent: function () {
      return this.analyse.result.geometric_analyse.vessels.reduce(function (sumAreaPercent, item) {
        return (sumAreaPercent + item.area_percent)
      }, 0)
    }
  }
}
</script>

<style scoped>

</style>

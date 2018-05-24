<template>
  <v-card flat>
    <v-container grid-list-md text-xs-center>
      <v-layout row wrap>
        <v-flex xs4>
          <v-card dark>
            <v-card-text>Оригинальное изображение</v-card-text>
            <img v-img style="width: 90%;" v-bind:src="originalBase64Img">
          </v-card>
        </v-flex>
        <v-flex xs4>
          <v-card dark>
            <v-card-text>Бинаризованное изображение</v-card-text>
            <img v-img style="width: 90%;" v-bind:src="binarizedBase64Img">
          </v-card>
        </v-flex>
        <v-flex xs4>
          <v-card dark>
            <v-card-text>Скелетизованное изображение</v-card-text>
            <img v-img style="width: 90%;" v-bind:src="skelBase64Img">
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
    <v-container grid-list-md text-xs-center>
      <v-card-text>Анализ сосудистой системы глаза по отдельным сосудам</v-card-text>
      <v-data-table
        :headers="headers"
        :items="vessels"
        class="elevation-1"
        :pagination.sync="pagination"
        >
        <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
        <template slot="items" slot-scope="props">
          <td>{{ props.item.id }}</td>
          <td><img v-img style="width: 90px;" v-bind:src="props.item.vesselImageBase64"></td>
          <td><img v-img style="width: 90px;" v-bind:src="props.item.mainVesselImageBase64"></td>
          <td>{{ props.item.countOfBranches }}</td>
          <td class="text-xs-right">{{ props.item.tortuosityDegree }}</td>
          <td class="text-xs-right">{{ props.item.branchingDegree }}</td>
          <td class="text-xs-right">{{ props.item.area }}</td>
          <td class="text-xs-right">{{ props.item.areaPercent }}</td>
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
    </v-container>
  </v-card>
</template>

<script>
export default {
  name: 'GeometricAnalyseTab',
  props: ['geometric'],
  data: () => ({
    pagination: {
      sortBy: 'id'
    },
    dialogDelete: false,
    delete_index: -1,
    delete_vessel_id: -1,
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
      { text: 'Сосуд', align: 'left', value: 'vesselImage', sortable: false, width: '20%' },
      { text: 'Русло', align: 'left', value: 'mainVesselImage', sortable: false, width: '20%' },
      { text: 'Ветви', align: 'left', value: 'countOfBranches', width: '10%' },
      { text: 'Извилистость', align: 'left', value: 'tortuosityDegree', width: '10%' },
      { text: 'Ветвистость', align: 'left', value: 'branchingDegree', width: '10%' },
      { text: 'S (px)', align: 'left', value: 'area', width: '10%' },
      { text: 'S (%)', align: 'left', value: 'areaPercent', width: '10%' },
      { text: 'Действия', align: 'left', value: 'actions', sortable: false, width: '5%' }
    ],
    headers_result: [
      { text: 'Количество сосудов', align: 'left', value: 'sum_id', sortable: false, width: '20%' },
      { text: 'Количество ветвей', align: 'left', value: 'sum_count_of_branches', sortable: false, width: '20%' },
      { text: 'Средняя извилистость', align: 'left', value: 'avg_tortuosity_degree', sortable: false, width: '20%' },
      { text: 'Средняя ветвистость', align: 'left', value: 'avg_branching_degree', sortable: false, width: '200%' },
      { text: 'Общая S (px)', align: 'left', value: 'sum_area', sortable: false, width: '10%' },
      { text: 'Общая S (%)', align: 'left', value: 'sum_area_percent', sortable: false, width: '10%' }
    ],
    // src
    originalImageSrc: '',
    binarizedImageSrc: '',
    skelImageSrc: '',
    // base64 images
    originalBase64Img: '',
    binarizedBase64Img: '',
    skelBase64Img: '',
    // vessels
    vessels: []
  }),
  watch: {
    geometric (newVal) {
      this.geometric = newVal
      this.originalImageSrc = newVal.originalImage
      this.binarizedImageSrc = newVal.binarizedImage
      this.skelImageSrc = newVal.skelImage
      this.vessels = newVal.vessels
    },
    vessels: {
      immediate: true,
      handler: function (val, oldVal) {
        this.vessels = val
        this.vessels.forEach((item) => {
          this.axios.get(item.vesselImage, { responseType: 'arraybuffer' })
            .then(function(response) {
              const base64 = btoa(
              new Uint8Array(response.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ''
              ))
              item.vesselImageBase64 = 'data:;base64,' + base64
              console.log('data:;base64,' + base64)
              $this.vessels.push(item)
            })
            .catch(function(error) {
              console.log(error);
            });
          })
        this.vessels.forEach((item) => {
          this.axios.get(item.mainVesselImage, { responseType: 'arraybuffer' })
            .then(function(response) {
              const base64 = btoa(
              new Uint8Array(response.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ''
              ))
              item.mainVesselImageBase64 = 'data:;base64,' + base64
              console.log('data:;base64,' + base64)
              $this.vessels.push(item)
            })
            .catch(function(error) {
              console.log(error);
            });
          })
      }
    },
    originalImageSrc (newVal) {
      this.originalImageSrc = newVal
      this.axios.get(newVal, { responseType: 'arraybuffer' })
        .then(response => {
          const base64 = btoa(
            new Uint8Array(response.data).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
            ))
          this.originalBase64Img = 'data:;base64,' + base64
        })
    },
    binarizedImageSrc (newVal) {
      this.binarizedImageSrc = newVal
      this.axios.get(newVal, { responseType: 'arraybuffer' })
        .then(response => {
          const base64 = btoa(
            new Uint8Array(response.data).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
            ))
          this.binarizedBase64Img = 'data:;base64,' + base64
        })
    },
    skelImageSrc (newVal) {
      this.skelImageSrc = newVal
      this.axios.get(newVal, { responseType: 'arraybuffer' })
        .then(response => {
          const base64 = btoa(
            new Uint8Array(response.data).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
            ))
          this.skelBase64Img = 'data:;base64,' + base64
        })
    }
  },
  methods: {
    downloadImage (src) {
      this.axios.get(src, { responseType: 'arraybuffer' })
        .then(response => {
          const base64 = btoa(
            new Uint8Array(response.data).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
            ))
          return 'data:;base64,' + base64
        })
        .catch(() => {
          console.log('err')
        })
    },
    deleteItem (item) {
      const index = this.geometric.vessels.indexOf(item)
      this.delete_index = index
      this.delete_vessel_id = item.id
      this.dialogDelete = true
    },
    deleteItemConfirmed () {
      this.axios.delete('v1/analyse/vessel/' + this.delete_vessel_id)
        .then(() => {
          this.geometric.vessels.splice(this.delete_index, 1)
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
    }
  },
  computed: {
    sumId: function () {
      return this.geometric.vessels.reduce(function (sumId, item) {
        return sumId + 1
      }, 0)
    },
    sumCountOfBranches: function () {
      return this.geometric.vessels.reduce(function (sumCountOfBranches, item) {
        return sumCountOfBranches + item.countOfBranches
      }, 0)
    },
    avgTortuosityDegree: function () {
      var total = this.geometric.vessels.length
      return this.geometric.vessels.reduce(function (avgTortuosityDegree, item) {
        return (avgTortuosityDegree + (item.tortuosityDegree / total))
      }, 0)
    },
    avgBranchingDegree: function () {
      var total = this.geometric.vessels.length
      return this.geometric.vessels.reduce(function (avgBranchingDegree, item) {
        return (avgBranchingDegree + (item.branchingDegree / total))
      }, 0)
    },
    sumArea: function () {
      return this.geometric.vessels.reduce(function (sumArea, item) {
        return (sumArea + item.area)
      }, 0)
    },
    sumAreaPercent: function () {
      return this.geometric.vessels.reduce(function (sumAreaPercent, item) {
        return (sumAreaPercent + item.areaPercent)
      }, 0)
    }
  }
}
</script>

<style scoped>

</style>

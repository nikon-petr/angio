<template>
  <v-card flat>
    <v-container grid-list-md text-xs-center text-sm-center>
      <v-layout row wrap>
        <v-flex xs12>
          <v-card flat>
            <v-card-text>
              <div class="display-1">Ишемические зоны и макула</div>
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs12 md6>
          <v-card flat>
            <v-card-text>
              <img v-img v-bind:width="imageHeight" v-bind:src="ishemiaBase64Img">
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs12 md6 text-xs-left>
          <v-card class="mb-3">
            <v-card-text>
              Площадь макулы: {{ makula.area }} <br>
              Радиус макулы: {{ Math.round(makula.radius * 100) / 100 }} <br>
              Центр макулы: {{ Math.round(makula.x * 100) / 100 + " ; " + Math.round(makula.y * 100) / 100 }}
            </v-card-text>
          </v-card>
          <v-data-table
            :headers="ishemiaTableHeaders"
            :items="ishemiaArr"
            class="elevation-1"
            :rows-per-page-items="rowNumbersIshemia"
            no-data-text="Ишемических зон не найдено"
          >
            <template slot="items" slot-scope="props">
              <td>{{ props.item.zoneNumber }}</td>
              <td class="text-xs-center">{{ props.item.area }}</td>
              <td class="text-xs-center">{{ Math.round(props.item.x * 100) / 100 }}</td>
              <td class="text-xs-center">{{ Math.round(props.item.y * 100) / 100 }}</td>
            </template>
            <template slot="pageText" slot-scope="props" >
              Строки {{ props.pageStart }} - {{ props.pageStop }} из {{ props.itemsLength }}
            </template>
          </v-data-table>
        </v-flex>
        <v-flex xs12>
          <v-card flat>
            <v-card-text>
              <div class="display-1">Плотность сосудов в области макулы</div>
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs12 md6>
          <v-card flat>
            <v-card-text>
              <img v-img v-bind:width="imageHeight" v-bind:src="densityBase64Img">
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs12 md6 text-xs-left>
          <v-data-table
            :headers="densityTableHeaders"
            :items="densityArr"
            class="elevation-1"
            hide-actions
          >
            <template slot="items" slot-scope="props">
              <td>{{ props.item.sectorNumber }}</td>
              <td class="text-xs-center">{{ Math.round(props.item.density  * 100) / 100 }}</td>
            </template>
          </v-data-table>
        </v-flex>
      </v-layout>
    </v-container>
  </v-card>
</template>

<script>
export default {
  name: 'BloodFlowAnalyseTab',
  props: ['ishemiaSrc', 'densitySrc', 'ishemiaArr', 'makula', 'densityArr'],
  data: () => ({
    ishemiaTableHeaders: [
      {text: 'Номер ишемической зоны', value: 'zoneNumber', sortable: true},
      {text: 'Площадь (px)', value: 'area', sortable: true},
      {text: 'x (px)', sortable: false},
      {text: 'y (px)', sortable: false}
    ],
    densityTableHeaders: [
      {text: 'Номер сектора', value: 'zoneNumber', sortable: true},
      {text: 'Плотность (px)', value: 'density', sortable: true}
    ],
    rowNumbersIshemia: [5],
    ishemiaBase64Img: '',
    densityBase64Img: ''
  }),
  watch: {
    ishemiaSrc (newVal) {
      this.ishemiaSrc = newVal
      this.axios.get(newVal, { responseType: 'arraybuffer' })
        .then(response => {
          const base64 = btoa(
            new Uint8Array(response.data).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
            ))
          this.ishemiaBase64Img = 'data:;base64,' + base64
        })
    },
    densitySrc (newVal) {
      this.densitySrc = newVal
      this.axios.get(newVal, { responseType: 'arraybuffer' })
        .then(response => {
          const base64 = btoa(
            new Uint8Array(response.data).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
            ))
          this.densityBase64Img = 'data:;base64,' + base64
        })
    }
  },
  computed: {
    imageHeight () {
      switch (this.$vuetify.breakpoint.name) {
        case 'xs': return '400px'
        case 'sm': return '500px'
        case 'md': return '500px'
        case 'lg': return '500px'
        case 'xl': return '600px'
      }
    }
  }
}
</script>

<style scoped>

</style>

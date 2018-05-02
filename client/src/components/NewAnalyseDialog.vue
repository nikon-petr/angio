<template>
  <v-layout>
    <v-flex>
      <v-app id="inspire" dark>
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
              <v-btn dark flat :disabled="new_analyse.info.img.length <= 0" @click="startNewAnalyse()">Запустить</v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text>
            <v-form v-model="valid" ref="form_new_analyse">
              <v-layout row justify-space-between>
                <v-flex xs5>
                  <h2>Информация о пациенте</h2>
                  <small>Для добавления информации о пациенте к описанию анализа, в первую очередь, убедитесь в том,
                    что пациент отсутствует в базе. Для этого введите номер страхового полиса.
                    Если пациент уже имеется в базе - поля будут заполненны автоматически.
                  </small>
                  <pre></pre>
                  <small>
                    Для обновления информации о пациенте поля можно редактировать.
                  </small>
                  <v-container grid-list-md text-xs-center>
                    <v-layout row wrap>
                      <v-text-field
                        label="Страховой полис (номер)"
                        v-model="new_analyse.patient.policy"
                        :rules="new_analyse_rules.patient.policy"
                        required
                      ></v-text-field>
                      <v-btn dark flat @click="checkPolicy()">Проверить</v-btn>
                    </v-layout>
                    </v-container>
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
                    required
                  ></v-text-field>
                  <v-text-field
                    label="Телефон"
                    v-model="new_analyse.patient.phone"
                    :rules="new_analyse_rules.patient.phone"
                    required
                  ></v-text-field>
                  <v-menu
                    lazy
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    full-width
                    :nudge-right="40"
                    min-width="290px"
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
                    label="Место жительства"
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
                    name="input-4-3"
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
                    name="input-2-1"
                    multi-line
                  ></v-text-field>
                  <v-text-field
                    label="Подробное описание"
                    v-model="new_analyse.info.full_description"
                    name="input-3-2"
                    multi-line
                  ></v-text-field>
                  <v-select
                    label="Тип анализа"
                    v-model="new_analyse.info.analyse_type"
                    :rules="new_analyse_rules.info.default"
                    :items="items_analyse_type"
                    data-vv-name="new_analyse.info.analyse_type"
                    required
                  ></v-select>
                  <v-text-field
                    name="input-2-1"
                    multi-line
                    label="Комментарий"
                    v-model="new_analyse.info.comments"
                  ></v-text-field>
                  <div>
                    Загрузить изображение:
                    <input type="file" @change="previewImage" accept="image/*">
                  </div>
                  <div v-if="new_analyse.info.img.length > 0">
                    <img class="preview" :src="new_analyse.info.img">
                  </div>
                </v-flex>
              </v-layout>
            </v-form>
          </v-card-text>
          <div style="flex: 1 1 auto;"></div>
        </v-card>
        </v-dialog>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
import {newAnalyse, checkPatientByPolicy} from '../api/analyses'

export default {
  name: 'NewAnalyseDialog',
  data: () => ({
    dialog: false,
    valid: false,
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
        comments: '',
        img: ''
      },
      username: 'user@angio.ru'
    },
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
          v => /^\d( ?\d){15,15}$/.test(v) || 'Номер должен содержать 16 цифр'
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
  watch: {
    new_analyse (newVal) {
      this.new_analyse = newVal
    }
  },
  methods: {
    startNewAnalyse () {
      if (this.$refs.form_new_analyse.validate()) {
        console.log(this.new_analyse)
        newAnalyse(this.new_analyse)
          .then(() => {
            this.dialog = false
            this.$root.$emit(
              'showAlert',
              {
                color: 'success',
                message: 'Новый анализ запущен. Ожидайте 2-3 минуты. Статус анализа можно наблюдать с списке анализов.',
                timeout: 15000
              })
          })
          .catch(() => {
            this.$root.$emit(
              'showAlert',
              {
                color: 'error',
                message: 'Ошибка запуска нового анализа',
                timeout: 5000
              })
          })
      }
    },
    close () {
      this.dialog = false
    },
    previewImage: function (event) {
      var input = event.target
      if (input.files && input.files[0]) {
        var reader = new FileReader()
        reader.onload = (e) => {
          this.new_analyse.info.img = e.target.result
        }
        // base64 format
        reader.readAsDataURL(input.files[0])
      }
    },
    checkPolicy () {
      checkPatientByPolicy(this.new_analyse.patient.policy)
        .then((response) => {
          console.log(response.data.contains)
          console.log(response)
          this.new_analyse.patient.firstname = response.data.patient.firstname
          this.new_analyse.patient.lastname = response.data.patient.lastname
          this.new_analyse.patient.patronymic = response.data.patient.patronymic
          this.new_analyse.patient.email = response.data.patient.email
          this.new_analyse.patient.phone = response.data.patient.phone
          this.new_analyse.patient.bday = response.data.patient.bday
          this.new_analyse.patient.address = response.data.patient.address
          this.new_analyse.patient.work = response.data.patient.work
          this.new_analyse.patient.comments = response.data.patient.comments
          this.$root.$emit(
            'showAlert',
            {
              color: 'success',
              message: 'Пациент был успешно найден в базе, данные добавлены',
              timeout: 5000
            })
        })
        .catch(() => {
          this.new_analyse.patient.firstname = ''
          this.new_analyse.patient.lastname = ''
          this.new_analyse.patient.patronymic = ''
          this.new_analyse.patient.email = ''
          this.new_analyse.patient.phone = ''
          this.new_analyse.patient.bday = ''
          this.new_analyse.patient.address = ''
          this.new_analyse.patient.work = ''
          this.new_analyse.patient.comments = ''
          this.$root.$emit(
            'showAlert',
            {
              color: 'error',
              message: 'Пациент с таким номером страхового полиса не был найден в базе',
              timeout: 5000
            })
        })
    }
  },
  mounted () {
    this.$root.$on('showNewAnalyseDialog', () => {
      console.log('show dialog')
      this.dialog = true
    })
  }
}
</script>

<style lang="css">
  .preview {
    height: 200px;
    background-color: white;
    border: 1px solid #DDD;
    padding: 3px;
    margin-top: 20px;
  }
</style>

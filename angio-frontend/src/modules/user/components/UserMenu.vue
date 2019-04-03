<template>
    <div class="text-xs-center">
        <v-menu
                v-model="menu"
                v-bind:close-on-content-click="false"
                min-width="400"
                max-width="300"
                offset-y
                offset-x
                nudge-bottom="25"
                bottom
        >
            <template v-slot:activator="{ on }">
                <v-btn icon flat v-on="on">
                    <v-badge overlap color="info">
                        <template v-slot:badge>
                            <v-icon small>notifications</v-icon>
                        </template>
                        <v-icon>person</v-icon>
                    </v-badge>
                </v-btn>
            </template>

            <v-card>
                <v-list three-line>
                    <v-list-tile avatar>
                        <v-list-tile-avatar color="primary">
                            <span class="white--text headline">П</span>
                        </v-list-tile-avatar>

                        <v-list-tile-content>
                            <v-list-tile-title>Петрунин Никон Дмитриевич</v-list-tile-title>
                            <v-list-tile-sub-title>Клиника глазных болезней СГМУ им. Разумовского</v-list-tile-sub-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>

                <v-divider></v-divider>

                <v-list>
                    <v-list-tile to="/user/settings" v-ripple>
                        <v-list-tile-title>Настройки</v-list-tile-title>
                    </v-list-tile>
                    <v-list-tile v-on:click="" v-ripple>
                        <v-list-tile-title>Выйти из приложения</v-list-tile-title>
                    </v-list-tile>
                    <v-subheader>Уведомления</v-subheader>
                </v-list>

                <v-divider></v-divider>

                <div class="scrollable">
                    <template v-for="notification in notifications">
                        <div>
                            <v-card flat>
                                <v-card-title class="pl-0">
                                    <v-flex xs2 class="text-xs-center">
                                        <v-icon color="success" medium v-if="notification.type === 'success'">
                                            check_circle_outline
                                        </v-icon>
                                        <v-icon color="error" medium v-if="notification.type === 'error'">
                                            error_outline
                                        </v-icon>
                                        <v-icon color="primary" medium v-if="notification.type === 'info'">
                                            info_outline
                                        </v-icon>
                                    </v-flex>
                                    <v-flex xs10>
                                        <v-layout row wrap>
                                            <v-flex xs12>
                                                <span>{{ notification.text }}</span>
                                            </v-flex>
                                            <v-flex xs12 class="text--secondary">
                                                <span>{{ notification.date | moment('from', 'now') }}</span>
                                            </v-flex>
                                        </v-layout>
                                    </v-flex>
                                </v-card-title>
                            </v-card>

                            <v-divider></v-divider>
                        </div>
                    </template>
                </div>

            </v-card>
        </v-menu>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class UserMenu extends Vue {
        fav: boolean = true;
        menu: boolean = false;
        message: boolean = false;
        hints: boolean = true;

        notifications = [
            {
                id: 1,
                type: 'success',
                date: new Date(2019, 3, 3, 12, 30, 0),
                text: 'Анализ "Первичный аналз состояния сосудов" успешно завершен'
            },
            {
                id: 2,
                type: 'info',
                date: new Date(2019, 3, 3, 12, 30, 0),
                text: 'Анализ "Первичный аналз состояния сосудов" принят в обработку'
            },
            {
                id: 3,
                type: 'error',
                date: new Date(2019, 3, 1, 12, 40, 0),
                text: 'Во время выполнения анализа "Первичный аналз состояния сосудов" произошла ошибка'
            },
            {
                id: 4,
                type: 'info',
                date: new Date(2019, 3, 1, 12, 30, 0),
                text: 'Анализ "Первичный аналз состояния сосудов" принят в обработку'
            },
        ]
    }
</script>

<style scoped>
    .scrollable {
        overflow: auto;
        height: 250px;
    }
</style>
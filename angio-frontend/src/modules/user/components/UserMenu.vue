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
                    <v-icon>person</v-icon>
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
                            <v-list-tile-sub-title>Клиника глазных болезней СГМУ им. Разумовского
                            </v-list-tile-sub-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>

                <v-divider></v-divider>

                <v-list>
                    <v-subheader>Настройки</v-subheader>
                    <v-list-tile>
                        <v-list-tile-action>
                            <v-switch v-model="message"></v-switch>
                        </v-list-tile-action>
                        <v-list-tile-title>Тёмная тема</v-list-tile-title>
                    </v-list-tile>
                </v-list>

                <v-layout class="px-3">
                    <v-select
                            single-line
                            :items="['Русский', 'English']"
                            label="Язык интерфейса"
                            solo
                    ></v-select>
                </v-layout>

                <v-card-actions>
                    <v-btn color="primary" flat block>Выйти из приложения</v-btn>
                </v-card-actions>
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
    }
</script>

<style scoped>
    .language-action {
        width: 56px;
    }

</style>
<template lang="html" xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="text-xs-center">
        <v-menu
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
                    <UserMenuNotificationBadge
                            v-bind:value="hasUnreadNotifications()">
                        <v-icon>person</v-icon>
                    </UserMenuNotificationBadge>
                </v-btn>
            </template>
            <UserMenu
                    v-bind:user="user"
                    v-bind:logout="logout"
                    v-bind:notifications="notifications"
            ></UserMenu>
        </v-menu>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    import FullName from "@/modules/common/models/fullName";
    import UserMenu from "@/modules/user/components/UserMenu.vue";
    import UserMenuNotificationBadge from "@/modules/user/components/UserMenuNotificationBadge.vue";
    import {UserInfo} from "@/modules/user/store/userState";

    @Component({
        components: {
            UserMenu,
            UserMenuNotificationBadge,
        }
    })
    export default class UserMenuButton extends Vue {

        user = {
            fullName: {
                firstname: 'Никон',
                lastname: 'Петрунин',
                patronymic: 'Дмитриевич'
            } as FullName,
            organization: "Клиника глазных болезней СГМУ им. Разумовского",
            permissions: []
        } as UserInfo;

        notifications = [
            {
                id: 1,
                read: false,
                type: 'success',
                date: new Date(2019, 3, 3, 12, 55, 0),
                text: 'Анализ "Первичный аналз состояния сосудов" успешно завершен'
            },
            {
                id: 2,
                read: true,
                type: 'info',
                date: new Date(2019, 3, 3, 12, 30, 0),
                text: 'Анализ "Первичный аналз состояния сосудов" принят в обработку'
            },
            {
                id: 3,
                read: true,
                type: 'error',
                date: new Date(2019, 3, 1, 12, 40, 0),
                text: 'Во время выполнения анализа "Первичный аналз состояния сосудов" произошла ошибка'
            },
            {
                id: 4,
                read: true,
                type: 'info',
                date: new Date(2019, 3, 1, 12, 30, 0),
                text: 'Анализ "Первичный аналз состояния сосудов" принят в обработку'
            },
        ]

        hasUnreadNotifications(): boolean {
            return this.notifications.some((notification) => !notification.read);
        }

        logout() {
        }
    }
</script>

<style scoped>

</style>
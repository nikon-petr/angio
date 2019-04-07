<template>
    <v-card flat>
        <v-card-text>
            <div class="text-xs-center text--secondary">
                Уведомлений еще нет
            </div>
        </v-card-text>
    </v-card>
</template>

<script>
    import {Component, Vue} from 'vue-property-decorator/lib/vue-property-decorator';

    @Component
    export default class NoNotificationsMessage extends Vue {
    }
</script>
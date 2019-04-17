<template>
    <div class="notifications-offset">
        <v-notifications
                v-bind:duration="15000"
                v-bind:max="5"
                position="bottom right"
                animation-name="scale-transition"
        >
            <template slot="body" slot-scope="props">
                <BaseNotification
                        v-bind:type="props.item.data.type"
                        v-bind:body="props.item.data.body"
                >
                </BaseNotification>
            </template>
        </v-notifications>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import BaseNotification from "@/modules/notification/components/BaseNotification.vue";

    @Component({
        components: {BaseNotification}
    })
    export default class TheNotification extends Vue {
    }
</script>

<style scoped>
    .notifications-offset >>> .notification-wrapper:last-child {
        margin-bottom: 4px;
    }
</style>

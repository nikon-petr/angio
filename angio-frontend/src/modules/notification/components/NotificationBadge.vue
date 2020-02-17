<template>
    <v-badge
            v-bind:value="value"
            overlap
            color="info">
        <template v-slot:badge>
            <v-icon small>notifications</v-icon>
        </template>
        <slot></slot>
    </v-badge>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class NotificationBadge extends Vue {

        @Prop()
        public readonly value!: boolean;
    }
</script>

<style scoped>

</style>
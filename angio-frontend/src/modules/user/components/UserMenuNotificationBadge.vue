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
    export default class UserMenuNotificationBadge extends Vue {

        @Prop()
        value!: boolean;
    }
</script>

<style scoped>

</style>
<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout justify-center align-center>

                <router-view></router-view>

            </v-layout>
        </v-container>
    </v-content>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class TheContent extends Vue {}
</script>

<style scoped>

</style>
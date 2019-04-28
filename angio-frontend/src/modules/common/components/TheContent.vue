<template>
    <v-content>
        <transition name="slide-x-transition" mode="out-in">
            <router-view></router-view>
        </transition>
    </v-content>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class TheContent extends Vue {

    }
</script>

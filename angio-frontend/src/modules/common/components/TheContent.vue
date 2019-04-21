<template>
    <v-content>
        <v-container fluid fill-height pl-0 py-0>
            <TheDrawer></TheDrawer>
            <v-layout justify-center align-center>
                <transition name="slide-x-transition" mode="out-in">

                    <router-view></router-view>

                </transition>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import TheDrawer from '@/modules/common/components/TheDrawer.vue';

    @Component({
        components: {TheDrawer}
    })
    export default class TheContent extends Vue {

    }
</script>
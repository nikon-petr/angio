<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout class="pa-4" wrap>
                <transition name="slide-x-transition" mode="out-in">

                    <router-view></router-view>

                </transition>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class TheContent extends Vue {

    }
</script>

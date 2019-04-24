<template>
    <v-footer
            v-bind:color="$vuetify.dark ? 'secondary' : 'white'"
            app
            absolute
            inset
            height="auto"
    >
        <v-layout
                justify-center
                row
                wrap
        >
            <v-btn
                    to="/"
                    flat
                    round
                    active-class
            >
                {{ $t('common.component.theFooter.button.home') }}
            </v-btn>
            <v-btn
                    to="/about"
                    flat
                    round
                    active-class
            >
                {{ $t('common.component.theFooter.button.about') }}
            </v-btn>
            <v-btn
                    to="/help"
                    flat
                    round
                    active-class
            >
                {{ $t('common.component.theFooter.button.help') }}
            </v-btn>
            <v-flex
                    py-3
                    text-xs-center
                    xs12
            >
                &copy; 2018-{{ new Date() | moment('YYYY') }} {{ $t('common.appName') }}
            </v-flex>
        </v-layout>
    </v-footer>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class TheFooter extends Vue {
    }
</script>

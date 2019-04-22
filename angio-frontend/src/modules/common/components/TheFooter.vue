<template>
    <v-footer
            app
            absolute
            inset
            height="auto"
            color="secondary"
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
                    color="white"
            >
                {{ $t('common.component.theFooter.button.home') }}
            </v-btn>
            <v-btn
                    to="/about"
                    flat
                    round
                    color="white"
            >
                {{ $t('common.component.theFooter.button.about') }}
            </v-btn>
            <v-btn
                    to="/help"
                    flat
                    round
                    color="white"
            >
                {{ $t('common.component.theFooter.button.help') }}
            </v-btn>
            <v-flex
                    py-3
                    text-xs-center
                    xs12
                    white--text
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

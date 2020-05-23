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

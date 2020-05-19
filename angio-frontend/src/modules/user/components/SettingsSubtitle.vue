<template>
    <v-layout row align-center>
        <v-flex shrink>
            <span class="subheading text--secondary text--darken-4 mr-2"><slot></slot></span>
        </v-flex>
        <v-flex grow>
            <hr size="1"/>
        </v-flex>
        <v-flex shrink>
            <v-icon small>{{icon}}</v-icon>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';

    @Component({})
    export default class SettingsSubtitle extends Vue {

        @Prop()
        public readonly icon!: string;
    }
</script>
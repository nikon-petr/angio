<template>
    <v-layout row>
        <v-flex xs10 align-self-center>
            <h3 class="display-1 font-weight-regular text-capitalize text-uppercase">{{ value }}</h3>
        </v-flex>

        <v-flex xs2 class="text-xs-right" pr-1>
            <v-btn
                    v-if="isButtonEnabled"
                    v-on:click="onButtonClick"
                    v-bind:color="color"
                    fab
                    small
            >
                <v-icon dark>{{ icon }}</v-icon>
            </v-btn>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BaseSubheader extends Vue {

        @Prop()
        public readonly value!: string;

        @Prop({default: false})
        public readonly isButtonEnabled!: boolean;

        @Prop({default: 'accent'})
        public readonly color!: string;

        @Prop({default: 'add'})
        public readonly icon!: string;

        @Emit('append-button-click')
        public onButtonClick() {

        }
    }
</script>

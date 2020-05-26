<template>
    <v-card>
        <div class="col-auto">
            <div v-bind:class="titleClass">&nbsp;</div>
        </div>

        <div
                v-if="!fetching"
                v-bind:class="contentClass"
        >
            <h5 class="text-truncate text-uppercase">
                {{ title }}
            </h5>
            <h1 class="display-1">{{ content }}</h1>
        </div>

        <div
                v-if="fetching"
                v-bind:class="contentClass"
                class="text-xs-center"
        >
            <v-progress-circular
                    v-bind:background-opacity="0"
                    indeterminate
                    pa-0
                    ma-0
            ></v-progress-circular>
        </div>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component({})
    export default class BlockInfo extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly title!: string;

        @Prop()
        public readonly content!: string;

        @Prop({default: 'red'})
        public readonly color!: string;

        @Prop({default: ''})
        public readonly colorVariant!: string;

        public titleClass!: string;

        public contentClass!: string;

        public data() {
            return {
                titleClass: this.colorVariant != ''
                    ? "fill-height " + this.color + " " + this.colorVariant
                    : "fill-height " + this.color,
                contentClass: this.colorVariant != ''
                    ? "col pa-3 py-4 " + this.color + "--text text--" + this.colorVariant
                    : "col pa-3 py-4 " + this.color + "--text",
            }
        }
    }
</script>

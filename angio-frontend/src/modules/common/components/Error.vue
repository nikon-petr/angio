<template>
    <v-layout justify-center align-center>
        <div>
            <div class="mr-3 hidden-sm-and-down" v-if="imageSrc">
                <img :src="imageSrc" :alt="code">
            </div>
            <div class="text-md-center">
                <h1 class="error-code font-weight-black shades">{{ code }}</h1>
                <h2 class="my-3 headline ">{{ description }}</h2>
                <div>
                    <v-btn
                            v-bind:to="link || '/'"
                            color="primary"
                            round
                    >
                        {{ linkName || $t('common.component.error.linkName') }}
                    </v-btn>
                </div>
            </div>
        </div>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";

    @Component
    export default class TheContent extends Vue {

        @Prop()
        public readonly code!: number;

        @Prop()
        public readonly description!: string;

        @Prop()
        public readonly link?: string;

        @Prop()
        public readonly linkName?: string;

        @Prop()
        public readonly imageSrc?: string;
    }
</script>

<style scoped>
    .error-code {
        font-size: 150px;
        line-height: 150px;
        font-weight: 700;
    }
</style>

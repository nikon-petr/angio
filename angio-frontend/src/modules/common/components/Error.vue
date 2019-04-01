<template>
    <div>
        <div class="mr-3 hidden-sm-and-down" v-if="imageSrc">
            <img :src="imageSrc" :alt="code">
        </div>
        <div class="text-md-center">
            <h1 class="error-code font-weight-black shades">{{ code }}</h1>
            <h2 class="my-3 headline ">{{ description }}</h2>
            <div>
                <v-btn color="primary" :to="link || '/'">{{ linkName || $t('common.component.error.linkName') }}</v-btn>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class TheContent extends Vue {

        @Prop()
        readonly code!: number;

        @Prop()
        readonly description!: string;

        @Prop()
        readonly link?: string;

        @Prop()
        readonly linkName?: string;

        @Prop()
        readonly imageSrc?: string;
    }
</script>

<style scoped>
    .error-code {
        font-size: 150px;
        line-height: 150px;
        font-weight: 700;
    }
</style>
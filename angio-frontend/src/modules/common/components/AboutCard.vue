<template>
    <CentredLayout>
        <v-card>
            <v-card-text>
                <span class="text--secondary subheading">{{ $t('common.view.about.app') }}</span><br>
                Front-end: {{ frontendVersion }}<br>
                Back-end: {{ backendVersion }}<br>
                <br>
                <span class="text--secondary subheading">{{ $t('common.view.about.repository') }}</span><br>
                <a class="link--accent" href="https://github.com/nikon-petr/angio" target="_blank">GitHub</a><br>
                <br>
                <span class="text--secondary subheading">{{ $t('common.view.about.developers') }}</span><br>
                <a
                        class="link--accent"
                        href="https://github.com/nikon-petr"
                        target="_blank"
                >
                    {{ $t('common.view.about.nikon') }}
                </a>,&nbsp;
                <a
                        class="link--accent"
                        href="https://github.com/Karkavin"
                        target="_blank"
                >
                    {{ $t('common.view.about.artem') }}
                </a><br>
            </v-card-text>
        </v-card>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';

    @Component({
        components: {CentredLayout}
    })
    export default class About extends Vue {

        @Prop()
        public frontendVersion!: string;

        @Prop()
        public backendVersion!: string;
    }
</script>

<style scoped>
    .link--accent {
        color: var(--v-accent-base);
    }
</style>

<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('common.view.landing.subheader')"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <v-card>
                <v-container fluid>
                    <v-layout
                            row
                            wrap
                            grid-list-md
                            align-content-space-around
                    >
                        <v-flex xs12>
                            <v-card-title primary-title>
                                <h4 class="display-1 font-weight-regular mb-0">{{ $t('common.view.landing.content.main.title') }}</h4>
                            </v-card-title>
                            <v-card-text class="headline font-weight-light pt-0">{{ $t('common.view.landing.content.main.content') }}</v-card-text>
                        </v-flex>

                        <v-flex xs12>
                            <v-card-title primary-title>
                                <h4 class="display-1 font-weight-regular mb-0">{{ $t('common.view.landing.content.analyses.title') }}</h4>
                            </v-card-title>
                            <v-card-text
                                    class="headline font-weight-light pt-0 pr-0 pb-0"
                                    v-for="analyse in $t('common.view.landing.content.analyses.content')"
                            >
                                &#9679; {{ analyse }}
                            </v-card-text>
                        </v-flex>

                        <v-flex xs12>
                            <v-card-title primary-title>
                                <h4 class="display-1 font-weight-regular mb-0">{{ $t('common.view.landing.content.abilities.title') }}</h4>
                            </v-card-title>
                            <v-card-text
                                    class="headline font-weight-light pt-0 pr-0 pb-0"
                                    v-for="ability in $t('common.view.landing.content.abilities.content')"
                            >
                                &#9679; {{ ability }}
                            </v-card-text>
                        </v-flex>

                        <v-flex xs12>
                            <v-card-title primary-title>
                                <h4 class="display-1 font-weight-regular mb-0">{{ $t('common.view.landing.content.parameters.title') }}</h4>
                            </v-card-title>
                            <v-card-text
                                    class="headline font-weight-light pt-0 pr-0 pb-0"
                                    v-for="(parameter, index) in $t('common.view.landing.content.parameters.content')"
                            >
                                {{ incrementIndex(index) }}. {{ parameter }}
                            </v-card-text>
                        </v-flex>

                        <v-flex xs12>
                            <v-card-title primary-title>
                                <h4 class="display-1 font-weight-regular mb-0">{{ $t('common.view.landing.content.work.title') }}</h4>
                            </v-card-title>
                            <v-card-text
                                    class="headline font-weight-light pt-0 pr-0 pb-0"
                                    v-for="(step, index) in filterWorks($t('common.view.landing.content.work.content'), true)"
                            >
                                {{ incrementIndex(index) }}. {{ step }}
                            </v-card-text>
                            <v-img
                                    min-height="100"
                                    min-width="100"
                                    max-height="500"
                                    max-width="500"
                                    aspect-ratio="1"
                                    v-bind:src="require('@/assets/angio-landing.png')"
                            ></v-img>
                            <v-card-text
                                    class="headline font-weight-light pt-0 pr-0 pb-0"
                                    v-for="(step, index) in filterWorks($t('common.view.landing.content.work.content'), false)"
                            >
                                {{ incrementIndex(index+1) }}. {{ step }}
                            </v-card-text>
                        </v-flex>

                        <v-flex xs12>
                            <v-card-title primary-title>
                                <h4 class="display-1 font-weight-regular mb-0">{{ $t('common.view.landing.content.specifications.title') }}</h4>
                            </v-card-title>
                            <v-card-text
                                    class="headline font-weight-light pt-0 pr-0 pb-0"
                                    v-for="specification in $t('common.view.landing.content.specifications.content')">
                                &#9679; {{ specification }}
                            </v-card-text>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-card>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import StackLayout from '@/modules/common/components/StackLayout.vue';

    @Component({
        components: {StackLayout, BaseSubheader},
    })
    export default class Landing extends Vue {
        public incrementIndex(i: string): string {
            return i + 1;
        }

        public filterWorks(works: string[], isFirst: boolean) {
            if (works.length > 0) {
                if (isFirst) {
                    return works.filter((value, index) => index === 0)
                } else {
                    return works.filter((value, index) => index > 0)
                }
            } else {
                return []
            }
        }
    }
</script>

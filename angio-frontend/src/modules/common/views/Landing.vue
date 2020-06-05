<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('common.view.landing.subheader')"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs6 d-flex>
            <v-card>
            <v-container>
                <v-card-title primary-title>
                    <h4 class="display-1">{{ $t('common.view.landing.content.main.title') }}</h4>
                </v-card-title>
                <v-card-text class="headline font-weight-light">
                    {{ $t('common.view.landing.content.main.content') }}
                </v-card-text>
            </v-container>
            </v-card>
        </v-flex>

        <v-flex xs6 d-flex>
            <v-card>
                <v-container>
                    <v-card-text
                    >
                        <h4 class="display-1 py-4">{{ $t('common.view.landing.content.analyses.title') }}</h4>
                        <ul>
                            <li
                                    class="headline font-weight-light"
                                    v-for="analyse in $t('common.view.landing.content.analyses.content')"
                            >
                                {{ analyse }}
                            </li>
                        </ul>
                    </v-card-text>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex xs8>
            <v-card>
                <v-container>
                    <v-card-title primary-title>
                        <h4 class="display-1">{{ $t('common.view.landing.content.work.title') }}</h4>
                    </v-card-title>
                    <v-card-text>
                        <v-layout row wrap>
                            <v-flex>
                                <v-img
                                        min-height="500"
                                        min-width="500"
                                        max-height="500"
                                        max-width="500"
                                        aspect-ratio="1"
                                        v-bind:src="require('@/assets/angio-landing.png')"
                                ></v-img>
                            </v-flex>
                            <v-flex grow align-center>
                                <ol>
                                    <li
                                            class="headline font-weight-light"
                                            v-for="step in $t('common.view.landing.content.work.content')"
                                    >
                                        {{ step }}
                                    </li>
                                </ol>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex xs4>
            <v-container fill-height>
                <v-layout row wrap align-center>
                    <v-flex class="text-xs-center">
                        <v-btn
                                to="/register"
                                color="success"
                                round
                                large
                        >
                            {{ $t('common.view.landing.content.getStarted.button') }}<v-icon right>person_add</v-icon>
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-flex>

        <v-flex xs4 d-flex>
            <v-card>
                <v-container>
                    <v-card-text>
                        <h4 class="display-1 py-4">{{ $t('common.view.landing.content.parameters.title') }}</h4>
                        <ul>
                            <li
                                    class="headline font-weight-light"
                                    v-for="parameter in $t('common.view.landing.content.parameters.content')"
                            >
                                {{ parameter }}
                            </li>
                        </ul>
                    </v-card-text>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex xs4 d-flex>
            <v-card>
                <v-container>
                    <v-card-text>
                        <h4 class="display-1 py-4">{{ $t('common.view.landing.content.abilities.title') }}</h4>
                        <ul>
                            <li
                                    class="headline font-weight-light"
                                    v-for="ability in $t('common.view.landing.content.abilities.content')"
                            >
                                {{ ability }}
                            </li>
                        </ul>
                    </v-card-text>
                </v-container>
            </v-card>
        </v-flex>

        <v-flex xs4 d-flex>
            <v-card>
                <v-container>
                    <v-card-text>
                        <h4 class="display-1 py-4">{{ $t('common.view.landing.content.specifications.title') }}</h4>
                        <ul>
                            <li
                                    class="headline font-weight-light"
                                    v-for="specification in $t('common.view.landing.content.specifications.content')"
                            >
                                {{ specification }}
                            </li>
                        </ul>
                    </v-card-text>
                </v-container>
            </v-card>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import {Component, Vue} from 'vue-property-decorator';

    @Component({
        components: {StackLayout, BaseSubheader},
    })
    export default class Landing extends Vue {
    }
</script>

<style scoped>
    .right-list {
        list-style-position: inside;
        direction: rtl;
    }
</style>

<template>
    <v-form
            ref="form"
    >
        <v-container>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-select
                            v-model="analyseAdditionalInfo.type"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.analyseType.field')"
                            v-bind:items="analyseTypes"
                            required
                            persistent-hint
                    ></v-select>
                </v-flex>

                <v-flex xs8>
                    <v-text-field
                            v-model="analyseAdditionalInfo.name"
                            v-bind:label="$t('analyse.component.details.editForm.name.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.name.validation.NotEmpty'),
                                            v => v.length >= 5 || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.name.validation.MinLength')]"
                            type="text"
                            name="name"
                            counter
                            maxlength="75"
                            browser-autocomplete="off"
                            required
                    ></v-text-field>
                </v-flex>

                <v-flex xs12>
                    <v-text-field
                            v-model="analyseAdditionalInfo.shortDescription"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.shortDescription.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.shortDescription.validation.NotEmpty'),
                                            v => v.length >= 5 || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.shortDescription.validation.MinLength')]"
                            type="text"
                            name="shortDescription"
                            counter
                            maxlength="100"
                            browser-autocomplete="off"
                            required
                    ></v-text-field>
                </v-flex>

                <v-flex xs12>
                    <v-text-field
                            v-model="analyseAdditionalInfo.fullDescription"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.fullDescription.field')"
                            type="text"
                            name="fullDescription"
                            counter
                            outline
                            multi-line
                            maxlength="1000"
                            clearable
                            browser-autocomplete="off"
                    ></v-text-field>
                </v-flex>

                <v-flex xs12>
                    <v-text-field
                            v-model="analyseAdditionalInfo.conclusion"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.conclusion.field')"
                            type="text"
                            name="conclusion"
                            counter
                            outline
                            multi-line
                            maxlength="1000"
                            clearable
                            browser-autocomplete="off"
                    ></v-text-field>
                </v-flex>

                <v-flex xs12>
                    <v-text-field
                            v-model="analyseAdditionalInfo.comment"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.comment.field')"
                            type="text"
                            name="comment"
                            counter
                            maxlength="125"
                            clearable
                            browser-autocomplete="off"
                    ></v-text-field>
                </v-flex>
            </v-layout>
        </v-container>
    </v-form>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseAdditionalInfo, AnalyseType} from '@/modules/analyse/models/analyse';

    @Component({})
    export default class CreateAnalyseFormStepDisease extends Vue {

        @Prop()
        public analyseAdditionalInfo!: AnalyseAdditionalInfo;

        public analyseTypes: string[] = [
            this.getAnalyseTypeName(AnalyseType.PRIMARY.toString()),
            this.getAnalyseTypeName(AnalyseType.SUBSEQUENT.toString())
        ];

        public getAnalyseTypeName(type: string): string {
            return this.$t('analyse.model.analyseType.' + type).toString()
        }
    }
</script>

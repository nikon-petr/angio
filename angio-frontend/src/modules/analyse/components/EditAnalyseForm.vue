<template lang="html">
    <v-dialog
            width="900"
            v-model="isShowed"
            v-on:keydown.esc.stop="cancel"
            v-on:keydown.enter.stop="agree"
            persistent
    >
        <v-card class="pa-3 elevation-12">
            <v-card-title class="headline font-weight-regular justify-space-between">
                <span>{{ $t('analyse.component.details.editForm.title') }}</span>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-form
                        v-model="valid"
                        v-on:submit.prevent="submitForm"
                        ref="form"
                        id="edit-analyse-form"
                        data-test-id="editAnalyse__form"
                >
                    <v-container>
                        <v-layout>
                            <v-flex xs12>
                                <v-select
                                        v-model="editedData.type"
                                        v-bind:label="$t('analyse.component.details.editForm.analyseType.field')"
                                        v-bind:items="analyseTypes"
                                        required
                                        persistent-hint
                                        v-bind:disabled="fetching"
                                ></v-select>
                                <v-text-field
                                        v-model="editedData.name"
                                        v-bind:label="$t('analyse.component.details.editForm.name.field')"
                                        v-bind:rules="[v => !!v || $t('analyse.component.details.editForm.name.validation.NotEmpty'),
                                            v => v.length >= 5 || $t('analyse.component.details.editForm.name.validation.MinLength')]"
                                        v-bind:disabled="fetching"
                                        data-test-id="name__input"
                                        type="text"
                                        name="name"
                                        counter
                                        maxlength="75"
                                        browser-autocomplete="off"
                                        required
                                ></v-text-field>
                                <v-text-field
                                        v-model="editedData.shortDescription"
                                        v-bind:label="$t('analyse.component.details.editForm.shortDescription.field')"
                                        v-bind:rules="[v => !!v || $t('analyse.component.details.editForm.shortDescription.validation.NotEmpty'),
                                            v => v.length >= 5 || $t('analyse.component.details.editForm.shortDescription.validation.MinLength')]"
                                        v-bind:disabled="fetching"
                                        data-test-id="shortDescription__input"
                                        type="text"
                                        name="shortDescription"
                                        counter
                                        maxlength="100"
                                        browser-autocomplete="off"
                                        required
                                ></v-text-field>
                                <v-text-field
                                        v-model="editedData.fullDescription"
                                        v-bind:label="$t('analyse.component.details.editForm.fullDescription.field')"
                                        v-bind:disabled="fetching"
                                        data-test-id="fullDescription__input"
                                        type="text"
                                        name="fullDescription"
                                        counter
                                        outline
                                        multi-line
                                        maxlength="1000"
                                        clearable
                                        browser-autocomplete="off"
                                ></v-text-field>
                                <v-text-field
                                        v-model="editedData.conclusion"
                                        v-bind:label="$t('analyse.component.details.editForm.conclusion.field')"
                                        v-bind:disabled="fetching"
                                        data-test-id="conclusion__input"
                                        type="text"
                                        name="conclusion"
                                        counter
                                        outline
                                        multi-line
                                        maxlength="1000"
                                        clearable
                                        browser-autocomplete="off"
                                ></v-text-field>
                                <v-text-field
                                        v-model="editedData.comment"
                                        v-bind:label="$t('analyse.component.details.editForm.comment.field')"
                                        v-bind:disabled="fetching"
                                        data-test-id="comment__input"
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
                    <BuiltInErrorMessage
                            v-bind:error-messages="mapErrorMessages"
                    ></BuiltInErrorMessage>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        v-on:click="cancel"
                        v-bind:loading="fetching"
                        data-test-id="cancel__button"
                        form="edit-analyse-form"
                        color="white"
                        flat
                        round
                >
                    Отменить
                </v-btn>
                <v-btn
                        v-bind:disabled="!valid"
                        v-bind:loading="fetching"
                        v-on:click="apply"
                        data-test-id="accept__button"
                        form="edit-analyse-form"
                        color="primary"
                        round
                        dark
                >
                    Сохранить
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {AnalyseAdditionalInfo, AnalyseType} from "@/modules/analyse/models/analyse";
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({
        components: {BuiltInErrorMessage, BaseSingleFormContainer}
    })
    export default class EditAnalyseForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly analyseId!: number;

        @Prop()
        public analyseAdditionalInfo!: AnalyseAdditionalInfo;

        @Prop()
        public editedData!: AnalyseAdditionalInfo;

        @Prop()
        public isShowed!: boolean;

        @Prop()
        public readonly submit!: () => Promise<any>;

        @Prop()
        public readonly errorMessages!: string[];

        public valid: boolean = false;

        public analyseTypes: string[] = [
            this.getAnalyseTypeName(AnalyseType.PRIMARY.toString()),
            this.getAnalyseTypeName(AnalyseType.SUBSEQUENT.toString())
        ];

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        public getAnalyseTypeName(type: string): string {
            return this.$t('analyse.model.analyseType.' + type).toString()
        }

        @Emit(CommonEvent.ANALYSE_EDIT_APPLY)
        public apply() {
            if (this.editedData.type == this.getAnalyseTypeName(AnalyseType.PRIMARY.toString())) {
                this.editedData.type = AnalyseType.PRIMARY.toString();
            } else {
                this.editedData.type = AnalyseType.SUBSEQUENT.toString();
            }
            return this.editedData
        }

        @Emit(CommonEvent.ANALYSE_EDIT_CANCEL)
        public cancel() {

        }
    }
</script>

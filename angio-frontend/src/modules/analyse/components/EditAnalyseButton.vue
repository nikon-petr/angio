<template lang="html">
    <div>
        <v-btn
                v-bind:loading="fetching"
                v-on:click="onOpen"
                class="ma-0"
                color="blue"
                ripple
                icon
                flat
        >
            <v-icon>edit</v-icon>
        </v-btn>

        <EditAnalyseForm
                v-on:analyse-edit-apply="onEditApply"
                v-on:analyse-edit-cancel="onEditCancel"
                v-bind:is-showed="isShowed"
                v-bind:fetching="fetching"
                v-bind:analyse-id="analyseId"
                v-bind:analyse-additional-info="analyseAdditionalInfo"
                v-bind:edited-data="editedData"
                v-bind:error-messages="errorMessages"
        ></EditAnalyseForm>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseAdditionalInfo} from '@/modules/analyse/models/analyse';
    import EditAnalyseForm from '@/modules/analyse/components/EditAnalyseForm.vue';
    import {throttle} from 'helpful-decorators';

    @Component({
        components: {EditAnalyseForm},
    })
    export default class EditAnalyseButton extends Vue {

        @Prop()
        public readonly analyseId!: number;

        @Prop()
        public analyseAdditionalInfo!: AnalyseAdditionalInfo;

        @Prop()
        public readonly editAnalyseAdditionalInfo!: (id: number, analyseAdditionalInfo: AnalyseAdditionalInfo) => Promise<void>;

        public errorMessages: string[] = [];

        public fetching: boolean = false;

        public isShowed: boolean = false;

        public editedData: AnalyseAdditionalInfo = this.fillEditedDataByDefaultValues();

        public onOpen() {
            this.editedData = this.fillEditedDataByDefaultValues();
            this.isShowed = true;
        }

        @throttle(1000)
        public onEditApply(analyseAdditionalInfo: AnalyseAdditionalInfo) {
            this.fetching = true;
            this.errorMessages = [];
            this.editAnalyseAdditionalInfo(this.analyseId, analyseAdditionalInfo)
                .then(() => {
                    this.isShowed = false;
                })
                .catch((error) => {
                    this.editedData.type = this.getAnalyseTypeName(this.editedData.type);
                    this.errorMessages = ['analyse.component.details.editForm.error'];
                    this.$logger.error(error)
                })
                .finally(() => {
                    this.fetching = false;
                });
        }

        @throttle(1000)
        public onEditCancel() {
            this.isShowed = false;
        }

        public fillEditedDataByDefaultValues(): AnalyseAdditionalInfo {
            return {
                name: this.analyseAdditionalInfo.name,
                patientId: this.analyseAdditionalInfo.patientId,
                diagnostician: this.analyseAdditionalInfo.diagnostician,
                shortDescription: this.analyseAdditionalInfo.shortDescription,
                fullDescription: this.analyseAdditionalInfo.fullDescription,
                type: this.$t('analyse.model.analyseType.' + this.analyseAdditionalInfo.type).toString(),
                comment: this.analyseAdditionalInfo.comment,
                conclusion: this.analyseAdditionalInfo.conclusion,
            };
        }

        public getAnalyseTypeName(type: string): string {
            return this.$t('analyse.model.analyseType.' + type).toString()
        }
    }
</script>

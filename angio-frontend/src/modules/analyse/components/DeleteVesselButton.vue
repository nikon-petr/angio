<template lang="html">
    <v-btn
            v-bind:loading="fetching"
            v-on:click="onDeleteVessel"
            class="ma-0"
            color="error"
            ripple
            icon
            flat
    >
        <v-icon>delete</v-icon>
    </v-btn>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';

    @Component
    export default class DeleteVesselButton extends Vue {

        @Prop()
        public readonly analyseId!: number;

        @Prop()
        public readonly vesselId!: number;

        @Prop()
        public readonly deleteVessel!: (analyseId: number, vesselId: number) => Promise<void>;

        public fetching: boolean = false;

        public async onDeleteVessel() {
            const title = this.$t('analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.button.deleteVessel.title')
                .toString();
            const text = this.$t('analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.button.deleteVessel.text')
                .toString();
            if (await this.$confirm(title, ConfirmType.DELETE, text)) {
                this.fetching = true;
                this.deleteVessel(this.analyseId, this.vesselId)
                    .finally(() => this.fetching = false);
            }
        }
    }
</script>

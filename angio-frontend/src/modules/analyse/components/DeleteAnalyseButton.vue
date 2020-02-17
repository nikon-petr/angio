<template lang="html">
    <v-btn
            v-bind:loading="fetching"
            v-on:click="onDeleteAnalyse"
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
    export default class DeleteAnalyseButton extends Vue {

        @Prop()
        public readonly analyseId!: number;

        @Prop()
        public readonly deleteAnalyse!: (id: number) => Promise<void>;

        public fetching: boolean = false;

        public async onDeleteAnalyse() {
            const title = this.$t('analyse.component.analyseListTablePreview.button.delete.confirm.title').toString();
            const text = this.$t('analyse.component.analyseListTablePreview.button.delete.confirm.text', [this.analyseId]).toString();
            if(await this.$confirm(title, ConfirmType.DELETE, text)) {
                this.fetching = true;
                this.deleteAnalyse(this.analyseId)
                    .finally(() => this.fetching = false);
            }
        }
    }
</script>

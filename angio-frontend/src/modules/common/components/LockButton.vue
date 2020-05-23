<template>
    <v-btn
            v-bind:loading="fetching"
            v-on:click="onLock"
            class="ma-0"
            color="error"
            ripple
            icon
            flat
    >
        <v-icon v-if="!locked">lock</v-icon>
        <v-icon v-else="locked">lock_open</v-icon>
    </v-btn>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';

    @Component({})
    export default class LockButton extends Vue {

        @Prop()
        public readonly lock!: (id: string, locked: boolean) => Promise<void>;

        @Prop()
        public readonly locked!: boolean;

        @Prop()
        public readonly id!: string;

        @Prop()
        public readonly object!: string;

        public fetching: boolean = false;

        public async onLock() {
            const object = this.$t(this.object).toString();

            const title = !this.locked
                ? this.$t('common.component.lockButton.lock.confirm.lockTitle', [object]).toString()
                : this.$t('common.component.lockButton.lock.confirm.unlockTitle', [object]).toString();
            const text = !this.locked
                ? this.$t('common.component.lockButton.lock.confirm.lockText', [object]).toString()
                : this.$t('common.component.lockButton.lock.confirm.unlockText', [object]).toString();
            if(await this.$confirm(title, ConfirmType.QUESTION, text)) {
                this.fetching = true;
                this.lock(this.id, !this.locked)
                    .finally(() => this.fetching = false);
            }
        }
    }
</script>

<template>
    <v-btn
            v-bind:loading="fetching"
            v-on:click="onLockUser"
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
    import {Component, Vue, Prop, Emit} from 'vue-property-decorator';
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';

    @Component({})
    export default class LockUserButton extends Vue {

        @Prop()
        public readonly lockUser!: (id: string, locked: boolean) => Promise<void>;

        @Prop()
        public readonly locked!: boolean;

        @Prop()
        public readonly userId!: string;

        public fetching: boolean = false;

        public async onLockUser() {
            const title = !this.locked
                ? this.$t('user.component.lockUserButton.lock.confirm.lockTitle').toString()
                : this.$t('user.component.lockUserButton.lock.confirm.unlockTitle').toString();
            const text = !this.locked
                ? this.$t('user.component.lockUserButton.lock.confirm.lockText', [this.userId]).toString()
                : this.$t('user.component.lockUserButton.lock.confirm.unlockText', [this.userId]).toString();
            if(await this.$confirm(title, ConfirmType.QUESTION, text)) {
                this.fetching = true;
                this.lockUser(this.userId, !this.locked)
                    .finally(() => this.fetching = false);
            }
        }
    }
</script>

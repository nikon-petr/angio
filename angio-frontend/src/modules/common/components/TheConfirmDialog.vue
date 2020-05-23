<template lang="html">
    <v-dialog
            v-model="dialog"
            v-bind:style="{ zIndex: 300 }"
            v-bind:max-width="450"
            v-on:keydown.esc.stop="cancel"
            v-on:keydown.enter.stop="agree"
            persistent
    >
        <v-card>
            <v-card-title class="title font-weight-light">
                {{ title }}
            </v-card-title>
            <v-card-text v-show="!!message" class="pt-0">
                <v-layout row>
                    <v-flex pr-2 align-self-center shrink>
                        <v-icon
                                v-bind:color="color"
                                x-large
                        >
                            {{ icon }}
                        </v-icon>
                    </v-flex>
                    <v-flex align-self-center>
                        {{ message }}
                    </v-flex>
                </v-layout>
            </v-card-text>
            <v-card-actions class="pt-0">
                <v-spacer></v-spacer>
                <v-btn
                        v-if="!isQuestion"
                        v-on:click.native="agree"
                        flat
                        round
                >
                    {{ $t('common.component.confirmDialog.button.back') }}
                </v-btn>
                <v-btn
                        v-if="isQuestion"
                        v-on:click.native="cancel"
                        flat
                        round
                >
                    {{ $t('common.component.confirmDialog.button.cancel') }}
                </v-btn>
                <v-btn
                        v-if="isQuestion"
                        v-on:click.native="agree"
                        color="primary"
                        round
                >
                    {{ $t('common.component.confirmDialog.button.yes') }}
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class TheConfirmDialog extends Vue {

        public dialog: boolean = false;

        public resolve?: ((value?: any) => any);

        public reject?: ((value?: any) => any);

        public message?: string;

        public title?: string;

        public type?: ConfirmType;

        public data(): object {
            return {
                resolve: undefined,
                reject: undefined,
                message: undefined,
                title: undefined,
                type: undefined
            };
        }

        public open(title: string, type: ConfirmType, message?: string): Promise<boolean> {
            this.dialog = true;
            this.title = title;
            this.message = message ? message : undefined;
            this.type = type;
            return new Promise((resolve, reject) => {
                this.resolve = resolve;
                this.reject = reject;
            });
        }

        public agree() {
            this.dialog = false;
            setTimeout(() => {
                if (this.resolve) {
                    this.resolve(true);
                    this.$logger.debug(`${TheConfirmDialog.name} respond with ${true}`);
                }
            }, 300);
        }

        public cancel() {
            this.dialog = false;
            setTimeout(() => {
                if (this.resolve) {
                    this.resolve(false);
                    this.$logger.debug(`${TheConfirmDialog.name} respond with ${false}`);
                }
            }, 300);
        }

        get icon(): string | undefined {
            switch (this.type) {
                case ConfirmType.QUESTION:
                    return 'help';
                case ConfirmType.DELETE:
                    return 'delete';
                case ConfirmType.WARNING:
                    return 'warning';
                case ConfirmType.ERROR:
                    return 'error';
                case undefined:
                    return undefined;
            }
        }

        get color(): string | undefined {
            switch (this.type) {
                case ConfirmType.QUESTION:
                    return 'primary';
                case ConfirmType.DELETE:
                    return 'error';
                case ConfirmType.WARNING:
                    return 'warning';
                case ConfirmType.ERROR:
                    return 'error';
                case null:
                    return undefined;
            }
        }

        get isQuestion() {
            return this.type == ConfirmType.QUESTION
                || this.type == ConfirmType.DELETE;
        }
    }
</script>

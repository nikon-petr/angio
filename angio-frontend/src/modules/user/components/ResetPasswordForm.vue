<template>
    <BaseSingleFormContainer v-bind:title="$t('user.component.resetPassword.title')">
        <template slot="form">
            <v-form
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    ref="form"
                    id="reset-password-form"
            >
                <v-card-text class="caption">{{ $t('user.component.resetPassword.content') }}</v-card-text>
                <v-text-field
                        v-model="email"
                        v-bind:label="$t('user.component.resetPassword.email.field')"
                        v-bind:rules="[v => !!v || $t('user.component.resetPassword.email.validation.NotEmpty')]"
                        v-bind:disabled="fetching"
                        type="text"
                        name="email"
                        prepend-inner-icon="person"
                        browser-autocomplete="off"
                        outline
                        required
                ></v-text-field>
                <BuiltInErrorMessage
                        v-bind:error-messages="mapErrorMessages"
                ></BuiltInErrorMessage>
            </v-form>
        </template>
        <template slot="buttons">
            <v-spacer></v-spacer>
            <v-btn
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
                    form="reset-password-form"
                    type="submit"
                    color="success"
                    flat
                    round
            >
                {{ $t('user.component.resetPassword.button.submit') }}
            </v-btn>
        </template>
    </BaseSingleFormContainer>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';

    @Component({
        components: {
            BaseSingleFormContainer,
            BuiltInErrorMessage,
        },
    })
    export default class ResetPasswordForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly errorMessages: string[];

        public valid: boolean = false;

        public email: string = '';

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.email;
        }
    }
</script>

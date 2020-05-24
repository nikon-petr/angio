<template>
    <div>
        <SubtitleSeparator icon="format_paint">
            {{ $t('user.component.personalization.title') }}
        </SubtitleSeparator>
        <v-switch
                v-on:change="changeDarkThemeEnabled"
                v-bind:input-value="userSettings.darkThemeEnabled"
                v-bind:label="$t('user.component.personalization.darkTheme')"
                class="mt-0 pl-0"
        ></v-switch>
        <v-select
                v-on:input="changeLocale"
                v-bind:value="userSettings.locale"
                v-bind:items="items"
                v-bind:menu-props="{closeOnContentClick: true}"
                v-bind:prefix="$t('user.component.personalization.localization')"
                color="accent"
                hide-details
                solo
        ></v-select>
    </div>
</template>

<script lang="ts">
    import SubtitleSeparator from '@/modules/common/components/SubtitleSeparator.vue';
    import {UserSettingsModel} from '@/modules/user/models/user';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {Locale, UserSettings} from '@/modules/user/store/userState';
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component({
        components: {SubtitleSeparator}
    })
    export default class Personalization extends Vue {

        @Prop()
        public readonly userSettings!: UserSettings;

        @Prop()
        public readonly fetchUser!: () => Promise<void>;

        public items = [
            {value: Locale.RU, text: 'Русский'},
            {value: Locale.EN, text: 'English'}
        ];

        public changeDarkThemeEnabled(value: boolean): void {
            let settings: UserSettingsModel = {
                darkThemeEnabled: value,
                locale: this.userSettings.locale
            };
            UserApiService.patchSettings(settings)
                .then(response => {
                    this.fetchUser();
                })
                .catch(error => this.$logger.error(error));
        }

        public changeLocale(value: Locale): void {
            let settings: UserSettingsModel = {
                darkThemeEnabled: this.userSettings.darkThemeEnabled,
                locale: value
            };
            UserApiService.patchSettings(settings)
                .then(response => {
                    this.fetchUser();
                })
                .catch(error => this.$logger.error(error));
        }
    }
</script>

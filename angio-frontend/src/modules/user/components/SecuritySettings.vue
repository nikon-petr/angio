<template>
    <div>
        <SettingsSubtitle icon="security">
            {{ $t('user.component.securitySettings.title') }}
        </SettingsSubtitle>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.securitySettings.id') }}:</span>
        <span>{{userInfo.id}}</span><br>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.securitySettings.password') }}:</span>
        <span>●●●●●● </span>
        <router-link to="/user/password">{{ $t('user.component.securitySettings.changePasswordLink') }}</router-link>
        <br/>
    </div>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';
    import SettingsSubtitle from '@/modules/user/components/SettingsSubtitle.vue';
    import {UserInfo} from '@/modules/user/store/userState';

    @Component({
        components: {SettingsSubtitle}
    })
    export default class SecuritySettings extends Vue {

        @Prop()
        public readonly userInfo!: UserInfo;
    }
</script>

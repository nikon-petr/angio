<template>
    <div>
        <SubtitleSeparator icon="security">
            {{ $t('user.component.securitySettings.title') }}
        </SubtitleSeparator>
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
    import SubtitleSeparator from '@/modules/common/components/SubtitleSeparator.vue';
    import {UserInfo} from '@/modules/user/store/userState';

    @Component({
        components: {SubtitleSeparator}
    })
    export default class SecuritySettings extends Vue {

        @Prop()
        public readonly userInfo!: UserInfo;
    }
</script>

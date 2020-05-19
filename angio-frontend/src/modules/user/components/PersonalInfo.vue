<template>
    <div>
        <SettingsSubtitle icon="person">
            {{ $t('user.component.personalInfo.title') }}
        </SettingsSubtitle>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.personalInfo.lastName') }}:</span>
        <span>{{userInfo.fullName.lastname}}</span><br>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.personalInfo.firstName') }}:</span>
        <span>{{userInfo.fullName.firstname}}</span><br>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.personalInfo.patronymic') }}:</span>
        <span>{{userInfo.fullName.patronymic}}</span><br>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.personalInfo.email') }}:</span>
        <span>{{userInfo.email}}</span><br>
        <span class="body-2 text--secondary text--darken-4 mr-2">{{ $t('user.component.personalInfo.organization') }}:</span>
        <span>{{userInfo.organizationName}}</span><br>
    </div>
</template>

<script lang="ts">
    import {Component, Vue, Prop, Emit} from 'vue-property-decorator';
    import {UserInfo} from '@/modules/user/store/userState';
    import SettingsSubtitle from '@/modules/user/components/SettingsSubtitle.vue';

    @Component({
        components: {SettingsSubtitle}
    })
    export default class PersonalInfo extends Vue {

        @Prop()
        public readonly userInfo!: UserInfo;
    }
</script>

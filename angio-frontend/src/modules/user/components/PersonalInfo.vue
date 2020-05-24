<template>
    <div>
        <SubtitleSeparator icon="person">
            {{ $t('user.component.personalInfo.title') }}
        </SubtitleSeparator>
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
    import {Component, Vue, Prop} from 'vue-property-decorator';
    import {UserInfo} from '@/modules/user/store/userState';
    import SubtitleSeparator from '@/modules/common/components/SubtitleSeparator.vue';

    @Component({
        components: {SubtitleSeparator}
    })
    export default class PersonalInfo extends Vue {

        @Prop()
        public readonly userInfo!: UserInfo;
    }
</script>

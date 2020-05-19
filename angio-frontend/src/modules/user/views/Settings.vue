<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('user.view.settings.subheader')"
            ></BaseSubheader>
        </v-flex>
        <v-flex xs12>
            <PersonalInfo v-bind:user-info="userInfo"></PersonalInfo>
        </v-flex>
        <v-flex xs12>
            <SecuritySettings v-bind:user-info="userInfo"></SecuritySettings>
        </v-flex>
        <v-flex xs12>
            <Personalization
                    v-bind:user-settings="userSettings"
                    v-bind:fetch-user="fetchUser"
            ></Personalization>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';
    import {Action, State} from 'vuex-class';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import {UserInfo, UserSettings} from '@/modules/user/store/userState';
    import PersonalInfo from '@/modules/user/components/PersonalInfo.vue';
    import SecuritySettings from '@/modules/user/components/SecuritySettings.vue';
    import Personalization from '@/modules/user/components/Personalization.vue';
    import {UserAction} from "@/modules/user/store/userStore";

    @Component({
        components: {Personalization, SecuritySettings, StackLayout, BaseSubheader, PersonalInfo}
    })
    export default class Settings extends Vue {

        @State((state) => state.user.info)
        public readonly userInfo!: UserInfo;

        @State((state) => state.user.settings)
        public readonly userSettings!: UserSettings;

        @Action(UserAction.FETCH_USER)
        public readonly fetchUser!: () => Promise<void>;

    }
</script>

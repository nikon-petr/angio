<template>
    <div>
        <v-list three-line>
            <v-list-tile avatar>
                <v-list-tile-avatar color="primary">
                    <span class="white--text headline">{{ fullName.lastname | truncate(1, '') }}</span>
                </v-list-tile-avatar>

                <v-list-tile-content>
                    <v-list-tile-title>{{ fullNameString() }}</v-list-tile-title>
                    <v-list-tile-sub-title>{{ organization | placeholder($t('user.component.userMenuAvatar.organizationPlaceholder')) }}</v-list-tile-sub-title>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>

        <v-divider></v-divider>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import FullName from '@/modules/common/models/fullName';

    @Component
    export default class UserMenuAvatar extends Vue {

        @Prop()
        public readonly fullName!: FullName;

        @Prop()
        public readonly organization?: string;

        public fullNameString() {
            const words = [this.fullName.lastname, this.fullName.firstname, this.fullName.patronymic];
            return words.join(' ');
        }
    }
</script>

<style scoped>

</style>
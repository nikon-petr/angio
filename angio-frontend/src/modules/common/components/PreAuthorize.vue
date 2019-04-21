<template>
    <div v-if="show()">
        <slot></slot>
    </div>
    <div v-else="!show()">
        <slot name="not-authorized"></slot>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {UserPermission} from "@/modules/user/store/userState";
    import {userModule} from "@/modules/user/store/userStore";

    @Component
    export default class PreAuthorize extends Vue {

        @Prop(Boolean)
        public readonly isAuthenticated!: boolean;

        @Prop(Boolean)
        public readonly isAnonymous!: boolean;

        @Prop()
        public readonly hasAnyPermission: UserPermission[] | undefined;

        @Prop()
        public readonly hasAnyOfGivenPermissions: UserPermission[] | undefined;

        @Prop()
        public readonly hasPermissions: UserPermission[] | undefined;

        @userModule.Getter('isAuthenticated')
        public readonly isAuthenticatedGetter!: boolean;

        @userModule.Getter('isAnonymous')
        public readonly isAnonymousGetter!: boolean;

        @userModule.Getter('hasAnyPermission')
        public readonly hasAnyPermissionGetter!: (permissions: UserPermission[]) => boolean;

        @userModule.Getter('hasAnyOfGivenPermissions')
        public readonly hasAnyOfGivenPermissionsGetter!: (permissions: UserPermission[]) => boolean;

        @userModule.Getter('hasPermissions')
        public readonly hasPermissionsGetter!: (permissions: UserPermission[]) => boolean;

        public show(): boolean {

            let result = true;

            if (this.isAuthenticated && result) {
                result = this.isAuthenticatedGetter;
            }

            if (this.isAnonymous && result) {
                result = this.isAnonymousGetter;
            }

            if(this.hasAnyPermission && result) {
                result = this.hasAnyPermissionGetter(this.hasAnyPermission);
            }

            if(this.hasAnyOfGivenPermissions && result) {
                result = this.hasAnyOfGivenPermissionsGetter(this.hasAnyOfGivenPermissions);
            }

            if(this.hasPermissions && result) {
                result = this.hasPermissionsGetter(this.hasPermissions);
            }

            return result;
        }
    }
</script>
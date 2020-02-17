<template>
    <transition name="expand" appear>
        <div key="content" v-if="show()" style="display: contents">
            <slot></slot>
        </div>
        <div key="not-authorized" v-else="!show()" style="display: contents">
            <slot name="not-authorized"></slot>
        </div>
    </transition>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {UserPermission} from '@/modules/user/store/userState';
    import {UserGetter} from '@/modules/user/store/userStore';
    import {Getter} from "vuex-class";

    @Component
    export default class PreAuthorize extends Vue {

        @Prop()
        public readonly isAuthenticated?: boolean;

        @Prop()
        public readonly isAnonymous?: boolean;

        @Prop()
        public readonly hasAnyPermission?: UserPermission[];

        @Prop()
        public readonly hasAnyOfGivenPermissions?: UserPermission[];

        @Prop()
        public readonly hasPermissions?: UserPermission[];

        @Getter(UserGetter.IS_AUTHENTICATED)
        public readonly isAuthenticatedGetter!: boolean;

        @Getter(UserGetter.IS_ANONYMOUS)
        public readonly isAnonymousGetter!: boolean;

        @Getter(UserGetter.HAS_ANY_PERMISSION)
        public readonly hasAnyPermissionGetter!: (permissions: UserPermission[]) => boolean;

        @Getter(UserGetter.HAS_ANY_OF_GIVEN_PERMISSIONS)
        public readonly hasAnyOfGivenPermissionsGetter!: (permissions: UserPermission[]) => boolean;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissionsGetter!: (permissions: UserPermission[]) => boolean;

        public show(): boolean {

            let result = true;

            if (this.isAuthenticated && result) {
                result = this.isAuthenticatedGetter;
            }

            if (this.isAnonymous && result) {
                result = this.isAnonymousGetter;
            }

            if (this.hasAnyPermission && result) {
                result = this.hasAnyPermissionGetter(this.hasAnyPermission);
            }

            if (this.hasAnyOfGivenPermissions && result) {
                result = this.hasAnyOfGivenPermissionsGetter(this.hasAnyOfGivenPermissions);
            }

            if (this.hasPermissions && result) {
                result = this.hasPermissionsGetter(this.hasPermissions);
            }

            return result;
        }
    }
</script>
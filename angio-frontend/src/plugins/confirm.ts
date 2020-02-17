import {ConfirmType} from '@/modules/analyse/helpers/confirm';

declare module 'vue/types/vue' {
    export interface Vue {
        $confirm: (title: string,  type: ConfirmType, message?: string) => Promise<boolean>;
    }

    export interface VueConstructor {
        $confirm: (title: string, type: ConfirmType, message?: string) => Promise<boolean>;
    }
}
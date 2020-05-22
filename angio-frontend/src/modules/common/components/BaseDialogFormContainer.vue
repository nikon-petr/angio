<template>
    <v-dialog
            v-bind:value="open"
            v-bind:style="{ zIndex: 300 }"
            v-bind:max-width="maxWidth"
            persistent
    >
        <v-card class="elevation-12 pa-3">

            <v-card-title class="headline font-weight-regular justify-space-between">
                <span>{{ title }}</span>
                <v-spacer></v-spacer>
            </v-card-title>

            <v-card-text>
                <slot name="form"></slot>
            </v-card-text>
            <v-card-actions>
                <slot name="buttons"></slot>
            </v-card-actions>

        </v-card>
    </v-dialog>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component({})
    export default class BaseDialogFormContainer extends Vue {

        @Prop()
        public readonly title!: string;

        @Prop()
        public readonly open!: boolean;

        @Prop({default: 450})
        public readonly maxWidth!: number;
    }
</script>

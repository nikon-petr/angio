<template>
    <v-expand-transition>
        <ul v-if="errorMessages.length > 0" data-test-id="formMsg__list">
            <li v-for="message in errorMessages" class="red--text body-1">{{ message }}</li>
        </ul>
    </v-expand-transition>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BuiltInErrorMessage extends Vue {

        @Prop()
        public readonly errorMessages!: string[];
    }
</script>

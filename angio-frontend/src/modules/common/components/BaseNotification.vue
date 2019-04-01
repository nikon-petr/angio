<template>
    <div class="v-snack v-snack--multi-line 'v-snack--auto-height'">
        <div
                v-bind:class="cssClass"
                v-bind:style="cssStyle">
            <div class="v-snack__content">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';

    @Component
    export default class BaseNotification extends Vue {

        @Prop()
        readonly color!: string;

        private cssClass = ['v-snack__wrapper'];

        private cssStyle = {};

        private static isCssColor (color?: string | false): boolean {
            return !!color && !!color.match(/^(#|(rgb|hsl)a?\()/)
        }

        @Watch('color', { immediate: true, deep: true })
        watchColor(newVal: string, oldVal: string) {
            if (BaseNotification.isCssColor(newVal)) {
                this.cssStyle = {
                    'background-color': `${newVal}`,
                    'border-color': `${newVal}`
                };
            } else if (newVal) {
                this.cssClass = ['v-snack__wrapper', newVal];
            }
        }
    }
</script>

<style scoped>

</style>
<template>
    <AboutCard
            v-bind:frontend-version="frontendVersion"
            v-bind:backend-version="backendVersion"
    ></AboutCard>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import AboutCard from '@/modules/common/components/AboutCard.vue';

    @Component({
        components: {AboutCard}
    })
    export default class About extends Vue {

        public frontendVersion: string = process.env.VUE_APP_VERSION || '';

        // TODO: fetch api version
        public backendVersion: string = '';
    }
</script>

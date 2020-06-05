<template>
    <AboutCard
            v-bind:frontend-version="frontendVersion"
            v-bind:backend-version="backendVersion"
    ></AboutCard>
</template>

<script lang="ts">
    import ActuatorApiService from '@/modules/common/service/actuatorApiService';
    import {Component, Vue} from 'vue-property-decorator';
    import AboutCard from '@/modules/common/components/AboutCard.vue';

    @Component({
        components: {AboutCard}
    })
    export default class About extends Vue {

        public frontendVersion: string = process.env.VUE_APP_VERSION || '';

        public backendVersion: string = '';

        public mounted() {
            ActuatorApiService.getInfo()
                .then(response => {
                    if (response.data.build) {
                        this.backendVersion = response.data.build.version;
                    }
                })
                .catch(error => this.$logger.error(error));
        }
    }
</script>

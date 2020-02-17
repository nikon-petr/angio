<template>
    <Error
            v-bind:code="500"
            v-bind:description="$t('common.view.500.description')"
    ></Error>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import Error from '@/modules/common/components/Error.vue';

    @Component({
        components: {
            Error,
        },
    })
    export default class ServerError extends Vue {
    }
</script>
<template>
    <Error
            code="500"
            :description="$t('common.view.500.description')"
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
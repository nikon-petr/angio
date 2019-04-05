<template>
    <Error
            code="404"
            :description="$t('common.view.404.description')"
    ></Error>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import Error from '@/modules/common/components/Error.vue';

    @Component({
        components: {Error},
    })
    export default class NotFound extends Vue {
    }
</script>
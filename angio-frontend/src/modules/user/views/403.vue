<template>
    <Error
            v-bind:code="403"
            v-bind:description="$t('user.view.403.description')"
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
    export default class Forbidden extends Vue {
    }
</script>
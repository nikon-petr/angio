<template>
    <p>{{ $t('message', { name: 'Петя' }) }}</p>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class HelloI18n extends Vue {
    }
</script>

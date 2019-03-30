<template>
    <div class="home">
        <img alt="Vue logo" src="../assets/logo.png">
        <HelloI18n></HelloI18n>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import HelloI18n from '@/modules/common/components/HelloI18n.vue';

    @Component({
        components: {
            HelloI18n,
        },
    })
    export default class Home extends Vue {
    }
</script>

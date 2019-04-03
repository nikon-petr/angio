<template>
    <v-list three-line>
        <v-list-tile avatar>
            <v-list-tile-avatar color="primary">
                <span class="white--text headline">{{ fullName.lastname | truncate(1, '') }}</span>
            </v-list-tile-avatar>

            <v-list-tile-content>
                <v-list-tile-title>{{ fullNameString() }}</v-list-tile-title>
                <v-list-tile-sub-title>{{ organization | placeholder('Без организации') }}</v-list-tile-sub-title>
            </v-list-tile-content>
        </v-list-tile>
    </v-list>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import FullName from "../../common/models/fullName";

    @Component
    export default class UserMenuAvatar extends Vue {

        @Prop()
        fullName!: FullName;

        @Prop()
        organization?: string;

        fullNameString() {
            let words = [this.fullName.lastname, this.fullName.firstname, this.fullName.patronymic];
            return words.join(' ');
        }
    }
</script>

<style scoped>

</style>
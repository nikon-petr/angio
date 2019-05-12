<template lang="html">
    <div class="text-xs-center">
        <v-pagination
                v-bind:value="pagination.page"
                v-bind:length="totalPages"
                v-bind:total-visible="7"
                v-on:input="updatePagination"
                prev-icon="arrow_left"
                next-icon="arrow_right"
                circle
        ></v-pagination>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import Pagination from '@/modules/common/helpers/pagination';

    @Component
    export default class AnalyseListTablePagination extends Vue {

        @Prop()
        public readonly pagination!: Pagination;

        get totalPages() {
            if (this.pagination.rowsPerPage == null || this.pagination.totalItems == null) {
                return 0;
            }

            return Math.ceil(this.pagination.totalItems / this.pagination.rowsPerPage);
        }

        @Emit('update:pagination')
        public updatePagination(page: number) {
            return {...this.pagination, page};
        }
    }
</script>

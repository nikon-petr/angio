<template lang="html">
    <div v-if="totalPages > 0" class="text-xs-center">
        <v-pagination
                v-bind:value="pagination.page"
                v-bind:length="totalPages"
                v-bind:total-visible="7"
                v-bind:disabled="disabled"
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
    export default class AnalyseListPagination extends Vue {

        @Prop()
        public readonly page!: number;

        @Prop()
        public readonly rowsPerPage!: number;

        @Prop()
        public readonly totalItems!: number;

        @Prop()
        public readonly disabled!: boolean;

        get totalPages() {
            if (this.pagination.rowsPerPage == null || this.pagination.totalItems == null) {
                return 0;
            }

            return Math.ceil(this.pagination.totalItems / this.pagination.rowsPerPage);
        }

        get pagination(): Pagination {
            return {
                sortBy: null,
                descending: null,
                page: this.page,
                rowsPerPage: this.rowsPerPage,
                totalItems: this.totalItems
            }
        }

        @Emit('update:page')
        public updatePagination(pagination: Pagination) {
            return pagination.page;
        }
    }
</script>

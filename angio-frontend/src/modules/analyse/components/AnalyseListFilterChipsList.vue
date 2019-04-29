<template>
    <v-flex xs12 pa-0>
        <transition-group
                v-bind:duration="{ enter: 200, leave: 200 }"
                name="scale-transition"
                type="transition"
                move-class="list-complete-item"
                tag="div"
                appear
        >
            <v-chip
                    v-if="search"
                    v-on:input="() => $emit('update:search', undefined)"
                    key="search"
                    class="ml-0 mt-4 elevation-2"
                    label
                    close
            >
                {{ $t('analyse.component.analyseListFilter.chips.search', [search]) }}
            </v-chip>
            <v-chip
                    v-if="statuses && statuses.length > 0"
                    v-on:input="() => $emit('update:statuses', [])"
                    key="statuses"
                    class="mt-4 elevation-2"
                    label
                    close
            >
                {{ $t('analyse.component.analyseListFilter.chips.statuses', [translateStatuses(statuses)]) }}
            </v-chip>
            <v-chip
                    v-if="singleDate"
                    v-on:input="() => $emit('update:single-date', undefined)"
                    key="date"
                    class="mt-4 elevation-2"
                    label
                    close
            >
                {{ $t('analyse.component.analyseListFilter.chips.singleDate', [formatDate(date)]) }}
            </v-chip>
            <v-chip
                    v-if="startDate && endDate"
                    v-on:input="() => { $emit('update:start-date', undefined); $emit('update:end-date', undefined) }"
                    key="datePeriod"
                    class="mt-4 elevation-2"
                    label
                    close
            >
                {{ $t('analyse.component.analyseListFilter.chips.datePeriod', [formatDate(startDate),
                formatDate(endDate)]) }}
            </v-chip>
            <v-chip
                    v-if="isStarred"
                    v-on:input="() => $emit('update:is-starred', undefined)"
                    key="isStarred"
                    class="mt-4 mr-0 elevation-2"
                    label
                    close
            >
                {{ $t('analyse.component.analyseListFilter.chips.isStarred') }}
            </v-chip>
        </transition-group>
    </v-flex>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseStatusType} from '@/modules/analyse/models/analyse';

    @Component
    export default class AnalyseListFilter extends Vue {

        @Prop()
        public search?: string;

        @Prop()
        public statuses?: AnalyseStatusType[];

        @Prop()
        public startDate?: Date;

        @Prop()
        public endDate?: Date;

        @Prop()
        public singleDate?: Date;

        @Prop()
        public isStarred?: boolean;

        public translateStatuses(statuses: AnalyseStatusType[]): string {
            return statuses.map((s) => this.$t(`analyse.component.analyseListFilter.status.${s}`).toString()).join(', ');
        }

        public formatDate(date: Date): string {
            // @ts-ignore
            return this.$moment(date, 'YYYY-MM-DD').format('DD.MM.YYYY');
        }
    }
</script>

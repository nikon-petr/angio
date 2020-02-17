export default interface Pagination {
    sortBy: string | null;
    descending: boolean | null;
    page: number | null;
    rowsPerPage: number | null;
    totalItems: number | null;
}
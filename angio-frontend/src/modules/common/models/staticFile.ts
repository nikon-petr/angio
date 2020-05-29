export default interface StaticFile {
    id?: string;
    filename?: string;
    url?: string;
    type?: StaticFileType
}

export enum StaticFileType {
    DOCUMENT = "DOCUMENT",
    IMAGE = "IMAGE",
    VIDEO = "VIDEO"
}

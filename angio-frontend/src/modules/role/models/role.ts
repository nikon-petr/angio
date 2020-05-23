export interface Role {
    id: number;
    name: string;
    description: string;
    permissions: Permission[];
    systemRole: boolean;
}

export interface Permission {
    id: number;
    name: string;
    description: string;
}

export interface UpdateRoleModel {
    id?: number;
    description: string;
    permissionIds: number[];
}

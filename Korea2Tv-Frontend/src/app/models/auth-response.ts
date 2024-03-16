export interface AuthResponse {
    accessToken: string;
    username: string;
    email: string;
    roles: string;
    permissions: string[];
}
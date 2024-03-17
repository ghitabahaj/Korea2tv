export interface ApiResponse<T> {
    message: string,
    result: T,
    errorsValidation: string,
    error: string
}

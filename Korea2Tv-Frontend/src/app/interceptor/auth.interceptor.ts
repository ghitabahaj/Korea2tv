import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalStorageServiceService } from '../service/local-storage/local-storage-service.service';
import { authUtils } from '../helper/auth';
import { AuthService } from '../service/auth/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    // add authorization header with jwt token if available
    const authUserJson = authUtils.currentAccessToken();        

    if (authUserJson != null) {
        // Parse the JSON string to an object
        const authUserParse = JSON.parse(authUserJson);

        // Extract the accessToken property
        const accessToken = authUserParse.accessToken;            

        request = request.clone({
            setHeaders: {
                Authorization: `Bearer ${accessToken}`
            }
        });

    }
    return next.handle(request);
}
}

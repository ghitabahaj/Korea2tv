import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalStorageServiceService } from '../service/local-storage/local-storage-service.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private localService: LocalStorageServiceService) {}


  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const token = this.localService.getUserToken();
      console.log(token)
    if(token){


      const authReq = request.clone({
        headers: request.headers.set('Authorization', `Bearer ${token}`)
      });

      // Send the newly created request
      return next.handle(authReq);
    }

    return next.handle(request);
  }
}

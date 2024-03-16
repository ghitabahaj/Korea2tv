import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User';
import { UserLogin } from 'src/app/models/User-login';
import { AuthResponse } from 'src/app/models/auth-response';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly URL_BASE = "http://localhost:8081/api/v1/auth/";

  constructor(private _http : HttpClient) {}

  login(UserEntity: UserLogin) : Observable<AuthResponse>{
    console.log("login");

    return this._http.post<AuthResponse>(this.URL_BASE+'login', UserEntity)
  }

  register(UserEntity: User) : Observable<AuthResponse>{
    return this._http.post<AuthResponse>(this.URL_BASE+'register', UserEntity)
  }

  checkJwtValidity(token: string) : Observable<boolean> {
    return this._http.get<boolean>(this.URL_BASE+`checkJwtValidity/${token}`)
  }}

import { HttpClient } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User';
import { UserLogin } from 'src/app/models/User-login';
import { AuthResponse } from 'src/app/models/auth-response';
import { Register } from 'src/app/models/register';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiServerUrl = "http://localhost:8080/api/v1.0.0/auth";

  constructor(private http: HttpClient) { }

  login(userObj: User): Observable<Token>{
    return this.http.post<Token>(`${this.apiServerUrl}/authenticate`, userObj);
  }

  register(userObj: Register): Observable<Token>{
    return this.http.post<Token>(`${this.apiServerUrl}/register`, userObj);
  }

}

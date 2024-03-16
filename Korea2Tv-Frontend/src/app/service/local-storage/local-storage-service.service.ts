import { Injectable } from '@angular/core';
import { AuthResponse } from 'src/app/models/auth-response';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageServiceService {

  responseString : string | null = localStorage.getItem('user');
  response: AuthResponse = this.responseString ? JSON.parse(this.responseString) : {};


  setUser(user:AuthResponse){
    localStorage.setItem('user', JSON.stringify(user));
  }

  getUserToken(): string | null {
    return this.response.accessToken;

  }


  clearLocalStorage() {
    localStorage.clear();
  }
}

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { authUtils } from '../helper/auth';

@Injectable({
  providedIn: 'root'
})
export class GuardGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(){
    if(authUtils.isLoggedIn()){
      return true;
    }else{
      this.router.navigate(['/auth/sign-in'])
      return false;
    }
  }
  
}

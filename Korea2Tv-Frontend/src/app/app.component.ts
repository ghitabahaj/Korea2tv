import { Component,HostListener } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { debounceTime } from 'rxjs/operators';
import { MovieApiServiceService } from './service/movie-api-service.service';
import { Router } from '@angular/router';
import { AuthService } from './service/auth/auth.service';
import { Register } from './models/register';
import { authUtils } from './helper/auth';
import Swal from 'sweetalert2';
import { User } from './models/User';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  registrationForm!: FormGroup;
  signInForm!: FormGroup;
  submitted = false;
  error = '';
  isAuthenticated = false;
  passwordTextType: boolean = false;


  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.signInForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.isAuthenticated = authUtils.isLoggedIn();
  }

  get f() {
    return this.registrationForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
  
    if (this.registrationForm.invalid) {
      return;
    }
  
    this.authService.register(this.registrationForm.value).subscribe({
      next: (response: Register) => {
        console.log(response);
        Swal.fire({
          icon: 'success',
          title: 'Registration Successful',
          text: 'You have successfully registered.',
          confirmButtonColor: '#3085d6',
          confirmButtonText: 'OK'
        }).then((result) => {
          if (result.isConfirmed) {
            // Close the registration modal
            this.closeRegistrationModal();
            this.router.navigate(['/']);
          }
        });
      },
      error: error => {
        console.error(error);
        Swal.fire({
          icon: 'error',
          title: 'Registration Error',
          text: 'An error occurred during registration. Please try again later.',
          confirmButtonColor: '#d33',
          confirmButtonText: 'OK'
        });
      }
    });
  }
  
  logout(): void {
    Swal.fire({
      icon: 'info',
      title: 'Logout Confirmation',
      text: 'Are you sure you want to logout?',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Logout'
    }).then((result) => {
      if (result.isConfirmed) {
        authUtils.logout();
        this.isAuthenticated = false;
        Swal.fire({
          icon: 'success',
          title: 'Logout Successful',
          text: 'You have been logged out successfully.',
          confirmButtonColor: '#3085d6',
          confirmButtonText: 'OK'
        }).then(() => {
          // Close the registration modal
          this.closeRegistrationModal();
        });
      }
    });
  }

  closeRegistrationModal(): void {

    const modal = document.getElementById('registrationModal');
    console.log(modal);
     // Check if the modal element exists
     if (modal) {

      }
  }

  togglePasswordTextType(): void {
    this.passwordTextType = !this.passwordTextType;
  }

  onSignIn(): void {
    this.submitted = true;
    const { email, password } = this.signInForm.value;

    // Stop here if form is invalid
    if (this.signInForm.invalid) {
      return;
    } else {
      this.authService.login(this.signInForm.value).subscribe({
        next: (response: User) => {
          console.log(response);
          this.setLoggedCredentials(response);
          Swal.fire({
            icon: 'success',
            title: 'Sign In Successful',
            text: 'You have been successfully logged in.',
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/']);
          });
        },
        error: error => {
          this.error = error ? error : 'An error occurred during sign in.';
          Swal.fire({
            icon: 'error',
            title: 'Sign In Error',
            text: this.error,
            confirmButtonColor: '#d33',
            confirmButtonText: 'OK'
          });
        }
      });
    }
  }

  setLoggedCredentials(user: User) {
    localStorage.setItem('authUser', JSON.stringify(user));
  }
}

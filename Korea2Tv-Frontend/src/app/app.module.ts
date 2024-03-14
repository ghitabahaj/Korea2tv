import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { SearchComponent } from './pages/search/search.component';
import { MovieDetailsComponent } from './pages/movie-details/movie-details.component';
import {HttpClientModule} from '@angular/common/http';
import { MovieApiServiceService } from './service/movie-api-service.service';
import {ReactiveFormsModule} from '@angular/forms';
import { SignupComponent } from './pages/auth/signup/signup.component';
import { ForgetPasswordComponent } from './pages/auth/forget-password/forget-password.component';
import { SafePipe } from './pages/pipe/safe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchComponent,
    MovieDetailsComponent,
    SignupComponent,
    ForgetPasswordComponent,
    SafePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule

  ],
  providers: [MovieApiServiceService],
  bootstrap: [AppComponent],
  exports: [SafePipe]
})
export class AppModule { }

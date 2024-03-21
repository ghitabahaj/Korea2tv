import { Component,HostListener } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import { debounceTime } from 'rxjs/operators';
import { MovieApiServiceService } from './service/movie-api-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  
}

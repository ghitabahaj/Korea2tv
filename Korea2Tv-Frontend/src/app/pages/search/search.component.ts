import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MovieApiServiceService } from 'src/app/service/movie-api-service.service';
import { Router } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { Media } from 'src/app/models/media';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchResults: Media[] = [];
  searchTermControl: FormControl = new FormControl();

  constructor(private fb: FormBuilder, private service: MovieApiServiceService, private router: Router) { }

  ngOnInit(): void {
    this.searchTermControl.valueChanges.pipe(
      debounceTime(300)
    ).subscribe(() => {
      this.onSearch();
    });
  }

  onMovieClick(movieId: string): void {
    this.router.navigate(['/movie', movieId]);
  }

  onSearch(): void {
    const searchTerm = this.searchTermControl.value.trim();
    if (searchTerm === '') {
      this.searchResults = [];
      return; // Exit early if search term is empty
    }
    
    this.service.searchMovies(searchTerm).subscribe(
      (response: any) => {
        this.searchResults = response.result;
        console.log(this.searchResults, 'searchResults#')
      },
      (error) => {
        console.error('Error fetching movie search results:', error);
        // Handle error as needed
      }
    );
  }
}

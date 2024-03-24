import { Component, OnInit } from '@angular/core';
import { MovieApiServiceService } from 'src/app/service/movie-api-service.service';
import { Title,Meta } from '@angular/platform-browser';
import { Observable, catchError, map, of, startWith } from 'rxjs';
import { ApiResponse } from 'src/app/models/api-response';
import { Media } from 'src/app/models/media';
import { Page } from 'src/app/models/pageable';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  mediaState$!: Observable<{ appState: string, appData?: ApiResponse<Page<Media>> }>;

  constructor(private service: MovieApiServiceService, private title: Title, private meta: Meta) {
    this.meta.updateTag({ name: 'description', content: 'watch online movies' });

  }
  bannerResult: any = [];
  koreanMovieResult: Media[] = []; // New property for Korean movies
  typeMedia!: string;
  trendingMovieResult: any = [];

  ngOnInit(): void {
    this.bannerData();
    this.trendingData();    
    this.getMedia("movie"); 
  }


  // bannerdata
  bannerData() {
    this.service.bannerApiData().subscribe(
      (result) => {
        if (result && result.result && result.result.trendingKoreanMovies) {
          this.bannerResult = result.result.trendingKoreanMovies;
        } else {
          console.error("Unexpected response format:", result);
          // Handle unexpected response format here
        }
      },
      (error) => {
        console.error("Error fetching banner data:", error);
        // Handle error here
      }
    );
  }

  public getMedia(typeMedia: string) {
    this.mediaState$ = this.service.getMedia(typeMedia).pipe(
      
      map((response: ApiResponse<Page<Media>>) => {
        
        console.log(response, 'response#');
        
        return { appState: "app_loaded", appData: response };

      }),
      startWith({ appState: "app_loading"}),
      catchError((error: any) => of({ appState: 'app_error', error }))
    );
  }

  


  trendingData() {
    this.service.trendingMovieApiData().subscribe((result) => {
      console.log(result, 'trendingresult#');
      this.trendingMovieResult = result.results;
    });
  }

}

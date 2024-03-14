import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MovieApiServiceService } from 'src/app/service/movie-api-service.service';
import { Title,Meta } from '@angular/platform-browser';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { forkJoin } from 'rxjs';


@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {

  

  constructor(private service:MovieApiServiceService,private router:ActivatedRoute,private title:Title,private meta:Meta ,private sanitizer: DomSanitizer) { }
  getMovieDetailResult:any;
  getMovieVideoResult:any;
  getMovieCastResult:any;
  movieVideos: any;
  relatedMovies: any;

  ngOnInit(): void {
    let getParamId = this.router.snapshot.paramMap.get('id');
    console.log(getParamId,'getparamid#');
  
    this.getMovie(getParamId);
    this.getVideo(getParamId);
    this.getMovieCast(getParamId);
  }
  generateStarsArray(rating: number): number[] {
    const maxStars = 5; // Total number of stars
    const filledStars = Math.floor(rating / 2); // Calculate the number of filled stars (1 filled star for every 2 rating)
    const emptyStars = maxStars - filledStars; // Calculate the number of empty stars
    return Array(filledStars).fill(1).concat(Array(emptyStars).fill(0)); // Generate an array with filled and empty star values
  }
  getMovie(id:any){
    this.service.getMovieDetails(id).subscribe(async(result)=>{
        console.log(result,'getmoviedetails#');
        this.getMovieDetailResult = await result;


        // updatetags
        this.title.setTitle(`${this.getMovieDetailResult.original_title} | ${this.getMovieDetailResult.tagline}`);
        this.meta.updateTag({name:'title',content:this.getMovieDetailResult.original_title});
        this.meta.updateTag({name:'description',content:this.getMovieDetailResult.overview});
     
        // facebook
        this.meta.updateTag({property:'og:type',content:"website"});
        this.meta.updateTag({property:'og:url',content:``});
        this.meta.updateTag({property:'og:title',content:this.getMovieDetailResult.original_title});
        this.meta.updateTag({property:'og:description',content:this.getMovieDetailResult.overview});
        this.meta.updateTag({property:'og:image',content:`https://image.tmdb.org/t/p/original/${this.getMovieDetailResult.backdrop_path}`});
        this.fetchRelatedMovies(); 

    });
  }
  getVideo(id: any) {
    this.service.getMovieVideo(id).subscribe((result) => {
      console.log(result, 'getMovieVideo#');
      this.movieVideos = result.results.filter((element: any) => element.type === "Trailer");
      console.log(this.movieVideos, 'movieVideos#');
      if (this.movieVideos.length > 0) {
        this.getMovieVideoResult = this.movieVideos[0].key; // Set the first trailer key as the default
        console.log(this.getMovieVideoResult, 'getMovieVideoResult#');
      }
    });
  }
  
  getTrailerUrl(videoKey: string): string {
    const baseUrl = 'https://www.youtube.com/embed/';
    // Construct the URL with the dynamic video key
    return `${baseUrl}${videoKey}`;
  }
  getMovieCast(id:any)
  {
    this.service.getMovieCast(id).subscribe((result)=>{
      console.log(result,'movieCast#');
      this.getMovieCastResult = result.cast;
    });
  }

  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  fetchRelatedMovies(): void {
    if (this.getMovieDetailResult && this.getMovieDetailResult.genres) {
      const genreIds = this.getMovieDetailResult.genres.map((genre: any) => genre.id);
      console.log(genreIds, 'genreIds#'); // Log genre IDs for debugging
      this.service.getRelatedMoviesByGenre(genreIds)
        .subscribe((result) => {
          console.log(result, 'relatedMovies#'); // Log related movies for debugging
          this.relatedMovies = result.results;
        });
    }
  }

}

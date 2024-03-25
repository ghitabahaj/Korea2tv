import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieApiServiceService } from 'src/app/service/movie-api-service.service';
import { Title,Meta } from '@angular/platform-browser';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { CommentsService } from 'src/app/service/comments/comments.service';
import { AuthService } from 'src/app/service/auth/auth.service';
import { authUtils } from 'src/app/helper/auth';
import Swal from 'sweetalert2';
import { WatchlistServiceService } from 'src/app/service/watchlistService/watchlist-service.service';
import { jwtDecode } from "jwt-decode";


@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  getMovieVideoResult: any;
  id!: string;
  idTmdb!: string;
  

  
  constructor(private service: MovieApiServiceService,
    private router: ActivatedRoute,
    private route: Router,
    private title: Title,
    private meta: Meta,
    private sanitizer: DomSanitizer,
    private authService: AuthService,
    private watchlistService: WatchlistServiceService) { }

movieDetails: any;
relatedMovies: any[] | undefined;

ngOnInit(): void {
const getParamId = this.router.snapshot.paramMap.get('id');
this.getMovieDetails(getParamId);


}


generateStarsArray(rating: number): number[] {
  const maxStars = 5; // Total number of stars
  const filledStars = Math.floor(rating / 2); // Calculate the number of filled stars (1 filled star for every 2 rating)
  const emptyStars = maxStars - filledStars; // Calculate the number of empty stars
  return Array(filledStars).fill(1).concat(Array(emptyStars).fill(0)); // Generate an array with filled and empty star values
}

getMovieDetails(id: any): void {
  this.service.getDetailsMedia(id).subscribe((result) => {
    console.log(result, 'getmoviedetails#');
    this.movieDetails = result.result;
    // Update meta tags
    this.updateMetaTags();
    // Fetch related movies
    this.fetchRelatedMovies();
    
    // Check if videos are available
    if (this.movieDetails.videos && this.movieDetails.videos.length > 0) {
      // Find the trailer video
      const trailerVideo = this.movieDetails.videos.find((video: any) => video._type === 'Trailer');
      if (trailerVideo) {
        // Extract the trailer key
        this.getMovieVideoResult = trailerVideo._key;
        console.log(this.getMovieVideoResult, 'getMovieVideoResult#');
      }
      if (!trailerVideo) {
        const teaserVideo = this.movieDetails.videos.find((video: any) => video._type.includes('Teaser'));
        this.getMovieVideoResult = teaserVideo._key;
        console.log(this.getMovieVideoResult, 'getMovieVideoResult#');
    }
    }
  });
}

getMediaByTmdbId(idTmdb: number): void {
  this.service.getMediaByIdTmdb(idTmdb).subscribe(
    (result: any) => {
      console.log(result, 'getmoviedetails#');
      this.movieDetails = result.result;

      // Check if videos are available
      if (this.movieDetails.videos && this.movieDetails.videos.length > 0) {
        // Find the trailer video
        const trailerVideo = this.movieDetails.videos.find((video: any) => video._type === 'Trailer');
        if (trailerVideo) {
          // Extract the trailer key
          this.getMovieVideoResult = trailerVideo._key;
          console.log(this.getMovieVideoResult, 'getMovieVideoResult#');
        } else {
          const teaserVideo = this.movieDetails.videos.find((video: any) => video._type.includes('Teaser'));
          this.getMovieVideoResult = teaserVideo._key;
          console.log(this.getMovieVideoResult, 'getMovieVideoResult#');
        }
      }
    },
    ( error: any) => {
      console.error('Error fetching movie details by TMDb ID:', error);
      // Handle error if needed
    }
  );
}

updateMetaTags(): void {
if (this.movieDetails) {
this.title.setTitle(`${this.movieDetails.originalTitle} | ${this.movieDetails.title}`);
this.meta.updateTag({ name: 'title', content: this.movieDetails.originalTitle });
this.meta.updateTag({ name: 'description', content: this.movieDetails.overview });
// Update Facebook meta tags
this.meta.updateTag({ property: 'og:type', content: 'website' });
this.meta.updateTag({ property: 'og:url', content: `YOUR_WEBSITE_URL` }); // Update with your website URL
this.meta.updateTag({ property: 'og:title', content: this.movieDetails.originalTitle });
this.meta.updateTag({ property: 'og:description', content: this.movieDetails.overview });
this.meta.updateTag({ property: 'og:image', content: `https://image.tmdb.org/t/p/original/${this.movieDetails.backDropPath}` });
}
}


fetchRelatedMovies(): void {
  if (this.movieDetails && this.movieDetails.genres) {
    const relatedMoviesArray: any[] = []; // Initialize an empty array to store related movies

    this.movieDetails.genres.forEach((genre: any) => {
      if (genre.idTmdb) {
        this.service.getRelatedMoviesByGenre(genre.idTmdb).subscribe((result) => {
          console.log(result, 'relatedMovies#');
          // Assuming result contains related movies for the current genre
          // Push the related movies into the array
          relatedMoviesArray.push(...result.result.relatedMoviesByGenre);
          // Assign the accumulated related movies to this.relatedMovies
          this.relatedMovies = relatedMoviesArray;
        });
      }
    });
  }
}



getSafeImageUrl(path: string): SafeResourceUrl {
return this.sanitizer.bypassSecurityTrustResourceUrl(`https://image.tmdb.org/t/p/original/${path}`);
}

getSafeUrl(url: string): SafeResourceUrl {
  return this.sanitizer.bypassSecurityTrustResourceUrl(url);
}

getTrailerUrl(videoKey: string): string {
const baseUrl = 'https://www.youtube.com/embed/';
// Construct the URL with the dynamic video key
return `${baseUrl}${videoKey}`;
}

navigateToStreamingPage(movieId: number) {
  this.route.navigate(['/streaming/' + movieId], { relativeTo: this.router });
}

reloadCurrentRoute(newUrl: string) {
  // Get the current URL
  const currentUrl = newUrl;

  // Navigate to the current route
  this.route.navigateByUrl('/').then(() => {
    this.route.navigate([currentUrl] , { relativeTo: this.router });
  });
}

addToWatchlist() {
  if (authUtils.isLoggedIn()) {
    const authUserJson = authUtils.currentAccessToken() as string;
    const authUser = JSON.parse(authUserJson);
    const accessToken = authUser.accessToken;
    const decodedJwt: any = jwtDecode(accessToken);
    const userEmail = decodedJwt.sub;
    console.log(userEmail, 'userEmail');

    const movieImdbId = this.movieDetails.idTmdb;

    console.log(authUser, 'authUser');
    console.log(this.movieDetails, 'movieDetails');
    console.log(userEmail, 'userEmail');
    console.log(movieImdbId, 'movieImdbId');

    this.watchlistService.addToWatchlist(userEmail, movieImdbId).subscribe(
      (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Added to Watchlist',
          text: 'The movie has been added to your watchlist successfully.',
          confirmButtonColor: '#28a745'
        });
        console.log(response);
      },
      error => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Failed to add the movie to your watchlist. Please try again later.',
          confirmButtonColor: '#dc3545'
        });
        console.log(error);
      }
    );
  } else {
    Swal.fire({
      icon: 'error',
      title: 'Authentication Required',
      text: 'You need to be authenticated to add to your watchlist.',
      confirmButtonColor: '#dc3545'
    });
  }
}


}

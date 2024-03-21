import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, of, switchMap } from 'rxjs';
import { ApiResponse } from '../models/api-response';
import { Media } from '../models/media';
import { Page } from '../models/pageable';
@Injectable({
  providedIn: 'root'
})
export class MovieApiServiceService {

  constructor(private http: HttpClient) { }

  baseurl = "https://api.themoviedb.org/3";
  apikey = "08cc33bd5ae3a747598ce2ad84376e66";

  private apiServerUrl = "http://localhost:8080/api/v1.0.0/media";

  public getMedia(typeMedia: string, searchTerm: string='', numPage: number=0, numSize: number=30): Observable<ApiResponse<Page<Media>>>{
    return this.http.get<ApiResponse<Page<Media>>>(`${this.apiServerUrl}?typeMedia=${typeMedia}&searchTerm=${searchTerm}&numPage=${numPage}&numSize=${numSize}`);
  }

  public getDetailsMedia(shortLink: string): Observable<ApiResponse<Media>>{
    return this.http.get<ApiResponse<Media>>(`${this.apiServerUrl}/${shortLink}`);
  }
  //bannerapidata

  bannerApiData(): Observable<any> {
    return this.http.get(`${this.apiServerUrl}/trending-korean-movies`);
  }
  
searchMovieApiData(name : string): Observable<any> {
  return this.http.get(`${this.apiServerUrl}/search/${name}`);
}

  getRelatedMoviesByGenre(genreId: number): Observable<any> {
    return this.http.get(`${this.apiServerUrl}/related-movies-by-genre/${genreId}`);
  }
  
  // trendingmovieapidata 
  trendingMovieApiData(): Observable<any> {
    return this.http.get(`${this.baseurl}/trending/movie/day?api_key=${this.apikey}`);
  }

  // searchmovive
  getSearchMovie(data: any): Observable<any> {
    console.log(data, 'movie#');

    return this.http.get(`${this.baseurl}/search/movie?api_key=${this.apikey}&query=${data.movieName}`);
  }

  getActorDetails(actorId: number): Observable<any> {
    return this.http.get(`${this.baseurl}/person/${actorId}?api_key=${this.apikey}`);
  }

  getActorMovies(actorId: number): Observable<any> {
    return this.http.get(`${this.baseurl}/person/${actorId}/movie_credits?api_key=${this.apikey}`);
  }


  // getmoviedatails
  getMovieDetails(data: any): Observable<any> {
    return this.http.get(`${this.baseurl}/movie/${data}?api_key=${this.apikey}`)
  }

  // getMovieVideo
  getMovieVideo(data: any): Observable<any> {
    return this.http.get(`${this.baseurl}/movie/${data}/videos?api_key=${this.apikey}`)
  }

  // getMovieCast
  getMovieCast(data: any): Observable<any> {
    return this.http.get(`${this.baseurl}/movie/${data}/credits?api_key=${this.apikey}`)
  }

  getMediaByCredit(creditId: string): Observable<Media> {
    return this.http.get<Media>(`${this.apiServerUrl}/actor?creditIdTmdb=${creditId}`).pipe(
      catchError(error => {
        // Handle errors here
        console.error('Error fetching media by credit:', error);
        throw error; // Rethrow the error to propagate it to the component
      })
    );
  }


  getMediaByIdTmdb(idTmdb: number): Observable<Media> {
    return this.http.get<Media>(`${this.apiServerUrl}/search/${idTmdb}`).pipe(
      catchError(error => {
        // Handle errors here
        console.error('Error fetching media by idTmdb:', error);
        throw error; // Rethrow the error to propagate it to the component
      })
    );
    
  }

  searchMediaByName(name: string): Observable<Media[]> {
    return this.http.get<Media[]>(`${this.apiServerUrl}/search?name=${name}`).pipe(
      catchError(error => {
        console.error('Error searching media by name:', error);
        throw error;
      })
    );
  }


  searchMovies(searchTerm: string): Observable<any> {
    return this.http.get(`${this.apiServerUrl}/searchMovies?query=${searchTerm}`);
  }

}

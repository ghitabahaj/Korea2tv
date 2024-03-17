import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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
  // action 
  fetchActionMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=28`);
  }

  // adventure
  fetchAdventureMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=12`);
  }

  // animation
  fetchAnimationMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=16`);
  }

  // comedy
  fetchComedyMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=35`);
  }

  // documentary
  fetchDocumentaryMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=99`);
  }

  // science-fiction:878

  fetchScienceFictionMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=878`);
  }

  // thriller:53
  fetchThrillerMovies(): Observable<any> {
    return this.http.get(`${this.baseurl}/discover/movie?api_key=${this.apikey}&with_genres=53`);
  }

}

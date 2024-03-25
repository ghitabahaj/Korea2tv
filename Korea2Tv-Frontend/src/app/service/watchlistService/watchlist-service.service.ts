import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Watchlist } from 'src/app/models/watchlist';

@Injectable({
  providedIn: 'root'
})
export class WatchlistServiceService {

  private apiUrl = 'http://localhost:8080/api/v1.0.0/watchlist';

  constructor(private http: HttpClient) { }

  addToWatchlist(userId: number, movieId: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add?userId=${userId}&movieId=${movieId}`, {});
  }

  getWatchlistByUser(userId: number): Observable<Watchlist[]> {
    return this.http.get<Watchlist[]>(`${this.apiUrl}/user/${userId}`);
  }

  removeFromWatchlist(userId: number, movieId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/remove?userId=${userId}&movieId=${movieId}`);
  }
}

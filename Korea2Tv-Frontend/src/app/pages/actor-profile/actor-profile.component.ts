import { Component, OnInit } from '@angular/core';
import { Title, Meta, DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MovieApiServiceService } from 'src/app/service/movie-api-service.service';

@Component({
  selector: 'app-actor-profile',
  templateUrl: './actor-profile.component.html',
  styleUrls: ['./actor-profile.component.css']
})
export class ActorProfileComponent implements OnInit {

  actorId!: number;
  actorDetails: any;
  actorMovies: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieApiServiceService
  ) { }

  ngOnInit(): void {
    this.actorId = this.route.snapshot.params['id'];
    this.getActorDetails(this.actorId);
  }

  getActorDetails(id: number) {
    this.movieService.getActorDetails(id).subscribe((data) => {
      this.actorDetails = data;
      this.getActorMovies(id);
    });
  }

  getActorMovies(id: number) {
    this.movieService.getActorMovies(id).subscribe((data) => {
      this.actorMovies = data.cast;
    });
  }

}

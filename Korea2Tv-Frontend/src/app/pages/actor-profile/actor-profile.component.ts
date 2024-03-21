import { Component, OnInit } from '@angular/core';
import { Title, Meta, DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
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
  mediaList: any[] = [];
  creditString !: string;

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieApiServiceService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.actorId = this.route.snapshot.params['id'];
    this.creditString = this.route.snapshot.params['creditIdTmdb'];
    this.getActorDetails(this.actorId);
    this.fetchMediaByCredit();
  }

  getActorDetails(id: number) {
    this.movieService.getActorDetails(id).subscribe((data) => {
      this.actorDetails = data;
      console.log(this.actorDetails, 'actorDetails#');
      this.getActorMovies(id);
    });
  }

  getActorMovies(id: number) {
    this.movieService.getActorMovies(id).subscribe((data) => {
      this.actorMovies = data.cast;
      console.log(this.actorMovies, 'actorMovies#');
    });
  }

  
reloadCurrentRoute(newUrl: string) {
  // Get the current URL
  const currentUrl = newUrl;

  // Navigate to the current route
  this.router.navigateByUrl('/').then(() => {
    this.router.navigate([currentUrl] , { relativeTo: this.route });
  });
}


fetchMediaByCredit(): void {
  this.movieService.getMediaByCredit(this.creditString.toString()).subscribe(
    (data: any) => {
      this.mediaList = data.result; // Assuming the result contains the list of media
      console.log('Media by credit:', this.mediaList);
    },
    error => {
      console.error('Error fetching media by credit:', error);
      // Handle error if needed
    }
  );
}

}

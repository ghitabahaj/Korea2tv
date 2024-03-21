import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MovieDetailsComponent } from './pages/movie-details/movie-details.component';
import { SearchComponent } from './pages/search/search.component';
import { SigninComponent } from './pages/auth/signin/signin.component';
import { ActorProfileComponent } from './pages/actor-profile/actor-profile.component';
import { StreamingPageComponent } from './pages/stream-movie/streaming-page/streaming-page.component';
import { MovieDetailsActorComponent } from './pages/movie-details-actor/movie-details-actor.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'search', component: SearchComponent },
  { path: 'movie/:id', component: MovieDetailsComponent },
  { path: 'movieActor/:idtmdb', component: MovieDetailsActorComponent },
  { path: 'actor/:id/:creditIdTmdb', component: ActorProfileComponent },
  { path: 'streaming/:movieId', component:StreamingPageComponent  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

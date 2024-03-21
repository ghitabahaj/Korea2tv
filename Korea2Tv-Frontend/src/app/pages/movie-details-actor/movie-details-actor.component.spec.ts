import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieDetailsActorComponent } from './movie-details-actor.component';

describe('MovieDetailsActorComponent', () => {
  let component: MovieDetailsActorComponent;
  let fixture: ComponentFixture<MovieDetailsActorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovieDetailsActorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieDetailsActorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

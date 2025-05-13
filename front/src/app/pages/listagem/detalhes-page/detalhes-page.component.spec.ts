import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalhesPageComponent } from './detalhes-page.component';

describe('DetalhesPageComponent', () => {
  let component: DetalhesPageComponent;
  let fixture: ComponentFixture<DetalhesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalhesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalhesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

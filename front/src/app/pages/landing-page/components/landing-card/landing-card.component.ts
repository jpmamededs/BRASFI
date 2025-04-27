import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-landing-card',
  imports: [],
  templateUrl: './landing-card.component.html',
  styleUrl: './landing-card.component.css'
})
export class LandingCardComponent {
  @Input() title: String = "";
}

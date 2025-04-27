import { Component } from '@angular/core';
import { NavbarComponent } from "./components/navbar/navbar.component";
import { LandingCardComponent } from "./components/landing-card/landing-card.component";
import { BigCardComponent } from "./components/big-card/big-card.component";

@Component({
  selector: 'app-landing-page',
  imports: [NavbarComponent, LandingCardComponent, BigCardComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}

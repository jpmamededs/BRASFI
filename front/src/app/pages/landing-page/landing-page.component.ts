import { Component } from '@angular/core';
import { NavbarComponent } from "./components/navbar/navbar.component";
import { LandingCardComponent } from "./components/landing-card/landing-card.component";
import { BigCardComponent } from "./components/big-card/big-card.component";
import { DepoimentosComponent } from "./components/depoimentos/depoimentos.component";

@Component({
  selector: 'app-landing-page',
  imports: [NavbarComponent, LandingCardComponent, BigCardComponent, DepoimentosComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}

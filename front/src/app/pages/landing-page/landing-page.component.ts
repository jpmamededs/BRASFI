import { Component } from '@angular/core';
import { NavbarComponent } from "./components/navbar/navbar.component";
import { BigCardComponent } from "./components/big-card/big-card.component";
import { DepoimentosComponent } from "./components/depoimentos/depoimentos.component";
import { SmallCardComponent } from "./components/small-card/small-card.component";

@Component({
  selector: 'app-landing-page',
  imports: [NavbarComponent, SmallCardComponent, BigCardComponent, DepoimentosComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {
  cards = [0, 1, 2, 3, 4];
  selectedId: string | null = "0";

  onCardClick(id: string | null) {
    this.selectedId = id;
  }
}

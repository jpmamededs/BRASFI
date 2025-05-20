import { Component } from '@angular/core';
import { NavbarComponent } from "./components/navbar/navbar.component";
import { BigCardComponent } from "./components/big-card/big-card.component";
import { DepoimentosComponent } from "./components/depoimentos/depoimentos.component";
import { SmallCardComponent } from "./components/small-card/small-card.component";
import { FooterComponent } from "./components/footer/footer.component";
import { ContactComponent } from "../contact/contact.component";

@Component({
  selector: 'app-landing-page',
  imports: [SmallCardComponent, BigCardComponent, DepoimentosComponent, FooterComponent, FooterComponent, ContactComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {
  cards = [0, 1, 2, 3, 4];
  selectedId: string | null = "0";
  imgSource:string="assets/images/Group.png";
  imgMulher2:string="assets/images/mulher2.svg";
  imgVector:string="assets/images/Vector.svg";
  imgVoluntario:string="assets/images/voluntario.svg";
  // parceiros
  img1:string="assets/images/1.svg";
  img2:string="assets/images/2.svg";
  img3:string="assets/images/3.svg";
  img4:string="assets/images/4.svg";
  img5:string="assets/images/5.svg";
  img6:string="assets/images/6.svg";
  img7:string="assets/images/7.svg";
  img8:string="assets/images/8.svg";
// depoimentos
  imgCara1:string="assets/images/cara1.svg";
  imgCara2:string="assets/images/cara2.svg";
  imgCara3:string="assets/images/cara3.svg";
  imgCara4:string="assets/images/cara4.svg";
  imgCara5:string="assets/images/cara5.svg";
  imgCara6:string="assets/images/cara6.svg";
  imgCara7:string="assets/images/cara7.svg";

  imgLogo:string = "assets/images/logo-lider.svg";


  onCardClick(id: string | null) {
    this.selectedId = id;
  }
}

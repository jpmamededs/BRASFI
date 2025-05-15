import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
  standalone: true
})
export class FooterComponent {
    imgSource:string="assets/images/Group.png";
    imginsta:string="assets/images/instagram.svg";
    imgyoutube:string="assets/images/youtube.svg";
    imglinkedin:string="assets/images/linkedin.svg";
    imglattes:string="assets/images/lattes.svg";

  constructor() {
    // Importa o Font Awesome diretamente no componente
    const link = document.createElement('link');
    link.rel = 'stylesheet';

    link.href = 'node_modules/@fortawesome/fontawesome-free/css/all.min.css';
    document.head.appendChild(link);
  }
}

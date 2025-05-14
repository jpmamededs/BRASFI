import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
  standalone: true
})
export class FooterComponent {
  constructor() {
    // Importa o Font Awesome diretamente no componente
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'node_modules/@fortawesome/fontawesome-free/css/all.min.css';
    document.head.appendChild(link);
  }
}

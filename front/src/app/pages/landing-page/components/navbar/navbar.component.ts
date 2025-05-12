import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostagemService } from '../../../../services/postagem.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],  // ✅ Corrigido
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']   // ✅ Corrigido
})
export class NavbarComponent {
  postagemService = inject(PostagemService);  // ✅ Injeção de dependência
  router = inject(Router);  // ✅ Injeção de dependência

  logout(): void {
    this.postagemService.logout();
    this.router.navigate(['/login']);
  }
}

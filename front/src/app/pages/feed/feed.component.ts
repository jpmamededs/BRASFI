import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { PostagemService } from '../../services/postagem.service';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../landing-page/components/navbar/navbar.component";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css'],
  imports: [CommonModule, NavbarComponent]
})
export class FeedComponent implements OnInit {
  postagemService = inject(PostagemService);
  router = inject(Router);
  sanitizer = inject(DomSanitizer);

  eventos: any[] = [];
  eventos_noticias: any[] = [];
  eventos_novidades: any[] = [];

  ngOnInit(): void {
    this.carregarEventos();
  }

  carregarEventos(): void {
    this.postagemService.listarPostagens().subscribe({
      next: (data) => {
        console.log('Todas as postagens:', data);

        // ✅ Melhoria: Função para filtrar postagens, reduz repetição
        this.eventos = this.filtrarPostagens(data, 'EVENTO');
        this.eventos_novidades = this.filtrarPostagens(data, 'NOVIDADES');
        this.eventos_noticias = this.filtrarPostagens(data, 'NOTICIAS');
      },
      error: (err) => {
        console.error('Erro ao carregar eventos:', err);
      }
    });
  }

  // ✅ Função para filtrar postagens por tag
  filtrarPostagens(data: any[], tag: string): any[] {
    return data
      .filter((post: any) => post.tag?.toUpperCase() === tag)
      .map((post: any) => ({
        ...post,
        imagemOuVideo: this.getSafeUrl(post.imagemOuVideo)
      }));
  }

  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  logout(): void {
    this.postagemService.logout();
    this.router.navigate(['/login']);
  }
  voltarParaPlataforma(): void {
  this.router.navigate(['/plataforma']);
}
verMais(categoria: string): void {
  // Redireciona para a página da categoria correspondente
  this.router.navigate(['/listagem', categoria.toUpperCase()]);
}

}

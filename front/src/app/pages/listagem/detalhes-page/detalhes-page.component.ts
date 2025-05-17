import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { PostagemService } from '../../../services/postagem.service';
import { NavbarComponent } from "../../landing-page/components/navbar/navbar.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detalhes-page',
  templateUrl: './detalhes-page.component.html',
  styleUrls: ['./detalhes-page.component.css'],
  imports: [NavbarComponent,CommonModule,FormsModule]
})
export class DetalhesPageComponent implements OnInit {

  router = inject(Router);
  route = inject(ActivatedRoute);
  sanitizer: DomSanitizer = inject(DomSanitizer);

  postagem: any;
  comentarios: any[] = [];
  novoComentarioTitulo: string = '';
  novoComentarioConteudo: string = '';
  showComments: boolean = false;

  postagemService = inject(PostagemService);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.carregarPostagem(id);
    this.carregarComentarios(id);
  }

  toggleComments(): void {
    this.showComments = !this.showComments;
  }

  carregarPostagem(id: number): void {
    this.postagemService.buscarPostagemPorId(id).subscribe((data) => {
      this.postagem = data;
    });
  }

  carregarComentarios(id: number): void {
    this.postagemService.listarComentarios(id).subscribe((comentarios) => {
      this.comentarios = comentarios;
    });
  }

  adicionarComentario(): void {
    if (!this.novoComentarioTitulo || !this.novoComentarioConteudo) {
      alert('Preencha todos os campos do comentário.');
      return;
    }

    this.postagemService.criarComentario(
      this.postagem.id,
      this.novoComentarioTitulo,
      this.novoComentarioConteudo
    ).subscribe(() => {
      this.carregarComentarios(this.postagem.id);
      this.novoComentarioTitulo = '';
      this.novoComentarioConteudo = '';
    });
  }

  voltar(): void {
    this.router.navigate(['/feed']);
  }

  getYoutubeEmbedUrl(link: string): SafeResourceUrl {
    const videoId = link.match(/(?:youtu\.be\/|youtube\.com\/(?:watch\?v=|embed\/|v\/|shorts\/|.+\?v=))([^&?\/\s]+)/);
    const embedUrl = videoId ? `https://www.youtube.com/embed/${videoId[1]}` : '';
    return this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
  }
}

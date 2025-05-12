import { Component, Input, inject } from '@angular/core';

import { PostagemService } from '../../../services/postagem.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css'],
  imports: [FormsModule,CommonModule]
})
export class PostCardComponent {
  @Input() post: any;
  newComment = { titulo: '', conteudo: '' };
  postagemService = inject(PostagemService);
  sanitizer: DomSanitizer = inject(DomSanitizer);
  criarComentario(postagemId: number) {
    const { titulo, conteudo } = this.newComment;
    if (!titulo || !conteudo) {
      alert('Preencha todos os campos do comentário!');
      return;
    }

    this.postagemService.criarComentario(postagemId, titulo, conteudo).subscribe({
      next: () => {
        alert('Comentário enviado com sucesso!');
        this.newComment = { titulo: '', conteudo: '' };
        this.postagemService.listarPostagens().subscribe(data => {
          const postAtualizado = data.find((p: any) => p.id === postagemId);
          if (postAtualizado) {
            this.post.comentarios = postAtualizado.comentarios;
          }
        });
      },
      error: (err) => {
        console.error('Erro ao enviar comentário:', err);
        alert('Erro ao enviar comentário!');
      }
    });
  }

   
  getYoutubeEmbedUrl(link: string): SafeResourceUrl {
    const videoId = link.match(/(?:youtu\.be\/|youtube\.com\/(?:watch\?v=|embed\/|v\/|shorts\/|.+\?v=))([^&?\/\s]+)/);
    const embedUrl = videoId ? `https://www.youtube.com/embed/${videoId[1]}` : '';
    return this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
  }
}

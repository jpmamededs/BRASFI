import { Component, inject } from '@angular/core';
import { PostagemService } from '../../services/postagem.service';
import { FormsModule } from '@angular/forms';
import { CommonModule, NgClass } from '@angular/common';

@Component({
  selector: 'app-postagem',
  imports: [FormsModule,CommonModule],
  templateUrl: './postagem.component.html',
  styleUrls: ['./postagem.component.css']
})
export class PostagemComponent {
  titulo = '';
  paragrafo = '';
  imagemOuVideo = '';
  link = '';
  fixado = false;
  tag = 'EVENTO';  
  isAdmin = false;
 
  private postagemService = inject(PostagemService);

  constructor() {
    this.isAdmin = this.postagemService.isAdmin();
  }

  criarPostagem() {
    if (!this.isAdmin) {
      alert('Apenas administradores podem criar postagens!');
      return;
    }
  

 
    const postagem = {
      titulo: this.titulo,
      paragrafo: this.paragrafo,
      imagemOuVideo: this.imagemOuVideo,
      link: this.link,
      fixado: this.fixado,
      tag: this.tag
    };

    this.postagemService.criarPostagem(postagem).subscribe({
      next: () => {
      
        alert('Postagem criada com sucesso!');
        window.location.reload();
      },
      error: (err) => {
       
        console.error('Erro ao criar postagem:', err);
        alert('Erro ao criar postagem! ❌');
      }
    });
  }
}
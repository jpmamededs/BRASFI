import { Component } from '@angular/core';
import { PostagemService } from '../../services/postagem.service';
import { CommonModule, NgClass } from '@angular/common';
import { OnInit, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { PostagemComponent } from '../postagem/postagem.component';
import { FormsModule, NgModel } from '@angular/forms';



@Component({
  selector: 'app-plataform',
  standalone: true,
  imports: [CommonModule, RouterModule, PostagemComponent,FormsModule],
  templateUrl: './plataform.component.html',
  styleUrls: ['./plataform.component.css']
})
export class PlataformComponent implements OnInit {
  posts: any[] = [];
  newComment: { [key: number]: { titulo: string, conteudo: string } } = {};
  postagemService = inject(PostagemService);
  router = inject(Router);

  ngOnInit() {
    this.postagemService.listarPostagens().subscribe(data => {
      this.posts = data;
      
      this.posts.forEach(post => {
        if (!this.newComment[post.id]) {
          this.newComment[post.id] = { titulo: '', conteudo: '' };
        }
      });
    });
  }


  logout() {
    this.postagemService.logout();
    this.router.navigate(['/login']);
  }

  goToEditProfile() {
    this.router.navigate(['/edit-profile']);
  }


  criarComentario(postagemId: number) {
    const { titulo, conteudo } = this.newComment[postagemId] || {};
    if (!titulo || !conteudo) {
      alert('Preencha todos os campos do comentário!');
      return;
    }

    this.postagemService.criarComentario(postagemId, titulo, conteudo).subscribe({
      next: () => {
        alert('Comentário enviado com sucesso!');
        
        this.newComment[postagemId] = { titulo: '', conteudo: '' };
        this.ngOnInit();  
      },
      error: (err) => {
        console.error('Erro ao enviar comentário:', err);
        alert('Erro ao enviar comentário!');
      }
    });
  }
}

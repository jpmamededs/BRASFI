import { Component } from '@angular/core';
import { PostagemService } from '../../services/postagem.service';
import { CommonModule, NgClass } from '@angular/common';
import { OnInit, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { PostagemComponent } from '../postagem/postagem.component';
import { FormsModule, NgModel } from '@angular/forms';
import { PostCardComponent } from "./post-card/post-card.component";
import { NavbarComponent } from "../landing-page/components/navbar/navbar.component";
import { FooterComponent } from "../landing-page/components/footer/footer.component";



@Component({
  selector: 'app-plataform',

  imports: [CommonModule, RouterModule, PostagemComponent, FormsModule, PostCardComponent, NavbarComponent, FooterComponent],
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



}

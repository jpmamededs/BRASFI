import { Component } from '@angular/core';
import { PostagemService } from '../../services/postagem.service';
import { CommonModule } from '@angular/common';
import { OnInit, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { PostagemComponent } from '../postagem/postagem.component';

@Component({
  selector: 'app-plataform',
  standalone: true,
  imports: [CommonModule, RouterModule, PostagemComponent],
  templateUrl: './plataform.component.html',
  styleUrls: ['./plataform.component.css']
})
export class PlataformComponent implements OnInit {
  posts: any[] = [];
  postagemService = inject(PostagemService);
  router = inject(Router);

  ngOnInit() {
    this.postagemService.listarPostagens().subscribe(data => {
      this.posts = data;
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

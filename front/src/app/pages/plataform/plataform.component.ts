import { Component } from '@angular/core';
import { PostagemService } from '../../services/postagem.service';
import { CommonModule } from '@angular/common';
import { OnInit,inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';


@Component({
  selector: 'app-plataform',
  imports: [CommonModule,RouterModule,],
  templateUrl: './plataform.component.html',
  styleUrl: './plataform.component.css'
})
export class PlataformComponent implements OnInit {
    posts: any[] = [];
    private postagemService = inject(PostagemService);
    private router = inject(Router);

    ngOnInit() {
      this.postagemService.listarPostagens().subscribe(data => {
        this.posts = data;
      });
    }

    logout() {
      this.postagemService.logout();
      this.router.navigate(['/login']);
    }
}
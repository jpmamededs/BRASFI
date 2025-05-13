import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostagemService } from '../../services/postagem.service';
import { NavbarComponent } from '../landing-page/components/navbar/navbar.component';

@Component({
  selector: 'app-listagem',
  standalone: true,
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css'],
  imports: [CommonModule,NavbarComponent]
})
export class ListagemComponent implements OnInit {
  postagemService = inject(PostagemService);
  route = inject(ActivatedRoute);
  router = inject(Router);

  categoria: string = '';
  postagens: any[] = [];

  ngOnInit(): void {
    // ✅ Obter categoria da rota
    this.categoria = this.route.snapshot.paramMap.get('categoria') || '';
    this.carregarPostagens();
  }

  carregarPostagens(): void {
    // ✅ Chamando o serviço com a categoria correta
    this.postagemService.listarPostagensPorCategoria(this.categoria).subscribe({
      next: (data) => {
        console.log(`Postagens da categoria ${this.categoria}:`, data);
        this.postagens = data;
      },
      error: (err) => {
        console.error('Erro ao carregar postagens:', err);
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/']);
  }
}

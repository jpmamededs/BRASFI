import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SafeUrlPipe } from '../../../pipes/safe-url.pipe';
import { FileSizePipe } from '../../../pipes/file-size.pipe';

@Component({
  selector: 'app-curso-detalhe',
  templateUrl: './curso-detalhe.component.html',
  styleUrls: ['./curso-detalhe.component.css'],
  imports:[CommonModule,SafeUrlPipe,FileSizePipe]
})
export class CursoDetalheComponent implements OnInit {
  curso: any;
  isLoading = true;
  errorMessage: string | null = null;

  

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    
    if (id) {
      this.carregarCurso(id);
    } else {
      this.handleError('ID do curso não encontrado na URL');
      this.router.navigate(['/pagina-de-erro']); 
    }
  }

  carregarCurso(id: string): void {
    const token = localStorage.getItem('authToken') || '';
    const headers = new HttpHeaders({
      'Authorization': token
    });

    this.http.get<any>(`http://localhost:8080/api/cursos/${id}`, { headers })
      .subscribe({
        next: (response) => {
          this.curso = response;
          this.isLoading = false;
        },
        error: (err) => {
          this.handleError('Erro ao carregar curso: ' + (err.error?.message || err.message));
        }
      });
  }

  private handleError(message: string): void {
    this.errorMessage = message;
    this.isLoading = false;
    console.error(message);
  }

  voltar(): void {
    this.router.navigate(['/centro-formacao']); 
  }
}
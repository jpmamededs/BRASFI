import { Component, Input, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post-view',
  templateUrl: './post-view.component.html',
  imports:[CommonModule]
})
export class PostViewComponent implements OnInit {
  @Input() curso?: CursoResponseDTO;
  cursos: CursoResponseDTO[] = [];
  loading = true;
  error: string | null = null;

  

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    if (this.curso) {
      this.cursos = [this.curso];
      this.loading = false;
    } else {
      this.loadCursos();
    }
  }

  getTotalAulas(curso: CursoResponseDTO): number {
  if (!curso.modulos) return 0;
  return curso.modulos.reduce((total, modulo) => total + (modulo.aulas?.length || 0), 0);
  }

  loadCursos(): void {
   
    const token = localStorage.getItem('authToken') || '';
    
    
    const headers = new HttpHeaders({
      'Authorization': token,
      'Content-Type': 'application/json'
    });

    this.http.get<CursoResponseDTO[]>('http://localhost:8080/api/cursos', { headers })
      .subscribe({
        next: (cursos) => {
          this.cursos = cursos;
          this.loading = false;
        },
        error: (err) => {
          console.error('Erro ao carregar cursos:', err);
          
         
          if (err.status === 401) {
            this.error = 'Acesso não autorizado. Faça login novamente.';
          } else {
            this.error = 'Erro ao carregar cursos. Tente novamente.';
          }
          
          this.loading = false;
          
          
          if (err.status === 401) {
           
          }
        }
      });
  }
}


interface CursoResponseDTO {
  id: number;
  titulo: string;
  duracao: string;
  areaConhecimento: string;
  autor: string;
  modulos: ModuloResponseDTO[];
}

interface ModuloResponseDTO {
  id: number;
  nome: string;  
  ordem: number;
  aulas: AulaResponseDTO[];
}

interface AulaResponseDTO {
  id: number;
  titulo: string;
  descricao: string;
  nivel: string;
  tema: string;
  autor: string;
  tipoConteudo: string;  
  conteudo: string;      
  ordem: number;
  materiais: MaterialResponseDTO[];
}

interface MaterialResponseDTO {
  id: number;
  nomeArquivo: string;
  tipo: string;
  downloadUrl: string;
}


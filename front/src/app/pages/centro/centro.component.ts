import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../landing-page/components/navbar/navbar.component";
import { FooterComponent } from "../landing-page/components/footer/footer.component";
import { CursoFormComponent } from './curso-form/curso-form.component';

@Component({
  selector: 'app-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.css'],
  imports: [
    CommonModule,
    NavbarComponent,
    FooterComponent,
  ]
})
export class CentroComponent implements OnInit {
  @ViewChild(CursoFormComponent) cursoFormComponent!: CursoFormComponent;

  cursos: any[] = [];
  cursoSelecionado: any = null;
  mostrarFormulario = true;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.carregarCursos();
  }

  carregarCursos(): void {
    const token = localStorage.getItem('authToken') || '';
    const headers = new HttpHeaders({
      'Authorization': token
    });

    this.http.get<any[]>('http://localhost:8080/api/cursos', { headers })
      .subscribe({
        next: (response) => {
          // Inicializando a propriedade expandido como false para cada curso
          this.cursos = response.map(curso => ({ ...curso, expandido: false }));
        },
        error: (err) => {
          console.error('Erro ao carregar cursos:', err);
          alert('Erro ao carregar cursos: ' + (err.error?.message || err.message));
        }
      });
  }

  onFormSubmit(formData: any): void {
    const token = localStorage.getItem('authToken') || '';
    const headers = new HttpHeaders({
      'Authorization': token,
      'Content-Type': 'application/json'
    });

    this.http.post<any>('http://localhost:8080/api/cursos', formData, { headers })
      .subscribe({
        next: (response) => {
          alert('Curso criado com sucesso!');
          this.cursoFormComponent.resetarFormulario();
          this.carregarCursos();
        },
        error: (err) => {
          console.error('Erro ao criar curso:', err);
          alert('Erro ao criar curso: ' + (err.error?.message || err.message));
        }
      });
  }

  getAreasConhecimento(): string[] {
    const areas = this.cursos.map(curso => curso.areaConhecimento);
    return [...new Set(areas)];
  }

  getCursosPorArea(area: string): any[] {
    return this.cursos.filter(curso => curso.areaConhecimento === area);
  }

  toggleCurso(curso: any): void {
    curso.expandido = !curso.expandido;
  }

  visualizarCurso(curso: any): void {
    this.cursoSelecionado = curso;
    this.mostrarFormulario = false;
  }

  voltarParaLista(): void {
    this.cursoSelecionado = null;
    this.mostrarFormulario = false;
  }

  mostrarCriarCurso(): void {
    this.cursoSelecionado = null;
    this.mostrarFormulario = true;
  }

  criarNovoCurso(): void {
    this.router.navigate(['/cursos/novo']);
  }
mostrarCursosDaArea(area: string): void {
    this.router.navigate(['/cursos', area]);
  }
}

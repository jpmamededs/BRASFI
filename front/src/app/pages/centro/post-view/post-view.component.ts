import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FooterComponent } from "../../landing-page/components/footer/footer.component";
import { NavbarComponent } from "../../landing-page/components/navbar/navbar.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.css'],
  imports: [FooterComponent, NavbarComponent,CommonModule,FormsModule]
})
export class PostViewComponent implements OnInit {
  cursos: any[] = [];
  area: string = '';
  searchQuery: string = '';

  router = inject(Router)

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.area = this.route.snapshot.paramMap.get('area') || '';
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
          this.cursos = response.filter(curso => curso.areaConhecimento === this.area);
        },
        error: (err) => {
          console.error('Erro ao carregar cursos:', err);
          alert('Erro ao carregar cursos: ' + (err.error?.message || err.message));
        }
      });
  }

  buscarCursos(): void {
    if (this.searchQuery) {
      this.cursos = this.cursos.filter(curso =>
        curso.titulo.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.carregarCursos();
    }
  }

  toggleFilter(filterType: string): void {
  
    console.log(`Filtro aplicado: ${filterType}`);
  }


  carregarCursoPorId(id: number): void {
    const token = localStorage.getItem('authToken') || '';
    const headers = new HttpHeaders({
        'Authorization': token
    });

    this.http.get<any>(`http://localhost:8080/api/cursos/${id}`, { headers })
        .subscribe({
            next: (response) => {
               
                console.log('Curso detalhado:', response);
               
                this.router.navigate(['/curso-detalhe', id]);
            },
            error: (err) => {
                console.error('Erro ao carregar curso:', err);
                alert('Erro ao carregar curso: ' + (err.error?.message || err.message));
            }
        });
}
}

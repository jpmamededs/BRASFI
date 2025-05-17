import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../../landing-page/components/navbar/navbar.component";
import { FooterComponent } from "../../landing-page/components/footer/footer.component";

@Component({
  selector: 'app-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.css'],
  imports: [CommonModule, NavbarComponent, FooterComponent]
})
export class PostViewComponent implements OnInit {
  cursos: any[] = [];
  area: string = '';

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
}

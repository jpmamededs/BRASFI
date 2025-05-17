import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostViewComponent } from './post-view/post-view.component';
import { CursoFormComponent } from './curso-form/curso-form.component';

@Component({
  selector: 'app-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.css'],
 
  imports: [ReactiveFormsModule, CommonModule,PostViewComponent,CursoFormComponent]
})
export class CentroComponent  {

  @ViewChild(CursoFormComponent) cursoFormComponent!: CursoFormComponent;

  cursos: any[] = []; 
  cursoSelecionado: any = null; 
  mostrarFormulario = true; 

  constructor(private http: HttpClient) {}

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
          this.cursos = response;
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
}
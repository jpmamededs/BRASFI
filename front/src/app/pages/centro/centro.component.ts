import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostViewComponent } from './post-view/post-view.component';

@Component({
  selector: 'app-centro',
  templateUrl: './centro.component.html',
  styleUrls: ['./centro.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,PostViewComponent]
})
export class CentroComponent implements OnInit {

  cursoCriado: any = null;
  cursoForm: FormGroup;
  niveisDificuldade = ['INICIANTE', 'INTERMEDIARIO', 'AVANCADO'];
  temasAula = ['GESTAO', 'CLIMA', 'REGULAMENTO', 'OUTRO'];
  areasConhecimento = ['GOVERNANCA', 'ADMINISTRACAO', 'TECNOLOGIA', 'OUTRA'];
  isSubmitting = false;
  mostrarVisualizacao = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.cursoForm = this.fb.group({
      areaConhecimento: ['GOVERNANCA', Validators.required],
      areaCustomizada: [''],
      titulo: ['', [Validators.required, Validators.maxLength(100)]],
      duracao: ['', [Validators.required, Validators.pattern(/^\d+\s*horas?$/)]],
      modulos: this.fb.array([this.criarModulo()])
    });
  }

  ngOnInit(): void {}

  get modulos(): FormArray {
    return this.cursoForm.get('modulos') as FormArray;
  }

  criarModulo(): FormGroup {
    return this.fb.group({
      nome: ['', Validators.required],
      ordem: [0, [Validators.required, Validators.min(0)]],
      aulas: this.fb.array([this.criarAula()])
    });
  }

  criarAula(): FormGroup {
    return this.fb.group({
      titulo: ['', Validators.required],
      descricao: [''],
      nivel: ['INICIANTE', Validators.required],
      tema: ['GESTAO', Validators.required],
      temaCustomizado: [''],
      autor: ['', Validators.required],
      conteudoTexto: [''],
      urlVideo: [''],
      isVideo: [false],
      ordem: [0, [Validators.required, Validators.min(0)]],
      materiais: this.fb.array([])
    });
  }

  criarMaterial(): FormGroup {
    return this.fb.group({
      nomeArquivo: ['', Validators.required],
      tipo: ['pdf', Validators.required],
      caminhoArquivo: [''],
      tamanho: [0, [Validators.required, Validators.min(0)]]
    });
  }

  aulasDoModulo(moduloIndex: number): FormArray {
    return this.modulos.at(moduloIndex).get('aulas') as FormArray;
  }

  materiaisDaAula(moduloIndex: number, aulaIndex: number): FormArray {
    return this.aulasDoModulo(moduloIndex).at(aulaIndex).get('materiais') as FormArray;
  }

  adicionarModulo(): void {
    this.modulos.push(this.criarModulo());
  }

  removerModulo(index: number): void {
    if (this.modulos.length > 1) {
      this.modulos.removeAt(index);
    }
  }

  adicionarAula(moduloIndex: number): void {
    this.aulasDoModulo(moduloIndex).push(this.criarAula());
  }

  removerAula(moduloIndex: number, aulaIndex: number): void {
    if (this.aulasDoModulo(moduloIndex).length > 1) {
      this.aulasDoModulo(moduloIndex).removeAt(aulaIndex);
    }
  }

  adicionarMaterial(moduloIndex: number, aulaIndex: number): void {
    this.materiaisDaAula(moduloIndex, aulaIndex).push(this.criarMaterial());
  }

  removerMaterial(moduloIndex: number, aulaIndex: number, materialIndex: number): void {
    this.materiaisDaAula(moduloIndex, aulaIndex).removeAt(materialIndex);
  }

  onSubmit(): void {
  if (this.cursoForm.invalid || this.isSubmitting) {
    this.cursoForm.markAllAsTouched();
    return;
  }

  this.isSubmitting = true;
  const formValue = this.prepareFormData();

  // Obter headers de autenticação
  const token = localStorage.getItem('authToken') || '';
  const headers = new HttpHeaders({
    'Authorization': token,
    'Content-Type': 'application/json'
  });

  this.http.post<any>('http://localhost:8080/api/cursos', formValue, { headers })
    .subscribe({
      next: (response) => {
        this.isSubmitting = false;
        this.cursoCriado = response;
        this.mostrarVisualizacao = true;
      },
      error: (err) => {
        console.error('Erro ao criar curso:', err);
        alert('Erro ao criar curso: ' + (err.error?.message || err.message));
        this.isSubmitting = false;
      }
    });
  }

  private prepareFormData(): any {
    const formValue = JSON.parse(JSON.stringify(this.cursoForm.value));
    
    // Processa temas customizados
    formValue.modulos.forEach((modulo: any) => {
      modulo.aulas.forEach((aula: any) => {
        if (aula.tema !== 'OUTRO') {
          aula.temaCustomizado = null;
        }
        // Converte isVideo para boolean (caso venha como string)
        aula.isVideo = Boolean(aula.isVideo);
      });
    });

    return formValue;
  }

  showCustomThemeField(moduloIndex: number, aulaIndex: number): boolean {
    return this.aulasDoModulo(moduloIndex).at(aulaIndex).get('tema')?.value === 'OUTRO';
  }

  showCustomAreaField(): boolean {
    return this.cursoForm.get('areaConhecimento')?.value === 'OUTRA';
  }
}
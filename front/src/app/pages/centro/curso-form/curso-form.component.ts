import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-curso-form',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './curso-form.component.html',
  styleUrl: './curso-form.component.css'
})
export class CursoFormComponent {

   @Output() formSubmit = new EventEmitter<any>();
  
  cursoForm: FormGroup;
  niveisDificuldade = ['INICIANTE', 'INTERMEDIARIO', 'AVANCADO'];
  temasAula = ['GESTAO', 'CLIMA', 'REGULAMENTO', 'OUTRO'];
  areasConhecimento = ['GOVERNANCA', 'ADMINISTRACAO', 'TECNOLOGIA', 'OUTRA'];
  isSubmitting = false;

  constructor(private fb: FormBuilder) {
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
    this.formSubmit.emit(formValue);
    this.isSubmitting = false;
  }

  







  private prepareFormData(): any {
    const formValue = JSON.parse(JSON.stringify(this.cursoForm.value));
    
    
    formValue.modulos.forEach((modulo: any) => {
      modulo.aulas.forEach((aula: any) => {
        if (aula.tema !== 'OUTRO') {
          aula.temaCustomizado = null;
        }
       
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

   resetarFormulario(): void {
   
    this.cursoForm.reset({
      areaConhecimento: 'GOVERNANCA',
      areaCustomizada: '',
      titulo: '',
      duracao: '',
      modulos: this.fb.array([this.criarModulo()])
    });
    
   
  }
  


}

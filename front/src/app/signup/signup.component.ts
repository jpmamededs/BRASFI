import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule]
})
export class SignupComponent {
  signupForm!: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Inicializando o formulário reativo com validações
    this.signupForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      role: ['USER', Validators.required],
      genero: ['MASCULINO', Validators.required]
    });
  }

  register() {
    // Verificando se todos os campos estão válidos
    if (this.signupForm.invalid) {
      this.errorMessage = 'Preencha todos os campos corretamente.';
      console.log('Formulário inválido:', this.signupForm.value);
      return;
    }

    const user = this.signupForm.value;

    // Verificação de senha
    if (user.password !== user.confirmPassword) {
      this.errorMessage = 'As senhas não coincidem.';
      return;
    }

    // Requisição para o backend
    this.http.post('http://localhost:8080/req/signup', user).subscribe(
      () => {
        alert('Cadastro realizado com sucesso!');
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Erro ao cadastrar:', error);
        this.errorMessage = 'Erro ao cadastrar. Tente novamente.';
      }
    );
  }
}

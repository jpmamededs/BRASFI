import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { PostagemService } from '../services/postagem.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';
  private postagemService = inject(PostagemService);
  private router = inject(Router);

  login() {
    this.postagemService.login(this.username, this.password).subscribe({
      next: (response) => {
        this.postagemService.saveToken(this.username, this.password);
      
        const role = localStorage.getItem('userRole');
        console.log('Usuário logado com role:', role);
        this.router.navigate(['/plataforma']);
      },
      error: (err) => {
        if (err.status === 401) {
          this.errorMessage = 'Usuário ou senha inválidos!';
        } else if (err.status === 500) {
          this.errorMessage = 'Erro no servidor. Tente novamente.';
        } else {
          this.errorMessage = `Erro inesperado: ${err.message}`;
        }
      }
    });
  }
}
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { PostagemService } from '../services/postagem.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';
  imgMulher:string="assets/images/mulher.svg";
  imgLogo:string="assets/images/brasfi-logo.svg"
  private postagemService = inject(PostagemService);
  private router = inject(Router);

  login() {
    if (!this.username || !this.password) {
      this.errorMessage = 'Preencha todos os campos!';
      return;
    }

    this.postagemService.login(this.username, this.password).subscribe({
      next: () => {
        alert('Login realizado com sucesso!');
        this.router.navigate(['/feed']);
      },
      error: (err) => {
        if (err.status === 401) {
          this.errorMessage = 'Usuário ou senha inválidos!';
        } else {
          this.errorMessage = `Erro inesperado: ${err.message}`;
        }
      }
       });
  }
  goBack(): void {
    this.router.navigate(['/']);
  }
}

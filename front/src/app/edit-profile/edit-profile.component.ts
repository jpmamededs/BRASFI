import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../pages/landing-page/components/navbar/navbar.component";
import { FooterComponent } from "../pages/landing-page/components/footer/footer.component";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, NavbarComponent, FooterComponent]
})
export class EditProfileComponent {
  editForm!: FormGroup;
  userProfile: any = {};
  isEditing: { [key: string]: boolean } = {};
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.checkIfLoggedIn();
    this.editForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      midleName: [''],
      biografia: [''],
      localizacao: [''],
      idade: [null],
      photo: [''],
      linkInstagram: [''],
      linkLinkedin: [''],
      linkLattes: [''],
      linkWhatsapp: [''],
      genero: ['MASCULINO'],
      role: ['USER']
    });

    this.loadUserProfile();
  }

  checkIfLoggedIn() {
    const token = localStorage.getItem('authToken');
    if (!token) {
      alert('Você precisa estar logado para acessar esta página.');
      this.router.navigate(['/login']);
    }
  }

  loadUserProfile() {
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.errorMessage = 'Usuário não autenticado. Faça login novamente.';
      this.router.navigate(['/login']);
      return;
    }

    this.http.get<any>('http://localhost:8080/usuarios/me', {
      headers: { 'Authorization': token }
    }).subscribe({
      next: (user) => {
        this.userProfile = user;
        this.editForm.patchValue(user);
        console.log('Perfil carregado:', user);
      },
      error: (error) => {
        console.error('Erro ao carregar perfil:', error);
        if (error.status === 401) {
          this.errorMessage = 'Sessão expirada. Faça login novamente.';
          localStorage.removeItem('authToken');
          this.router.navigate(['/login']);
        } else if (error.status === 403) {
          this.errorMessage = 'Acesso negado. Faça login novamente.';
          localStorage.removeItem('authToken');
          this.router.navigate(['/login']);
        } else {
          this.errorMessage = 'Erro ao carregar perfil. Tente novamente.';
        }
      }
    });
  }




  toggleEdit(field: string) {

    this.isEditing[field] = !this.isEditing[field];
  }

  saveProfile() {
    if (this.editForm.invalid) {
      this.errorMessage = 'Preencha todos os campos corretamente.';
      return;
    }

    const user = this.editForm.value;
    const token = localStorage.getItem('authToken') ?? '';
    const userId = this.userProfile.id;

    
    if (!user.password) {
      delete user.password;
    }

    this.http.put(`http://localhost:8080/usuarios/${userId}`, user, {
      headers: { 'Authorization': `${token}` }
    }).subscribe({
      next: (updatedUser: any) => {
        this.successMessage = 'Perfil atualizado com sucesso!';
        
       
        localStorage.setItem('userName', updatedUser.username);
        localStorage.setItem('userImage', updatedUser.photo || 'assets/default-profile.png');
        
       
        if (this.userProfile.username !== updatedUser.username) {
          const password = atob(token.split(' ')[1]).split(':')[1];
          const newToken = `Basic ${btoa(`${updatedUser.username}:${password}`)}`;
          localStorage.setItem('authToken', newToken);
        }

       
        this.userProfile = updatedUser;
        this.editForm.patchValue(updatedUser);
        
        
        setTimeout(() => this.router.navigate(['/plataforma']), 2000);
      },
      error: (error) => {
        console.error('Erro ao atualizar perfil:', error);
       
      }
    });
  }


}

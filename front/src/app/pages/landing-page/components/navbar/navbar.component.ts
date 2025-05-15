import { Component, inject, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostagemService } from '../../../../services/postagem.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  postagemService = inject(PostagemService);
  router = inject(Router);
  isLoggedIn = signal(false);
  imgSource:string="assets/images/Group.png";
  menuOpen = false;
  userName = 'Usuário';
  userImage = 'assets/images/default-profile.png';

  constructor() {
    this.checkLoginStatus();
  }

  checkLoginStatus(): void {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    if (user && user.username) {
      this.userName = user.username;
      this.userImage = user.photo || 'assets/images/default-profile.png';
      this.isLoggedIn.set(true);
    } else {
      this.isLoggedIn.set(false);
    }
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }
  ngOnInit(): void {
    this.loadUserData();
  }
loadUserData(): void {
    const storedName = localStorage.getItem('userName');
    const storedImage = localStorage.getItem('userImage');

    if (storedName && storedImage) {
      this.userName = storedName;
      this.userImage = storedImage;
      this.isLoggedIn.set(true);
    } else {
      this.isLoggedIn.set(false);
    }
  }
   logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userName');
    localStorage.removeItem('userImage');
    localStorage.removeItem('userRole');
    this.isLoggedIn.set(false);
    this.router.navigate(['/login']);
  }

  goToEditProfile(): void {
    this.router.navigate(['/edit-profile']);
  }
}

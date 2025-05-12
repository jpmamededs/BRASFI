import { Component } from '@angular/core';
import { NavbarComponent } from "../landing-page/components/navbar/navbar.component";
import { PostagemService } from '../../services/postagem.service';

import { OnInit, inject } from '@angular/core';

import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { EventCardComponent } from "../../components/event-card/event-card.component";


@Component({
  selector: 'app-feed',
  imports: [NavbarComponent, CommonModule, EventCardComponent],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.css'
})
export class FeedComponent {

  postagemService = inject(PostagemService);
  router = inject(Router);
  eventos: any[] = [];
  eventos_noticias:any[]= [];
  eventos_novidades:any[]=[];
  sanitizer: DomSanitizer = inject(DomSanitizer);


  ngOnInit() {
    this.carregarEventos();
  }

  carregarEventos() {
    this.postagemService.listarPostagens().subscribe({
      next: (data) => {
        console.log('Todas as postagens:', data);
        
       
        this.eventos = data
          .filter((post: any) => post.tag?.toUpperCase() === 'EVENTO')
          .map((post: any) => ({
            ...post,
            imagemOuVideo: this.getSafeUrl(post.imagemOuVideo) 
          }));

        this.eventos_novidades = data
          .filter((post: any) => post.tag?.toUpperCase() === 'NOVIDADES')
          .map((post: any) => ({
            ...post,
            imagemOuVideo: this.getSafeUrl(post.imagemOuVideo) 
          }));

        this.eventos_noticias = data
          .filter((post: any) => post.tag?.toUpperCase() === 'NOTICIAS')
          .map((post: any) => ({
            ...post,
            imagemOuVideo: this.getSafeUrl(post.imagemOuVideo) 
          }));

      
      },
      error: (err) => {
        console.error('Erro ao carregar eventos:', err);
      }
    });
  }

  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
          
  

  logout() {
    this.postagemService.logout();
    this.router.navigate(['/login']);
  }


}

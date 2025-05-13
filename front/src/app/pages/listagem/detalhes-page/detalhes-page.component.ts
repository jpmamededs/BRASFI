import { Component } from '@angular/core';
import { NavbarComponent } from "../../landing-page/components/navbar/navbar.component";
import { inject,OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostagemService } from '../../../services/postagem.service';
import { SafeUrlPipe } from '../../../pipes/safe-url.pipe';

@Component({
  selector: 'app-detalhes-page',
  imports: [NavbarComponent,CommonModule,SafeUrlPipe],
  templateUrl: './detalhes-page.component.html',
  styleUrl: './detalhes-page.component.css',
  
})
export class DetalhesPageComponent {

  router= inject(Router);
  route = inject(ActivatedRoute);

  categoria: string = '';


  postagem: any;

  postagemService = inject(PostagemService);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.carregarPostagem(id);
  }

  carregarPostagem(id: number): void {
    this.postagemService.buscarPostagemPorId(id).subscribe((data) => {
      this.postagem = data;
    });
  }




  voltar(): void {
    this.router.navigate(['/feed']);
  }

  

 


}

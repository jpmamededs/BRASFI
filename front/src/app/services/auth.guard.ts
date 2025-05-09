import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { PostagemService } from './postagem.service';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);
  const postagemService = inject(PostagemService);

 
  const token = localStorage.getItem('authToken');
  if (!token) {
    router.navigate(['/login']);
    return false;
  }

 
  return postagemService.checkAuth().pipe(
    map(() => true),  
    catchError(() => {
      router.navigate(['/login']);  
      return of(false);
    })
  );
};
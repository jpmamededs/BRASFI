import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostagemService {
  private apiUrl = 'http://localhost:8080';
  private http = inject(HttpClient);


  criarPostagem(postagem: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/postagens`, postagem, { headers });
  }


  login(username: string, password: string): Observable<any> {
    const credentials = btoa(`${username}:${password}`);
    const headers = new HttpHeaders({
      'Authorization': `Basic ${credentials}`,
      'Content-Type': 'application/json'
    });

    return this.http.get(`${this.apiUrl}/auth/check`, { headers }).pipe(
      tap((response: any) => {
        this.saveToken(username, password);

        localStorage.setItem('userName', response.username);
        localStorage.setItem('userImage', response.photo || 'assets/images/default-profile.png');
        localStorage.setItem('userRole', response.role || 'USER');
      })
    );
  }

  saveToken(username: string, password: string): void {
    const credentials = btoa(`${username}:${password}`);
    localStorage.setItem('authToken', `Basic ${credentials}`);
  }


  getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken') || '';
    return new HttpHeaders({
      'Authorization': token,
      'Content-Type': 'application/json'
    });


  }


  listarPostagens(): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}/postagens`, { headers: this.getAuthHeaders() });
  }


  checkAuth(): Observable<any> {
    return this.http.get(`${this.apiUrl}/auth/check`, { headers: this.getAuthHeaders() });
  }


  logout(): void {
    localStorage.removeItem('authToken');
  }

  isAdmin(): boolean {
    const role = localStorage.getItem('userRole');
    return role === 'ADMIN';
  }

  criarComentario(postagemId: number, titulo: string, conteudo: string): Observable<any> {
    const token = localStorage.getItem('authToken') ?? '';
    const comentario = {
      titulo,
      conteudo,
      dataCriacao: new Date().toISOString(),
      postagemId
    };

    return this.http.post(`${this.apiUrl}/comentarios`, comentario, {
      headers: { 'Authorization': token, 'Content-Type': 'application/json' }
    });
  }

  getYoutubeEmbedUrl(link: string): string {
    const videoId = link.match(/(?:youtu\.be\/|youtube\.com\/(?:watch\?v=|embed\/|v\/|shorts\/|.+\?v=))([^&?\/\s]+)/);
    return videoId ? `https://www.youtube.com/embed/${videoId[1]}` : '';
  }

  listarPostagensPorCategoria(categoria: string): Observable<any[]> {
    const headers = this.getAuthHeaders();
    // Ajuste para usar o endpoint correto
    return this.http.get<any[]>(`${this.apiUrl}/postagens/tag/${categoria}`, { headers });
  }

  buscarPostagemPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/postagens/${id}`, { headers: this.getAuthHeaders() });
  }

}

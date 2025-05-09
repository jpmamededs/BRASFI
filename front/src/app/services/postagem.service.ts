import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostagemService {
  private apiUrl = 'http://localhost:8080';
  private http = inject(HttpClient);

  login(username: string, password: string): Observable<any> {
    
    const credentials = btoa(`${username}:${password}`);
    const headers = new HttpHeaders({
      'Authorization': `Basic ${credentials}`,
      'Content-Type': 'application/json'
    });

    
    

    return this.http.get(`${this.apiUrl}/auth/check`, { headers });
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
}

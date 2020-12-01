import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Curso } from '../model/curso';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  private cursosUrl: string;

  constructor(private http: HttpClient) {
    this.cursosUrl = 'http://localhost:8080/api/curso';
  }

  findAll(assunto: string, pageNumber: number, pageSize: number): Observable<any> {
    let params = new HttpParams();
    params = params.append('assunto', assunto);
    params = params.append('page', pageNumber.toString());
    params = params.append('size', pageSize.toString());

    return this.http.get<any>(this.cursosUrl, { params: params });
  }

  findById(codigo: number): Observable<Curso> {
    return this.http.get<Curso>(`${this.cursosUrl}/${codigo}`);
  }

  save(curso: Curso): Observable<Curso> {
    return this.http.post<Curso>(this.cursosUrl, curso);
  }

  update(codigo: number, curso: Curso): Observable<Curso> {
    return this.http.put<Curso>(`${this.cursosUrl}/${codigo}`, curso);
  }

  delete(codigo: number): Observable<any> {
    return this.http.delete(`${this.cursosUrl}/${codigo}`);
  }
}

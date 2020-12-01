import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Categoria } from '../model/categoria';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private categoriasUrl: string;

  constructor(private http: HttpClient) {
    this.categoriasUrl = 'http://localhost:8080/api/categoria';
  }

  findAllCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.categoriasUrl}/all`);
  }

  findAll(descricao: string, pageNumber: number, pageSize: number): Observable<any> {
    let params = new HttpParams();
    params = params.append('descricao', descricao);
    params = params.append('page', pageNumber.toString());
    params = params.append('size', pageSize.toString());

    return this.http.get<any>(this.categoriasUrl, { params: params });
  }

  findById(codigo: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.categoriasUrl}/${codigo}`);
  }

  save(categoria: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.categoriasUrl, categoria);
  }

  update(codigo: number, categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.categoriasUrl}/${codigo}`, categoria);
  }

  delete(codigo: number): Observable<any> {
    return this.http.delete(`${this.categoriasUrl}/${codigo}`);
  }
}

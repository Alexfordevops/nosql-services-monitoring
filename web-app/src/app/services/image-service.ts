import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UploadResponse} from '../interfaces/UploadResponseInterface';
import {FileInfo} from '../interfaces/FileInfoInterface';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  // URL base da API
  private baseUrl = 'http:/192.168.56.14:30080/image';

  constructor(private http: HttpClient) {}

  //upload de imagem
  upload(file: File): Observable<UploadResponse>{
    const formData: FormData = new FormData();
    formData.append('file', file);

    return this.http.post<UploadResponse>(`${this.baseUrl}/uploadImage`, formData);
  }

  //Download por id
  download(id: string):Observable<Blob>{
    return this.http.get(`${this.baseUrl}/downloadImage/${id}`, {responseType: 'blob'})
  }

  //Listar os metadados de todas as imagens
  listAll(): Observable<FileInfo[]>{
    return this.http.get<FileInfo[]>(`${this.baseUrl}/listImages`)
  }
}

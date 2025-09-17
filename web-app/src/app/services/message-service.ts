import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MessageInterface} from '../interfaces/MessageInterface';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://192.168.56.14:30080/message';

  constructor(private http: HttpClient) {}

  public listAll(): Observable<MessageInterface[]>{
    return this.http.get<MessageInterface[]>(`${this.baseUrl}/getUsersAndMessages`);
  }
}

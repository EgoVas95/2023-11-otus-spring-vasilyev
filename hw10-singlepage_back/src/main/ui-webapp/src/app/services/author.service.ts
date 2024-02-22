import { Injectable } from '@angular/core';
import {HttpBackend, HttpClient} from "@angular/common/http";
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Author } from '../model/author';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private endPoint: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  get contextPath(): string {
    return this.endPoint.concat('authors');
  }

  public getAuthorList(): Observable<Author[]> {
    return this.http.get<Author[]>(this.contextPath);
  }
}

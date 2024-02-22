import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../model/book';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private endPoint: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  get contextPath(): string {
    return this.endPoint.concat('books');
  }

  getApiWithId(id: number): string {
    let idStr = (id === null || id === undefined) ? '' : ("" + id);
    let url = this.endPoint.concat('books/', idStr);
    return url;
  }

  public getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.contextPath);
  }

  public getBook(id: number): Observable<Book> {
   return this.http.get<Book>(this.getApiWithId(id));
  }

  public saveBook(book: Book): Observable<Book> {
    if(book.id !== undefined && book.id !== null) {
      return this.http.patch<Book>(this.getApiWithId(book.id), book.toUpdateCreateJson);
    } else {
      return this.http.post<Book>(this.contextPath, book.toUpdateCreateJson);
    }
  }

  public deleteBook(id: number) {
   return this.http.delete(this.getApiWithId(id));
  }
}

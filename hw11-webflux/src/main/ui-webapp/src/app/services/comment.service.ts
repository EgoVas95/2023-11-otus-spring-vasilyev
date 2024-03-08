import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable, catchError, throwError } from 'rxjs';
import { Comment } from '../model/comment';
import { CommentData } from '../model/comment-data';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private commentsApiPath: string = 'comments';
  private booksApiPath: string = 'books';

  private endPoint: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  get contextPath(): string {
    return this.endPoint.concat(this.commentsApiPath);
  }

  getApiWithId(id: string): string {
    let idStr = (id === null || id === undefined) ? '' : ("" + id);
    return this.endPoint.concat(this.commentsApiPath, '/', idStr);
  }

  private getBookApiWithId(bookId: string): string {
    return this.endPoint.concat(this.booksApiPath, '/',
      ("" + bookId), '/', this.commentsApiPath);
  }

  public getComment(id: string): Observable<Comment> {
    return this.http.get<Comment>(this.getApiWithId(id)).pipe(
      catchError(this.handleError)
    );
  }

  public getCommentsForBook(id: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.getBookApiWithId(id)).pipe(
      catchError(this.handleError)
    );
  }

  public updateComment(id: string, comment: CommentData): Observable<Comment> {
    return this.http.patch<CommentData>(this.getApiWithId(id), comment).pipe(
      catchError(this.handleError)
    );
  }

  public createComment(comment: CommentData): Observable<Comment> {
    return this.http.post<CommentData>(this.endPoint.concat(this.commentsApiPath), comment).pipe(
      catchError(this.handleError)
    );
  }

  public deleteComment(id: string) {
    return this.http.delete(this.getApiWithId(id)).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMsg = error.headers.get('errorMsgs')?.toString();
    errorMsg = errorMsg === undefined ? '' : errorMsg;
    console.log(errorMsg);
    return throwError(() => new Error(errorMsg));
  }
}

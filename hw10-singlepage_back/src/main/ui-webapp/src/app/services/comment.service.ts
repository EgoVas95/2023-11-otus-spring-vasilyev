import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Comment } from '../model/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentServiceService {
  private commentsApiPath: string = 'comments';
  private booksApiPath: string = 'books';

  private endPoint: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  get contextPath(): string {
    return this.endPoint.concat(this.commentsApiPath);
  }

  getApiWithId(id: number): string {
    let idStr = (id === null || id === undefined) ? '' : ("" + id);
    return this.endPoint.concat(this.commentsApiPath, '/', idStr);
  }

   private getBookApiWithId(bookId: number): string {
    return this.endPoint.concat(this.booksApiPath, '/',
      ("" + bookId), '/', this.commentsApiPath);
   }

   public getCommentsForBook(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.getBookApiWithId(id));
   }

   public getCommentById(id: number): Observable<Comment> {
    return this.http.get<Comment>(this.getApiWithId(id));
   }

   public updateComment(id: number, comment: Comment): Observable<Comment> {
    return this.http.patch<Comment>(this.getApiWithId(id),
      comment.toUpdateCreateComment());
   }

   public crateComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(this.endPoint.concat(this.commentsApiPath),
      comment.toUpdateCreateComment);
   }


   public deleteComment(id: number) {
    return this.http.delete(this.getApiWithId(id));
   }
}

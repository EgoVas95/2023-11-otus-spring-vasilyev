import {Book} from "./book";

export class Comment {
  id?: number;
  text?: string;
  book?: Book;

  constructor(id?: number,
    text?: string,
    book?: Book) {
      this.id = id;
      this.text = text;
      this.book = book;
    }

  toUpdateCreateComment() {
    return {
      "id": this.id,
      "text": this.text,
      "bookId": this.book === undefined ? null : this.book.id
    }
  }
}

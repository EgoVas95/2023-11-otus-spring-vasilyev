import {Book} from "./book";

export class Comment {
  id?: string;
  text?: string;
  book?: Book;

  constructor(id?: string,
              text?: string,
              book?: Book) {
    this.id = id;
    this.text = text;
    this.book = book;
  }
}

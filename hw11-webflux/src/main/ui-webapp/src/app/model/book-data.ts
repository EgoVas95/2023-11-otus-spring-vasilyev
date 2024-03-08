import { Book } from "./book";

export class BookData {
  id?: string | undefined;
  title?: string | null;
  authorId?: string | undefined;
  genreId?: string | undefined;

  constructor(book: Book) {
    this.id = book.id;
    this.title = book.title;
    this.authorId = book.author?.id;
    this.genreId = book.genre?.id;
  }
}

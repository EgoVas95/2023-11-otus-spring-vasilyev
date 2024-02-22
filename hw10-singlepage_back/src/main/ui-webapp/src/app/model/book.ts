import {Author} from "./author";
import {Genre} from "./genre";

export class Book {
  id?: number | undefined;
  title?: string | null;
  author?: Author | null | undefined;
  genre?: Genre | null | undefined;

  constructor() {}

  toUpdateCreateJson() {
    return {
      "id": this.id,
      "title": this.title,
      "authorId": (this.author === null || this.author === undefined) ? null : this.author.id,
      "genreId": (this.genre === null || this.genre === undefined) ? null : this.genre.id
    }
  }
}

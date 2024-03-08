import {Author} from "./author";
import {Genre} from "./genre";

export class Book {
  id?: string | undefined;
  title?: string | null;
  author?: Author | null | undefined;
  genre?: Genre | null | undefined;

  constructor() {}
}

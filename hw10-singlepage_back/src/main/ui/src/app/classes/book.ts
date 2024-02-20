import {Author} from "./author";
import {Genre} from "./genre";

export class Book {
  id: bigint;
  title: string;
  author: Author;
  genre: Genre;
}

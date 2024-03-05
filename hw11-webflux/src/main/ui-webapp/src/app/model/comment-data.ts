import { Comment } from "./comment";

export class CommentData {
    id?: string;
    text?: string;
    bookId?: string;

    constructor(comment: Comment) {
      this.id = comment.id;
      this.text = comment.text;
      this.bookId = comment.book?.id;
    }
  }

import { Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book.service';
import { Book } from '../../model/book';
import { MatTableModule } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [MatTableModule, MatButton],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit {

  books: Book[] = [];
  displayedColumns: string[] = ['id', 'title', 'author', 'genre', 'goToEdit'];

  constructor(private bookService: BookService,
    private router:Router) {}

  ngOnInit(): void {
    this.bookService.getAllBooks().subscribe(data => {
      this.books = data;
    });
  }

  public goToCreateBook() {
    this.router.navigate(['book-edit']);
  }

  public goToEditBook(book: Book) {
    if (typeof book === undefined || book.id === undefined) {
      return;
    }
    this.router.navigate(['book-edit', book.id]);
  }
}

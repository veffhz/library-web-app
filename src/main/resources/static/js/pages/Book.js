import AuthorTable from '../components/authors/AuthorTable.js'
import GenreTable from '../components/genres/GenreTable.js'
import BookTable from '../components/books/BookTable.js'
import CommentTable from '../components/comments/CommentTable.js'

export default {
  name: 'Info',
  components: {
        AuthorTable,
        GenreTable,
        BookTable,
        CommentTable
  },
  data() {
      return {
        authors: frontendData.authors,
        genres: frontendData.genres,
        books: frontendData.books,
        comments: frontendData.comments
      }
  },
  //template: '<author-table :authors="authors" />',
  //template: '<genre-table :genres="genres" />',
  //template: '<book-table :books="books" :authors="authors" :genres="genres" />',
  template:

  '<comment-table :comments="comments" :books="books" />'
};
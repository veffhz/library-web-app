import AuthorTable from '../components/authors/AuthorTable.js'
import GenreTable from '../components/genres/GenreTable.js'
import BookTable from '../components/books/BookTable.js'

export default {
  name: 'App',
  components: {
        AuthorTable,
        GenreTable,
        BookTable
  },
  data() {
      return {
        authors: frontendData.authors,
        genres: frontendData.genres,
        books: frontendData.books,
      }
  },
  //template: '<author-table :authors="authors" />',
  //template: '<genre-table :genres="genres" />',
  template: '<book-table :books="books" :authors="authors" :genres="genres" />',
};
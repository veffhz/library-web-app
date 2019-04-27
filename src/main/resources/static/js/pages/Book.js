import BookTable from '../components/books/BookTable.js'

export default {
  name: 'Book',
  components: {
        BookTable
  },
  data() {
      return {
        authors: frontendData.authors,
        genres: frontendData.genres,
        books: frontendData.books
      }
  },
  template: '<book-table :books="books" :authors="authors" :genres="genres" />'
};
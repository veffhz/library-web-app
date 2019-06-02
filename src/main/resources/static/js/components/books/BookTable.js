import BookTr from './BookTr.js'
import BookForm from './BookForm.js'
import { showAlert } from '../Utils.js'

export default {
    name: 'BookTable',
    props: ['books', 'authors', 'genres'],
    components: {
                BookTr,
                BookForm
            },
    data: function() {
            return {
                book: null
            }
    },
    template: `
        <div>
            <h1>Books:</h1>
            <table class="items">
                <thead>
                <tr>
                    <th>id</th>
                    <th>bookName</th>
                    <th>publishDate</th>
                    <th>language</th>
                    <th>publishingHouse</th>
                    <th>city</th>
                    <th>isbn</th>
                    <th>author</th>
                    <th>genre</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <book-tr v-for="book in books" :key="book.id" :book="book"
                    :editBook="editBook" :deleteBook="deleteBook" :books="books" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <book-form :books="books" :bookAttr="book" :authors="authors" :genres="genres" />
            <div class="gap-30"></div>
            <div class="alert alert-danger" id="bookError" style="display:none;">
              <strong>Error!</strong> Access Denied! You not have permission to delete book!
            </div>
            <div class="alert alert-success" id="bookSuccess" style="display:none;">
              <strong>Success!</strong> Book deleted successfully.
            </div>
        </div>
    `,
      methods: {
          editBook(book) {
              this.book = book;
          },
          deleteBook(book) {
              this.$resource('/api/book{/id}').remove({id: book.id}).then(result => {
                  if (result.ok) {
                      this.books.splice(this.books.indexOf(book), 1);
                      showAlert('#bookSuccess');
                  }
              }, error => {
                  showAlert('#bookError');
              })
          }
      }
};
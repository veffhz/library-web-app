import BookTr from './BookTr.js'
import BookForm from './BookForm.js'

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
                    <th>Id</th>
                    <th>Book Name</th>
                    <th>Publish Date:</th>
                    <th>Language:</th>
                    <th>Publishing House:</th>
                    <th>City:</th>
                    <th>Isbn:</th>
                    <th>Author:</th>
                    <th>Genre:</th>
                    <th>Controls</th>
                </tr>
                </thead>
                <tbody>
                <book-tr v-for="book in books" :key="book.id" :book="book"
                    :editMethod="editMethod" :books="books" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <book-form :books="books" :bookAttr="book" :authors="authors" :genres="genres" />
        </div>
    `,
      methods: {
          editMethod: function(book) {
              this.book = book;
          }
      }
};
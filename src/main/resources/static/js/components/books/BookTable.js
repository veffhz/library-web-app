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
                    :editMethod="editMethod" :deleteMethod="deleteMethod" :books="books" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <book-form :books="books" :bookAttr="book" :authors="authors" :genres="genres" />
        </div>
    `,
      methods: {
          editMethod(book) {
              this.book = book;
          },
          deleteMethod(book) {
              this.$resource('/api/book{/id}').remove({id: book.id}).then(result => {
                if (result.ok) {
                  this.books.splice(this.books.indexOf(book), 1)
                }
              })
          }
      }
};
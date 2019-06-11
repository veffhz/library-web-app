import BookTr from './BookTr.js'
import BookForm from './BookForm.js'

export default {
    name: 'BookTable',
    computed: Vuex.mapState('bookModule', ['books']),
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
                <book-tr v-for="book in books" :key="book.id"
                :book="book" :editBook="editBook" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <book-form :bookAttr="book" />
            <div class="gap-30"></div>
            <div class="alert alert-danger" id="bookError" style="display:none;">
              <strong>Error!</strong> Access Denied! You not have permission to <span id="bookToAction"></span> book!
            </div>
            <div class="alert alert-success" id="bookSuccess" style="display:none;">
              <strong>Success!</strong> Book <span id="bookAction"></span> successfully.
            </div>
        </div>
    `,
      methods: {
          editBook(book) {
              this.book = book;
          }
      }
};
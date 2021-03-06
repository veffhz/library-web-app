import { showAlert } from '../Utils.js'

export default {
  name: 'BookForm',
  computed: {
      ...Vuex.mapState('authorModule', ['authors']),
      ...Vuex.mapState('genreModule', ['genres'])
  },
  props: ['bookAttr'],
  data: function() {
      return {
          bookName: '',
          publishDate: '',
          language: '',
          publishingHouse: '',
          city: '',
          isbn: '',
          author: '',
          genre: '',
          id: null
      }
  },
  watch: {
      bookAttr: function(newVal, oldVal) {
          this.bookName = newVal.bookName;
          this.publishDate = newVal.publishDate;
          this.language = newVal.language;
          this.publishingHouse = newVal.publishingHouse;
          this.city = newVal.city;
          this.isbn = newVal.isbn;
          this.author = newVal.author;
          this.genre = newVal.genre;
          this.id = newVal.id;
      }
  },
  template: `
      <div class="block">
          <form onsubmit="return false">
              <input type="text" placeholder="Book Name" v-model="bookName" required="required">
              <p/>
              <input type="date" v-model="publishDate" required="required">
              <p/>
              <input type="text" placeholder="Language" v-model="language" required="required">
              <p/>
              <input type="text" placeholder="Publishing House" v-model="publishingHouse" required="required">
              <p/>
              <input type="text" placeholder="City" v-model="city" required="required">
              <p/>
              <input type="text" placeholder="Isbn" v-model="isbn" required="required">
              <p/>

              <select v-model="author" required="required">
                <option disabled value="">---</option>
                <option v-for="author in authors" v-bind:value="author">{{ author.fullName }}</option>
              </select>
              <p/>
              <select v-model="genre" required="required">
                <option disabled value="">---</option>
                <option v-for="genre in genres" v-bind:value="genre">{{ genre.genreName }}</option>
              </select>
              <p/>

              <input type="reset" value="Clean">
              <input type="submit" value="Save" @click="save">
          </form>
      </div>
  `,
  methods: {
      ...Vuex.mapActions('bookModule', ['addBook', 'updateBook']),
      save: function() {
          var inputs = document.getElementsByTagName('input');

          for (var index = 0; index < inputs.length; ++index) {
             if (!inputs[index].checkValidity()) {
               return;
             }
          }

          var book = { id: this.id, bookName: this.bookName, publishDate: this.publishDate, language: this.language,
                        publishingHouse: this.publishingHouse, city: this.city, isbn: this.isbn,
                        author: this.author, genre: this.genre };

          if (this.id) {
             this.updateBook(book).then(result => {
              if (result.ok) {
                  showAlert('#bookSuccess', '#bookAction', 'updated');
              }}, error => showAlert('#bookError', '#bookToAction', 'update'));
          } else {
             this.addBook(book).then(result => {
               if (result.ok) {
                   showAlert('#bookSuccess', '#bookAction', 'created');
               }}, error => showAlert('#bookError', '#bookToAction', 'create'));
          }

          this.bookName = '';
          this.publishDate = '';
          this.language = '';
          this.publishingHouse = '';
          this.city = '';
          this.isbn = '';
          this.author = '';
          this.genre = '';
          this.id = null;
      }
  }
};
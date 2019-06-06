import { showAlert } from '../Utils.js'

export default {
  name: 'CommentForm',
  computed: Vuex.mapState('bookModule', ['books']),
  props: ['commentAttr'],
  data: function() {
      return {
          author: '',
          content: '',
          book: '',
          id: null
      }
  },
  watch: {
      commentAttr: function(newVal, oldVal) {
          this.author = newVal.author;
          this.date = newVal.date;
          this.content = newVal.content;
          this.book = newVal.book;
          this.id = newVal.id;
      }
  },
  template: `
      <div class="block">
          <form onsubmit="return false">
              <input type="text" placeholder="Author" v-model="author" required="required">
              <p/>
              <input type="text" placeholder="Content" v-model="content" required="required">
              <p/>

              <select v-model="book" required="required">
                <option disabled value="">---</option>
                <option v-for="book in books" v-bind:value="book">{{ book.bookName }}</option>
              </select>
              <p/>

              <input type="reset" value="Clean">
              <input type="submit" value="Save" @click="save">
          </form>
      </div>
  `,
  methods: {
      ...Vuex.mapActions('commentModule', ['addComment', 'updateComment']),
      save: function() {
          var inputs = document.getElementsByTagName('input');

          for (var index = 0; index < inputs.length; ++index) {
             if (!inputs[index].checkValidity()) {
               return;
             }
          }

          var comment = { id: this.id, author: this.author, date: this.date, content: this.content, book: this.book };

          if (this.id) {
             this.updateComment(comment).then(result => {
              if (result.ok) {
                  showAlert('#commentSuccess', '#commentAction', 'updated');
              }}, error => showAlert('#commentError', '#commentToAction', 'update'));
          } else {
             this.addComment(comment).then(result => {
               if (result.ok) {
                   showAlert('#commentSuccess', '#commentAction', 'created');
               }}, error => showAlert('#commentError', '#commentToAction', 'create'));
          }

          this.author = '';
          this.content = '';
          this.book = '';
          this.id = null;
      }
  }
};
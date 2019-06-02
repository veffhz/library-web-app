import AuthorTr from './AuthorTr.js'
import AuthorForm from './AuthorForm.js'
import { showAlert } from '../Utils.js'

export default {
    name: 'AuthorTable',
    computed: Vuex.mapState(['authors']),
    components: {
                AuthorTr,
                AuthorForm
            },
    data: function() {
            return {
                author: null
            }
    },
    template: `
        <div>
            <h1>Authors:</h1>
            <table class="items">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>fullName</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <author-tr v-for="author in authors" :key="author.id" :author="author"
                    :editAuthor="editAuthor" :deleteAuthor="deleteAuthor" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <author-form :authorAttr="author" />
            <div class="gap-30"></div>
            <div class="alert alert-danger" id="authorError" style="display:none;">
              <strong>Error!</strong> Access Denied! You not have permission to delete author!
            </div>
            <div class="alert alert-success" id="authorSuccess" style="display:none;">
              <strong>Success!</strong> Author deleted successfully.
            </div>
        </div>
    `,
      methods: {
          editAuthor(author) {
              this.author = author;
          },
          deleteAuthor(author) {
              this.$resource('/api/author{/id}').remove({id: author.id}).then(result => {
                  if (result.ok) {
                      this.authors.splice(this.authors.indexOf(author), 1);
                      showAlert('#authorSuccess');
                  }
              }, error => {
                  showAlert('#authorError');
              })
          }
      }
};
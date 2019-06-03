import GenreTr from './GenreTr.js'
import GenreForm from './GenreForm.js'
import { showAlert } from '../Utils.js'

export default {
    name: 'GenreTable',
    computed: Vuex.mapState('genreModule', ['genres']),
    components: {
                GenreTr,
                GenreForm
            },
    data: function() {
            return {
                genre: null
            }
    },
    template: `
        <div>
            <h1>Genres:</h1>
            <table class="items">
                <thead>
                <tr>
                    <th>id</th>
                    <th>genreName</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <genre-tr v-for="genre in genres" :key="genre.id" :genre="genre"
                    :editGenre="editGenre" :deleteGenre="deleteGenre" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <genre-form :genreAttr="genre" />
            <div class="gap-30"></div>
            <div class="alert alert-danger" id="genreError" style="display:none;">
              <strong>Error!</strong> Access Denied! You not have permission to delete genre!
            </div>
            <div class="alert alert-success" id="genreSuccess" style="display:none;">
              <strong>Success!</strong> Genre deleted successfully.
            </div>
        </div>
    `,
      methods: {
          editGenre(genre) {
              this.genre = genre;
          },
          deleteGenre(genre) {
              this.$resource('/api/genre{/id}').remove({id: genre.id}).then(result => {
                  if (result.ok) {
                      this.genres.splice(this.genres.indexOf(genre), 1);
                      showAlert('#genreSuccess');
                }
              }, error => {
                  showAlert('#genreError');
              })
          }
      }
};
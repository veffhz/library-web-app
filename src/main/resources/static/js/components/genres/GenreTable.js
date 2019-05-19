import GenreTr from './GenreTr.js'
import GenreForm from './GenreForm.js'

export default {
    name: 'GenreTable',
    props: ['genres'],
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
                    :editMethod="editMethod" :deleteMethod="deleteMethod" :genres="genres" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <genre-form :genres="genres" :genreAttr="genre" />
        </div>
    `,
      methods: {
          editMethod: function(genre) {
              this.genre = genre;
          },
          deleteMethod: function(genre) {
              this.$resource('/api/genre{/id}').remove({id: this.genre.id}).then(result => {
                if (result.ok) {
                  this.genres.splice(this.genres.indexOf(this.genre), 1)
                }
              })
          }
      }
};
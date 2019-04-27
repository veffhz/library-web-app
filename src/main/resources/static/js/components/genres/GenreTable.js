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
                    <th>Id</th>
                    <th>Genre Name</th>
                    <th>Controls</th>
                </tr>
                </thead>
                <tbody>
                <genre-tr v-for="genre in genres" :key="genre.id" :genre="genre"
                    :editMethod="editMethod" :genres="genres" />
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
          }
      }
};
import { showAlert } from '../Utils.js'

export default {
  name: 'GenreForm',
  props: ['genreAttr'],
  data: function() {
      return {
          genreName: '',
          id: null
      }
  },
  watch: {
      genreAttr: function(newVal, oldVal) {
          this.genreName = newVal.genreName;
          this.id = newVal.id;
      }
  },
  template: `
      <div class="block">
          <form onsubmit="return false">
              <input type="text" placeholder="Genre Name" v-model="genreName" required="required">
              <p/>
              <input type="reset" value="Clean">
              <input type="submit" value="Save" @click="save">
          </form>
      </div>
  `,
  methods: {
      ...Vuex.mapActions('genreModule', ['addGenre', 'updateGenre']),
      save: function() {
          var inputs = document.getElementsByTagName('input');
          for (var index = 0; index < inputs.length; ++index) {
             if (!inputs[index].checkValidity()) {
               return;
             }
          }

          var genre = { id: this.id, genreName: this.genreName };

          if (this.id) {
             this.updateGenre(genre).then(result => {
              if (result.ok) {
                  showAlert('#genreSuccess', '#genreAction', 'updated');
              }}, error => showAlert('#genreError', '#genreToAction', 'update'));
          } else {
             this.addGenre(genre).then(result => {
               if (result.ok) {
                   showAlert('#genreSuccess', '#genreAction', 'created');
               }}, error => showAlert('#genreError', '#genreToAction', 'create'));
          }

          this.genreName = '';
          this.id = null
      }
  }
};
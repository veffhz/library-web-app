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
              this.updateGenre(genre);
           } else {
              this.addGenre(genre);
          }

          this.genreName = '';
          this.id = null
      }
  }
};
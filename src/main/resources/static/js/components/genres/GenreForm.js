export default {
  name: 'GenreForm',
  computed: Vuex.mapState(['genres']),
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
      save: function() {
          var inputs = document.getElementsByTagName('input');
          for (var index = 0; index < inputs.length; ++index) {
             if (!inputs[index].checkValidity()) {
               return;
             }
          }

          var genre = { id: this.id, genreName: this.genreName };

          if (this.id) {
              this.$resource('/api/genre{/id}').update({}, genre)
              .then(response => response.json())
              .then(data => {
                  var index = this.genres.findIndex(function(item) { return item.id == data.id; });
                  this.genres.splice(index, 1, data);
                  this.genreName = '';
                  this.id = null
              });
           } else {
              this.$resource('/api/genre{/id}').save({}, genre)
              .then(response => response.json())
              .then(data => {
                  this.genres.push(data);
                  this.genreName = '';
                  this.id = null
              });
          }
      }
  }
};
export default {
  name: 'AuthorForm',
  computed: Vuex.mapState(['authors']),
  props: ['authorAttr'],
  data: function() {
      return {
          firstName: '',
          lastName: '',
          birthDate: '',
          id: null
      }
  },
  watch: {
      authorAttr: function(newVal, oldVal) {
          this.firstName = newVal.firstName;
          this.birthDate = newVal.birthDate;
          this.lastName = newVal.lastName;
          this.id = newVal.id;
      }
  },
  template: `
      <div class="block">
          <form onsubmit="return false">
              <input type="text" placeholder="First Name" v-model="firstName" required="required">
              <p/>
              <input type="date" v-model="birthDate" required="required">
              <p/>
              <input type="text" placeholder="Last Name" v-model="lastName" required="required">
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

          var author = { id: this.id, firstName: this.firstName, birthDate: this.birthDate, lastName: this.lastName };

          if (this.id) {
              this.$resource('/api/author{/id}').update({}, author)
              .then(response => response.json())
              .then(data => {
                  var index = this.authors.findIndex(function(item) { return item.id == data.id; });
                  this.authors.splice(index, 1, data);
                  this.firstName = '';
                  this.birthDate = '';
                  this.lastName = '';
                  this.id = null
              });
           } else {
              this.$resource('/api/author{/id}').save({}, author)
              .then(response => response.json())
              .then(data => {
                  this.authors.push(data);
                  this.firstName = '';
                  this.birthDate = '';
                  this.lastName = '';
                  this.id = null
              });
          }
      }
  }
};
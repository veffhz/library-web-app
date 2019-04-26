var authorApi = Vue.resource('/api/author{/id}');

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

function subStrDate(date) {
    return date != null ? date.toString().substr(0,10) : null;
}


Vue.component('author-form', {
  props: ['authors', 'authorAttr'],
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
          this.birthDate = subStrDate(newVal.birthDate);
          this.lastName = newVal.lastName;
          this.id = newVal.id;
      }
  },
  template:
        '<div class="block">' +
            '<form onsubmit="return false">' +
            '<input type="text" placeholder="First Name" v-model="firstName" required="required"><p/>' +
            '<input type="date" placeholder="Birth Date" v-model="birthDate" required="required"><p/>' +
            '<input type="text" placeholder="Last Name" v-model="lastName" required="required"><p/>' +
            '<input type="reset" value="Clean">' +
            '<input type="submit" value="Save" @click="save"></form>' +
        '</div>',
  methods: {
      save: function() {
          var inputs = document.getElementsByTagName('input');
          for (index = 0; index < inputs.length; ++index) {
             if (!inputs[index].checkValidity()) {
               return;
             }
          }

          var author = { id: this.id, firstName: this.firstName, birthDate: this.birthDate, lastName: this.lastName };
          if (this.id) {
              authorApi.update({}, author)
              .then(response => response.json())
              .then(data => {
                  var index = getIndex(this.authors, data.id);
                  this.authors.splice(index, 1, data);
                  this.firstName = '';
                  this.birthDate = '';
                  this.lastName = '';
                  this.id = null
              });
           } else {
              authorApi.save({}, author)
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
});

Vue.component('author-tr', {
  props: ['author', 'editMethod', 'authors'],
  template:
        '<tr><td>{{ author.id }}</td>' +
        '<td>{{ author.fullName }}</td>' +
        '<td>' +
            '<input type="button" value="edit" @click="edit">' +
            '<input type="button" value="x" @click="del" />' +
        '</td></tr>',
  methods: {
      edit: function() {
          this.editMethod(this.author);
      },
      del: function() {
          authorApi.remove({id: this.author.id}).then(result => {
            if (result.ok) {
              this.authors.splice(this.authors.indexOf(this.author), 1)
            }
          })
      }
  }
});

Vue.component('authors-table', {
  props: ['authors'],
  data: function() {
      return {
          author: null
      }
  },
  template: '<div>' +
                '<h1>Authors:</h1>' +
                '<table class="items">' +
                '<thead>' +
                '<tr><th>Id</th><th>Full Name</th><th>Controls</th></tr>' +
                '</thead><tbody>' +
                    '<author-tr v-for="author in authors" :key="author.id" :author="author" :editMethod="editMethod" :authors="authors"/>' +
                '</tbody></table>' +
            '<div class="gap-30"></div><p>Edit:</p><author-form :authors="authors" :authorAttr="author" />' +
            '</div>',
  created: function() {
      authorApi.get()
      .then(response => response.json())
      .then(data => {
          data.forEach(author => this.authors.push(author))
      });
  },
  methods: {
      editMethod: function(author) {
          this.author = author;
      }
  }
});

var app = new Vue({
  el: '#app',
  template: '<authors-table :authors="authors" />',
  data: {
    authors: []
  }
});
var authorApi = Vue.resource('/api/authors{/id}');

Vue.component('author-tr', {
  props: ['author'],
  template:
        '<tr><td>{{ author.id }}</td>' +
        '<td>{{ author.fullName }}</td>' +
        '<td><a :href="\'/api/authors/\' + author.id">Detail</a></td></tr>'
});

Vue.component('authors-list', {
  props: ['authors'],
  template: '<table class="items">' +
            '<thead><tr><th>ID</th><th>Full Name</th></tr></thead><tbody>' +
               '<author-tr v-for="author in authors" :key="author.id" :author="author" />' +
            '</tbody></table>',
  created: function() {
     authorApi.get()
     .then(response => response.json())
     .then(data => {
        data.forEach(author => this.authors.push(author))
     });
  }
});

var app = new Vue({
  el: '#app',
  template: '<authors-list :authors="authors" />',
  data: {
    authors: []
  }
});
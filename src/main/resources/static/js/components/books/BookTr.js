export default {
  name: 'BookTr',
  props: ['book', 'editMethod', 'books'],
  template: `
      <tr>
          <td>{{ book.id }}</td>
          <td>{{ book.bookName }}</td>
          <td>{{ book.publishDate }}</td>
          <td>{{ book.language }}</td>
          <td>{{ book.publishingHouse }}</td>
          <td>{{ book.city }}</td>
          <td>{{ book.isbn }}</td>
          <td>{{ book.author.fullName }}</td>
          <td>{{ book.genre.genreName }}</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        edit: function() {
            this.editMethod(this.book);
        },
        del: function() {
            this.$resource('/api/book{/id}').remove({id: this.book.id}).then(result => {
              if (result.ok) {
                this.books.splice(this.books.indexOf(this.book), 1)
              }
            })
        }
    }
};
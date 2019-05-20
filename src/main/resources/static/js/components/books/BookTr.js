export default {
  name: 'BookTr',
  props: ['book', 'editMethod', 'deleteMethod', 'books'],
  template: `
      <tr>
          <td>{{ book.id }}</td>
          <td>{{ book.bookName }}</td>
          <td>{{ book.publishDate }}</td>
          <td>{{ book.language }}</td>
          <td>{{ book.publishingHouse }}</td>
          <td>{{ book.city }}</td>
          <td>{{ book.isbn }}</td>
          <td v-if="book.author != null">{{ book.author.fullName }}</td>
          <td class="gray" v-else>Not found!</td>
          <td v-if="book.genre != null">{{ book.genre.genreName }}</td>
          <td class="gray" v-else>Not found!</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        edit() {
            this.editMethod(this.book);
        },
        del() {
            this.deleteMethod(this.book);
        }
    }
};
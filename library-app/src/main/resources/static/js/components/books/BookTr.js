import { showAlert } from '../Utils.js'

export default {
  name: 'BookTr',
  props: ['book', 'editBook'],
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
        ...Vuex.mapActions('bookModule',['removeBook']),
        edit() {
            this.editBook(this.book);
        },
        del() {
            this.removeBook(this.book)
            .then(result => {
              if (result.ok) {
                  showAlert('#bookSuccess', '#bookAction', 'deleted');
              }}, error => showAlert('#bookError', '#bookToAction', 'delete'));
        }
    }
};
import { showAlert } from '../Utils.js'

export default {
  name: 'AuthorTr',
  props: ['author', 'editAuthor'],
  template: `
      <tr>
          <td>{{ author.id }}</td>
          <td>{{ author.fullName }}</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        ...Vuex.mapActions('authorModule',['removeAuthor']),
        edit() {
            this.editAuthor(this.author);
        },
        del() {
            this.removeAuthor(this.author)
            .then(result => {
              if (result.ok) {
                  showAlert('#authorSuccess', '#authorAction', 'deleted');
              }}, error => showAlert('#authorError', '#authorToAction', 'delete'));
        }
    }
};
export default {
  name: 'AuthorTr',
  props: ['author', 'editAuthor', 'deleteAuthor'],
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
        edit() {
            this.editAuthor(this.author);
        },
        del() {
            this.deleteAuthor(this.author);
        }
    }
};
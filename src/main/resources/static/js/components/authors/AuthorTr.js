export default {
  name: 'AuthorTr',
  props: ['author', 'editMethod', 'deleteMethod', 'authors'],
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
            this.editMethod(this.author);
        },
        del() {
            this.deleteMethod(this.author);
        }
    }
};
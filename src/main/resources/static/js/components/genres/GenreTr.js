export default {
  name: 'GenreTr',
  props: ['genre', 'editMethod', 'deleteMethod', 'genres'],
  template: `
      <tr>
          <td>{{ genre.id }}</td>
          <td>{{ genre.genreName }}</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        edit() {
            this.editMethod(this.genre);
        },
        del() {
            this.deleteMethod(this.genre);
        }
    }
};
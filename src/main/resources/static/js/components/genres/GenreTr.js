export default {
  name: 'GenreTr',
  props: ['genre', 'editGenre', 'deleteGenre', 'genres'],
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
            this.editGenre(this.genre);
        },
        del() {
            this.deleteGenre(this.genre);
        }
    }
};
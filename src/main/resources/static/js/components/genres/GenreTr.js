import { showAlert } from '../Utils.js'

export default {
  name: 'GenreTr',
  props: ['genre', 'editGenre'],
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
        ...Vuex.mapActions('genreModule',['removeGenre']),
        edit() {
            this.editGenre(this.genre);
        },
        del() {
            this.removeGenre(this.genre).then(result => {
              if (result.ok) {
                  showAlert('#genreSuccess', '#genreAction', 'deleted');
              }}, error => showAlert('#genreError', '#genreToAction', 'delete'));
        }
    }
};
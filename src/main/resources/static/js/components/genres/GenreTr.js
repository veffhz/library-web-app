export default {
  name: 'GenreTr',
  props: ['genre', 'editMethod', 'genres'],
  template: `
      <tr><td>{{ genre.id }}</td>
          <td>{{ genre.genreName }}</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        edit: function() {
            this.editMethod(this.genre);
        },
        del: function() {
            this.$resource('/api/genre{/id}').remove({id: this.genre.id}).then(result => {
              if (result.ok) {
                this.genres.splice(this.genres.indexOf(this.genre), 1)
              }
            })
        }
    }
};
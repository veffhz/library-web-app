export default {
  name: 'AuthorTr',
  props: ['author', 'editMethod', 'authors'],
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
        edit: function() {
            this.editMethod(this.author);
        },
        del: function() {
            this.$resource('/api/author{/id}').remove({id: this.author.id}).then(result => {
              if (result.ok) {
                this.authors.splice(this.authors.indexOf(this.author), 1)
              }
            })
        }
    }
};
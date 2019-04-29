export default {
  name: 'CommentTr',
  props: ['comment', 'editMethod', 'comments'],
  template: `
      <tr>
          <td>{{ comment.id }}</td>
          <td>{{ comment.author }}</td>
          <td>{{ comment.date }}</td>
          <td>{{ comment.content }}</td>
          <td>{{ comment.book.bookName }}</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        edit: function() {
            this.editMethod(this.comment);
        },
        del: function() {
            this.$resource('/api/comment{/id}').remove({id: this.comment.id}).then(result => {
              if (result.ok) {
                this.comments.splice(this.comments.indexOf(this.comment), 1)
              }
            })
        }
    }
};
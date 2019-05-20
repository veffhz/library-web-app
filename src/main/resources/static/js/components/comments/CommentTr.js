export default {
  name: 'CommentTr',
  props: ['comment', 'editMethod', 'deleteMethod', 'comments'],
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
        edit() {
            this.editMethod(this.comment);
        },
        del() {
            this.deleteMethod(this.comment);
        }
    }
};
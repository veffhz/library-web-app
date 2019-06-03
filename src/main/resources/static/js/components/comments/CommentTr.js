import { showAlert } from '../Utils.js'

export default {
  name: 'CommentTr',
  props: ['comment', 'editComment', 'deleteComment'],
  template: `
      <tr>
          <td>{{ comment.id }}</td>
          <td>{{ comment.author }}</td>
          <td>{{ comment.date }}</td>
          <td>{{ comment.content }}</td>
          <td v-if="comment.book != null">{{ comment.book.bookName }}</td>
          <td class="gray" v-else>Not found!</td>
          <td>
              <input type="button" value="edit" @click="edit">
              <input type="button" value="x" @click="del"/>
          </td>
      </tr>
  `,
  methods: {
        ...Vuex.mapActions('commentModule',['removeComment']),
        edit() {
            this.editComment(this.comment);
        },
        del() {
            this.removeComment(this.comment)
            .then(result => {
              if (result.ok) {
                  showAlert('#commentSuccess');
              }}, error => showAlert('#commentError'));
        }
    }
};
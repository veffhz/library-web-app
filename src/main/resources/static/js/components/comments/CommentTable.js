import CommentTr from './CommentTr.js'
import CommentForm from './CommentForm.js'
import { showAlert } from '../Utils.js'

export default {
    name: 'CommentTable',
    props: ['comments', 'books'],
    components: {
                CommentTr,
                CommentForm
            },
    data: function() {
            return {
                comment: null
            }
    },
    template: `
        <div>
            <h1>Comments:</h1>
            <table class="items">
                <thead>
                <tr>
                    <th>id</th>
                    <th>author</th>
                    <th>date</th>
                    <th>content</th>
                    <th>book</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <comment-tr v-for="comment in comments" :key="comment.id" :comment="comment"
                    :editComment="editComment" :deleteComment="deleteComment" :comments="comments" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <comment-form :comments="comments" :commentAttr="comment" :books="books" />
            <div class="gap-30"></div>
            <div class="alert alert-danger" id="commentError" style="display:none;">
              <strong>Error!</strong> Access Denied! You not have permission to delete comment!
            </div>
            <div class="alert alert-success" id="commentSuccess" style="display:none;">
              <strong>Success!</strong> Comment deleted successfully.
            </div>
        </div>
    `,
      methods: {
          editComment(comment) {
              this.comment = comment;
          },
          deleteComment(comment) {
              this.$resource('/api/comment{/id}').remove({id: comment.id}).then(result => {
                  if (result.ok) {
                      this.comments.splice(this.comments.indexOf(comment), 1);
                      showAlert('#commentSuccess');
                }
              }, error => {
                  showAlert('#commentError');
              })
          }
      }
};
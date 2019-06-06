import CommentTr from './CommentTr.js'
import CommentForm from './CommentForm.js'

export default {
    name: 'CommentTable',
    computed: Vuex.mapState('commentModule', ['comments']),
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
                <comment-tr v-for="comment in comments" :key="comment.id"
                :comment="comment" :editComment="editComment" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <comment-form :commentAttr="comment" />
            <div class="gap-30"></div>
            <div class="alert alert-danger" id="commentError" style="display:none;">
              <strong>Error!</strong> Access Denied! You not have permission to <span id="commentToAction"></span> comment!
            </div>
            <div class="alert alert-success" id="commentSuccess" style="display:none;">
              <strong>Success!</strong> Comment <span id="commentAction"></span> successfully.
            </div>
        </div>
    `,
      methods: {
          editComment(comment) {
              this.comment = comment;
          }
      }
};
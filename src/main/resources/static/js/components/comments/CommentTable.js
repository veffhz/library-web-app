import CommentTr from './CommentTr.js'
import CommentForm from './CommentForm.js'

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
                    :editMethod="editMethod" :comments="comments" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <comment-form :comments="comments" :commentAttr="comment" :books="books" />
        </div>
    `,
      methods: {
          editMethod: function(comment) {
              this.comment = comment;
          }
      }
};
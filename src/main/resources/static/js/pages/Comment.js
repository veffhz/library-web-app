import CommentTable from '../components/comments/CommentTable.js'

export default {
  name: 'Comment',
  components: {
        CommentTable
  },
  data() {
      return {
        books: frontendData.books,
        comments: frontendData.comments
      }
  },
  template: '<comment-table :comments="comments" :books="books" />'
};
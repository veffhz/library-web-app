import AuthorTable from '../components/authors/AuthorTable.js'

export default {
  name: 'Info',
  components: {
        AuthorTable
  },
  data() {
      return {
        authors: frontendData.authors
      }
  },
  template: '<author-table :authors="authors" />'
};
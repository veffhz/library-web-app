import AuthorTable from '../components/authors/AuthorTable.js'
import GenreTable from '../components/genres/GenreTable.js'

export default {
  name: 'App',
  components: {
        AuthorTable,
        GenreTable
  },
  data() {
      return {
        authors: frontendData.authors,
        genres: frontendData.genres
      }
  },
  //template: '<author-table :authors="authors" />',
  template: '<genre-table :genres="genres" />',
};
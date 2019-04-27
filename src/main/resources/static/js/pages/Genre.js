import GenreTable from '../components/genres/GenreTable.js'

export default {
  name: 'Genre',
  components: {
        GenreTable,
  },
  data() {
      return {
        genres: frontendData.genres
      }
  },
  template: '<genre-table :genres="genres" />'
};
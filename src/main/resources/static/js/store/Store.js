Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      authors: frontendData.authors,
      genres: frontendData.genres,
      books: frontendData.books,
      comments: frontendData.comments
  },
  getters: {
      authors() {
        return this.authors;
      },
      genres() {
        return this.genres;
      },
      books() {
        return this.books;
      },
      comments() {
        return this.comments;
      }
  },
  mutations: {
    increment (state) {
      state.count++
    }
  }
})
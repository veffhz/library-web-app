Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      authors: frontendData.authors,
      genres: frontendData.genres,
      books: frontendData.books,
      comments: frontendData.comments
  },
  mutations: {
    increment (state) {
      state.count++
    }
  }
})
Vue.use(Vuex)

import { authorModule } from './modules/Author.js'

export default new Vuex.Store({
  state: {
        genres: frontendData.genres,
        books: frontendData.books,
        comments: frontendData.comments
  },
  mutations: {},
  actions: {},
  modules: {
    authorModule
  }
})
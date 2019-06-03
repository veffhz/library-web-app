Vue.use(Vuex)

import { authorModule } from './modules/Author.js'
import { bookModule } from './modules/Book.js'
import { commentModule } from './modules/Comment.js'
import { genreModule } from './modules/Genre.js'

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    authorModule,
    bookModule,
    commentModule,
    genreModule
  }
})
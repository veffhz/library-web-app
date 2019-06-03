Vue.use(Vuex)

export const authorModule = {
  namespaced: true,

  state: {
      authors: frontendData.authors
  },

  mutations: {

    addAuthor(state, author) {
      state.authors = [...state.authors, author]
    },

    updateAuthor(state, author) {
      var indexUpdated = state.authors.findIndex(item => item.id === author.id);

      state.authors = [
          ...state.authors.slice(0, indexUpdated),
          author,
          ...state.authors.slice(indexUpdated + 1)
      ]
    },

    removeAuthor(state, author) {
      var indexDeleted = state.authors.findIndex(item => item.id === author.id);

      if (indexDeleted > -1) {
          state.authors = [
              ...state.authors.slice(0, indexDeleted),
              ...state.authors.slice(indexDeleted + 1)
          ]
      }
    }
  },

  actions: {

        async add({commit, state}, author) {
            const result = await Vue.resource('/api/author{/id}').save({}, author);
            const data = await result.json();
            commit('addAuthor', data);
        },

        async update({commit}, author) {
            const result = await Vue.resource('/api/author{/id}').update({}, author)
            const data = await result.json()
            commit('updateAuthor', data)
        },

        async remove({commit}, author) {
            const result = await Vue.resource('/api/author{/id}').remove({id: author.id})
            if (result.ok) {
                commit('removeAuthor', author);
            }
            return result
        },
  }
}
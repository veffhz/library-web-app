Vue.use(Vuex)

export const authorModule = {
  namespaced: true,

  state: {
      ...frontendData.authorDto
  },

  mutations: {

        addAuthorMutation(state, author) {
          state.authors = [...state.authors, author]
        },

        updateAuthorMutation(state, author) {
          var indexUpdated = state.authors.findIndex(item => item.id === author.id);

          state.authors = [
              ...state.authors.slice(0, indexUpdated),
              author,
              ...state.authors.slice(indexUpdated + 1)
          ]
        },

        removeAuthorMutation(state, author) {
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

        async addAuthor({commit, state}, author) {
            const result = await Vue.resource('/api/author{/id}').save({}, author);
            const data = await result.json();
            if (result.ok) {
                commit('addAuthorMutation', data);
            }
            return result;
        },

        async updateAuthor({commit}, author) {
            const result = await Vue.resource('/api/author{/id}').update({}, author)
            const data = await result.json()
            if (result.ok) {
                commit('updateAuthorMutation', data);
            }
            return result;
        },

        async removeAuthor({dispatch, commit}, author) {
            const result = await Vue.resource('/api/author{/id}').remove({id: author.id})
            if (result.ok) {
                commit('removeAuthorMutation', author);
                dispatch('bookModule/removeBookByAuthorId', author.id, { root: true });
            }
            return result;
        },
  }
}
Vue.use(Vuex)

export const commentModule = {
  namespaced: true,

  state: {
      comments: frontendData.comments
  },

  mutations: {

    addCommentMutation(state, comment) {
      state.comments = [...state.comments, comment]
    },

    updateCommentMutation(state, comment) {
      var indexUpdated = state.comments.findIndex(item => item.id === comment.id);

      state.comments = [
          ...state.comments.slice(0, indexUpdated),
          comment,
          ...state.comments.slice(indexUpdated + 1)
      ]
    },

    removeCommentMutation(state, comment) {
      var indexDeleted = state.comments.findIndex(item => item.id === comment.id);

      if (indexDeleted > -1) {
          state.comments = [
              ...state.comments.slice(0, indexDeleted),
              ...state.comments.slice(indexDeleted + 1)
          ]
      }
    }
  },

  actions: {

        async addComment({commit, state}, comment) {
            const result = await Vue.resource('/api/comment{/id}').save({}, comment);
            const data = await result.json();
            commit('addCommentMutation', data);
        },

        async updateComment({commit}, comment) {
            const result = await Vue.resource('/api/comment{/id}').update({}, comment)
            const data = await result.json()
            commit('updateCommentMutation', data)
        },

        async removeComment({commit}, comment) {
            const result = await Vue.resource('/api/comment{/id}').remove({id: comment.id})
            if (result.ok) {
                commit('removeCommentMutation', comment);
            }
            return result
        },
  }
}
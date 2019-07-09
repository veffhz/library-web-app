Vue.use(Vuex)

export const genreModule = {
  namespaced: true,

  state: {
       ...frontendData.genreDto
  },

  mutations: {

        addGenreMutation(state, genre) {
          state.genres = [...state.genres, genre]
        },

        updateGenreMutation(state, genre) {
          var indexUpdated = state.genres.findIndex(item => item.id === genre.id);

          state.genres = [
              ...state.genres.slice(0, indexUpdated),
              genre,
              ...state.genres.slice(indexUpdated + 1)
          ]
        },

        removeGenreMutation(state, genre) {
          var indexDeleted = state.genres.findIndex(item => item.id === genre.id);

          if (indexDeleted > -1) {
              state.genres = [
                  ...state.genres.slice(0, indexDeleted),
                  ...state.genres.slice(indexDeleted + 1)
              ]
          }
        }
  },

  actions: {

        async addGenre({commit, state}, genre) {
            const result = await Vue.resource('/api/genre{/id}').save({}, genre);
            const data = await result.json();
            if (result.ok) {
                commit('addGenreMutation', data);
            }
            return result;
        },

        async updateGenre({commit}, genre) {
            const result = await Vue.resource('/api/genre{/id}').update({}, genre)
            const data = await result.json()
            if (result.ok) {
                commit('updateGenreMutation', data);
            }
            return result;
        },

        async removeGenre({dispatch, commit}, genre) {
            const result = await Vue.resource('/api/genre{/id}').remove({id: genre.id})
            if (result.ok) {
                commit('removeGenreMutation', genre);
                dispatch('bookModule/removeBookByGenreId', genre.id, { root: true });
            }
            return result;
        },
  }
}
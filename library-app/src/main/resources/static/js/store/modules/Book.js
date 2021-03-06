Vue.use(Vuex)

export const bookModule = {
  namespaced: true,

  state: {
      ...frontendData.bookDto
  },

  mutations: {

        addBookMutation(state, book) {
          state.books = [...state.books, book]
        },

        updateBookMutation(state, book) {
          var indexUpdated = state.books.findIndex(item => item.id === book.id);

          state.books = [
              ...state.books.slice(0, indexUpdated),
              book,
              ...state.books.slice(indexUpdated + 1)
          ]
        },

        removeBookMutation(state, book) {
          var indexDeleted = state.books.findIndex(item => item.id === book.id);

          if (indexDeleted > -1) {
              state.books = [
                  ...state.books.slice(0, indexDeleted),
                  ...state.books.slice(indexDeleted + 1)
              ]
          }
        },

        addBookPageMutation(state, books) {
            const targetBooks = state.books
                .concat(books)
                .reduce((result, value) => {
                    result[value.id] = value
                    return result;
                }, {})

            state.books = Object.values(targetBooks)
        },
        updateTotalPagesMutation(state, totalPages) {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage) {
            state.currentPage = currentPage
        }
  },

  actions: {

        async addBook({commit, state}, book) {
            const result = await Vue.resource('/api/book{/id}').save({}, book);
            const data = await result.json();
            if (result.ok) {
                commit('addBookMutation', data);
            }
            return result;
        },

        async updateBook({commit}, book) {
            const result = await Vue.resource('/api/book{/id}').update({}, book)
            const data = await result.json()
            if (result.ok) {
                commit('updateBookMutation', data);
            }
            return result;
        },

        async removeBook({commit}, book) {
            const result = await Vue.resource('/api/book{/id}').remove({id: book.id})
            if (result.ok) {
                commit('removeBookMutation', book);
                commit('commentModule/removeCommentByBookIdMutation', book.id, { root: true });
            }
            return result;
        },

        async removeBookByAuthorId({commit, state}, authorId) {
            var booksToBeDeleted = state.books.filter(book => authorId === book.author.id);
            booksToBeDeleted.forEach(function(book) {
                commit('removeBookMutation', book);
                commit('commentModule/removeCommentByBookIdMutation', book.id, { root: true });
            });
        },

        async removeBookByGenreId({commit, state}, genreId) {
            var booksToBeDeleted = state.books.filter(book => genreId === book.genre.id);
            booksToBeDeleted.forEach(function(book) {
                commit('removeBookMutation', book);
                commit('commentModule/removeCommentByBookIdMutation', book.id, { root: true });
            });
        },

        async loadPageAction({commit, state}) {
            const response = await Vue.http.get('/api/book', {params: { page: state.currentPage + 1 }})
            const data = await response.json()

            commit('addBookPageMutation', data.books)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
        }
  }
}
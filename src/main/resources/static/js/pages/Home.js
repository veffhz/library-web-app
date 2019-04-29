
export default {
  name: 'Home',
  data() {
      return {
        authors: frontendData.authors,
        genres: frontendData.genres,
        books: frontendData.books,
        comments: frontendData.comments
      }
  },
  template: `
  <div>
      <h1>Info</h1>
      <p>Now we have:</p>
      <table class="items">
          <thead>
          <tr>
              <th>#</th>
              <th>what</th>
              <th>count</th>
          </tr>
          </thead>
          <tbody>
          <tr>
              <td>1</td>
              <td>Authors</td>
              <td>{{ authors.length }}</td>
          </tr>
          <tr>
              <td>2</td>
              <td>Genres</td>
              <td>{{ genres.length }}</td>
          </tr>
          <tr>
              <td>3</td>
              <td>Books</td>
              <td>{{ books.length }}</td>
          </tr>
          <tr>
              <td>4</td>
              <td>Comments</td>
              <td>{{ comments.length }}</td>
          </tr>
          </tbody>
      </table>
  </div>`
};
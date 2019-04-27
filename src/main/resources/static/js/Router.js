import Home from './pages/Home.js'
import Author from './pages/Author.js'
import Genre from './pages/Genre.js'
import Book from './pages/Book.js'
import Comment from './pages/Comment.js'

Vue.use(VueRouter)

export default new VueRouter({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/author',
      name: 'author',
      component: Author
    },
    {
      path: '/genre',
      name: 'genre',
      component: Genre
    },
    {
      path: '/book',
      name: 'book',
      component: Book
    },
    {
      path: '/comment',
      name: 'comment',
      component: Comment
    }
  ]
})
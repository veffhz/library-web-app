import Home from '../pages/Home.js'
import Author from '../pages/Author.js'

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
    }
  ]
})
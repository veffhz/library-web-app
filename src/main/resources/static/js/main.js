import App from './App.js'
import router from './Router.js'
import store from './store/Store.js'

Vue.use(VueResource)

new Vue({
    el: '#app',
    store,
    router,
    components: { App },
    render: a => a(App)
});

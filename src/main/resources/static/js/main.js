import App from './App.js';
import router from './router/index.js';

Vue.use(VueResource)

new Vue({
    el: '#app',
    router,
    components: { App },
    render: a => a(App)
});

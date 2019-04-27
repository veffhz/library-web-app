import App from './pages/App.js';

Vue.use(VueResource)

new Vue({
    el: '#app',
    render: a => a(App)
});

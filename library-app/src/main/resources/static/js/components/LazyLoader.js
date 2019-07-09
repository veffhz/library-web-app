export default {
    name: 'LazyLoader',
    template: `<span></span>`,
    methods: {...Vuex.mapActions('bookModule', ['loadPageAction'])},
    mounted() {
        window.onscroll = () => {
            const el = document.documentElement
            const isBottomOfScreen = el.scrollTop + window.innerHeight === el.offsetHeight

            if (isBottomOfScreen) {
                this.loadPageAction()
            }
        }
    },
    beforeDestroy() {
        window.onscroll = null
    }
}
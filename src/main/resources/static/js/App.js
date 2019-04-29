export default {
  name: 'App',

  template: `
  <div id="app">
  <!-- navbar -->
        <div class="navbar navbar-expand-lg fixed-top navbar-light bg-light">
          <div class="container">
          <router-link to="/" class="navbar-brand">Home</router-link>
          <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav">
              <li class="nav-item">
                <router-link class="nav-link" to="/author">Author</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/genre">Genre</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/book">Book</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/comment">Comment</router-link>
              </li>
            </ul>
          </div>
          </div>
        </div>
        <div class="container">
           <router-view/>
       </div>
  </div>
  `
};
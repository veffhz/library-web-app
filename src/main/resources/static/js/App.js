export default {
  name: 'App',

  template: `
  <div id="app">
  <!-- navbar -->
        <div class="navbar navbar-expand-lg fixed-top navbar-light bg-light">
          <div class="container">
          <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav">
              <li class="nav-item">
                <router-link class="nav-link" to="/">Home</router-link>
              </li>
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
              <li class="nav-item">
                <a class="nav-link" href="/swagger-ui.html">Swagger</a>
              </li>
            </ul>
          </div>
          <form method="post" action="/logout">
            <button class="button-cosmo btn btn-link" type="submit">Log Out</button>
          </form>
          </div>
        </div>
        <div class="container">
           <router-view/>
       </div>
  </div>
  `
};
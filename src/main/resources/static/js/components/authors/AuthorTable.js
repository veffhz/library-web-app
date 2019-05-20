import AuthorTr from './AuthorTr.js'
import AuthorForm from './AuthorForm.js'

export default {
    name: 'AuthorTable',
    props: ['authors'],
    components: {
                AuthorTr,
                AuthorForm
            },
    data: function() {
            return {
                author: null
            }
    },
    template: `
        <div>
            <h1>Authors:</h1>
            <table class="items">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>fullName</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <author-tr v-for="author in authors" :key="author.id" :author="author"
                    :editMethod="editMethod" :deleteMethod="deleteMethod" :authors="authors" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <author-form :authors="authors" :authorAttr="author" />
        </div>
    `,
      methods: {
          editMethod(author) {
              this.author = author;
          },
          deleteMethod(author) {
              this.$resource('/api/author{/id}').remove({id: author.id}).then(result => {
                  if (result.ok) {
                      this.authors.splice(this.authors.indexOf(author), 1)
                  }
              })
          },
      }
};
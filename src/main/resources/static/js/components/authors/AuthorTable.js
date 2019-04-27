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
                    <th>Full Name</th>
                    <th>Controls</th>
                </tr>
                </thead>
                <tbody>
                <author-tr v-for="author in authors" :key="author.id" :author="author"
                    :editMethod="editMethod" :authors="authors" />
                </tbody>
            </table>
            <div class="gap-30"></div>
            <p>Edit:</p>
            <author-form :authors="authors" :authorAttr="author" />
        </div>
    `,
      methods: {
          editMethod: function(author) {
              this.author = author;
          }
      }
};
<template>
  <div class="main">
    <search-view v-if="view ==='search'" :accountId="accountId"></search-view>
    <file-update v-if="view ==='upload'" :accountId="accountId"></file-update>
    <drug-compatibility v-if="view ==='drug'" :accountId="accountId"></drug-compatibility>
  </div>
</template>

<script>
import MainHeader from '../components/Header.vue'
import SearchView from '../components/SearchView.vue'
import DrugCompatibility from './Drugs'
import FileUpdate from './FileUpdate'
import { EventBus } from '../router/index.js'
export default {
  name: 'MainView',
  components: {
    FileUpdate,
    DrugCompatibility,
    MainHeader,
    SearchView
  },
  props: {
    accountId: Number,
    view: String
  },
  data () {
    return {
      msg: 'Test'
    }
  },
  mounted () {
    EventBus.$on('view', view => {
      console.log(`Oh, that's nice. It's gotten ${view} clicks! :)`)
    })
  },
  events: {
    'login': function (id) {
      this.accountId = id
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

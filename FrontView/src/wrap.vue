<template>
    <div v-on:login="print">
      <main-header v-if="verified"></main-header>
      <login v-if="!verified"></login>
      <main-view v-if="verified" :account-id="accountId" :view="view"></main-view>
    </div>
</template>

<script>
import MainHeader from './components/Header.vue'
import Login from './components/Login'
import MainView from './components/MainView'
import { EventBus } from './router/index.js'

export default {
  components: {
    MainHeader,
    MainView,
    Login},
  name: 'wrap',
  data () {
    return {
      accountId: '',
      verified: false,
      view: 'search'
    }
  },
  mounted () {
    console.log('hola')
    EventBus.$on('login', id => {
      this.accountId = id
      this.verified = true
      console.log(this.accountId)
      console.log(this.verified)
    })
    EventBus.$on('view', view => {
      this.view = view
      console.log(`Oh, that's nice. It's gotten ${view} clicks! :)`)
    })
  },
  methods: {
    print () {
      console.log('wtf')
    }
  },
  events: {
    'login': function (id) {
      // this.$broadcast('login', id)
    },
    'view': function (view) {
      this.view = view
      // this.$broadcast('view', view)
    }
  }
}
</script>

<style scoped>

</style>

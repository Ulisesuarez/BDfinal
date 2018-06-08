<template>
  <v-toolbar app extended
             fixed>
    <v-container>
    <v-layout row wrap style="background-color: #006A9A" >
    <div class="logo"><a href="/" @click.prevent.stop="$router.push('/')"><img src="../logo.png" id="header-logo-img"/></a>
    </div>
      <v-flex id="subheader" d-flex xs2 style="margin-right: 50px; min-width: 400px; color:#7FFFD4">
         <h2>My space</h2>
      </v-flex>

        <v-flex d-flex xs1>
    <v-tooltip slot="activator" bottom>
      <v-btn flat icon dark slot="activator" @click="selectView('upload')">
        <v-icon>arrow_upward</v-icon>
      </v-btn>
       <span>Upload File</span>
    </v-tooltip>
        </v-flex>
      <v-flex d-flex xs1>
      <v-tooltip bottom slot="activator">
        <v-btn flat slot="activator" dark    @click="selectView('drug')">
          <v-icon>local_pharmacy</v-icon>
        </v-btn>
         <span>DrugCompatibility</span>
      </v-tooltip>
        </v-flex>
          <v-flex d-flex xs1>
      <v-tooltip bottom slot="activator">
        <v-btn flat dark icon slot="activator" @click="selectView('search')">
          <v-icon>search</v-icon>
        </v-btn>
         <span>Search</span>
      </v-tooltip>
          </v-flex>
      <v-flex d-flex xs1>
    <v-menu offset-y right>
      <v-tooltip bottom slot="activator">
        <v-btn flat dark icon slot="activator"
        onclick="document.getElementById('sp1').style.display='none'"
        onmouseover="document.getElementById('sp1').style.display='inline'">
          <v-icon>account_circle</v-icon>
        </v-btn>
         <span id="sp1">{{user.username}}</span>
      </v-tooltip>
      <v-card>
        <v-container fluid grid-list-lg>
          <v-layout row>
            <div class="identity">
              <div class="name headline">{{user.username}}</div>
              <div class="username">
                <a href="/Myprofile">My profile</a>
              </div>
            </div>
          </v-layout>
          <div class="user-card-actions">
            <v-btn to="/">
              <v-icon left>power_settings_new</v-icon>
              Logout
            </v-btn>
          </div>
        </v-container>
      </v-card>
    </v-menu>
        </v-flex>
      </v-layout>
    </v-container>
<!--    <v-btn to="/access/register" v-if="!isAuthenticated && $route.path.startsWith('/info')">
      <v-icon left>exit_to_app</v-icon>
      {{$t('Access')}}
    </v-btn>
-->
  </v-toolbar>
</template>

<script>
import {EventBus} from '../router/index.js'

export default {
  name: 'main-header',
  props: {accountId: String},
  data () {
    return {
      locale: 'en-US',
      user: {}
    }
  },
  beforeMount () {
    this.$http.get('http://localhost:8088/MedicalDB/User?userId=' + this.accountId).then(response => {
      // JSON responses are automatically parsed.
      this.user = response.data
      console.log(response.data)
      this.httpStatusCode = response.status
      this.httpStatusText = response.statusText
      this.headers = response.headers
      this.fullResponse = response
    })
      .catch(e => {
        console.log(e)
      })
  },
  methods: {
    selectView (view) {
      EventBus.$emit('view', view)
    }
  }
}
</script>
<style lang="css">
  .flex{
max-width: 56px;
    display: inline-flex;
    position: static;
  }
  #header-logo-img{
    max-height: 100%;
    height: 100%;
    display: inline-flex;
    position: static;
    width: 200px;
  }
  .logo {
    background-color: aquamarine;
      max-height: 100%;
        height: 100%;
        width: 100%;
  }
  .toolbar__extension {
      display: none;
  }
</style>

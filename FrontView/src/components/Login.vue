<template>
    <v-container>
      <v-layout>
        <v-card-text>
          <v-card-title><h1>Login</h1></v-card-title>
        <v-flex sx12>
          <v-text-field v-model="userName" label="Username"></v-text-field>
        </v-flex>
          <v-flex sx12>
          <v-text-field label="password">password</v-text-field>
          <v-btn @click="logIn">Login</v-btn>
        </v-flex>
        </v-card-text>
      </v-layout>
    </v-container>
</template>

<script>
import { EventBus } from '../router/index.js'

export default {
  name: 'login',
  data () {
    return {
      userName: '',
      accountId: '0'
    }
  },
  watch: {
    accountId (val) {
      if (val === 0) {
        alert('This user does not exist, try Again')
      } else {
        console.log('emiting? ' + val)
        EventBus.$emit('login', val)
        this.$emit('login', val)
        this.$parent.$emit('login', val)
      }
    }
  },
  methods: {
    logIn () {
      this.$http.get('http://localhost:8088/MedicalDB/login?username=' + this.userName).then(response => {
        // JSON responses are automatically parsed.
        this.accountId = response.data
        console.log(response.data)
        this.httpStatusCode = response.status
        this.httpStatusText = response.statusText
        this.headers = response.headers
        this.fullResponse = response
      })
        .catch(e => {
          console.log(e)
        })
    }
  }
}
</script>

<style scoped>

</style>

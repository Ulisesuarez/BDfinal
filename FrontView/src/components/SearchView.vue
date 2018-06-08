<template>
  <div>
    <v-container>
      <v-layout>
    <div  class="search-container" style="margin-top: 150px">
      <v-flex xs7>
      <v-text-field
        class=""
        name="search"
        label="SearchCare"
        v-model="searchInput"
        single-line
        light
        @keyup.enter="search"
        append-icon="search"/>
        </v-flex>
      <v-flex xs5>
        <span>
        <v-btn flat small @click=search>Find</v-btn>
    </span>
      </v-flex>
      <v-flex xs12>
      <span>
        <v-btn flat small  @click= "switchOptions">Advanced options</v-btn>
    </span>
      </v-flex>
    </div>
        <div v-if="showOptions">
          <v-flex xs12 style="max-width: 200px">
            <v-select
              :items="items"
              v-model="selectDocumentType"
              label="What are you looking for?"
              class="input-group--focused"
              item-value="text"
            ></v-select>
          </v-flex>
          <v-layout v-if="selectDocumentType ==='Users'">
          <v-flex xs12 style="max-width: 200px">
          Soy user
          </v-flex>
          </v-layout>
          <v-layout v-else-if="selectDocumentType ==='Files'">
            <v-flex xs12 style="max-width: 200px">
              Soy files
            </v-flex>
          </v-layout>
          <v-layout v-else-if="selectDocumentType ==='Groups'">
            <v-flex xs12 style="max-width: 200px">
              Soy grupos
            </v-flex>
          </v-layout>
        </div>
      </v-layout>
    </v-container>
    <v-layout style="min-width: 700px;min-height: 200px" row>
      <v-flex xs12 sm6 offset-sm3>
        <v-card style="min-width: 700px;min-height: 200px">
          <v-toolbar color="light-blue" dark>
            <v-toolbar-title>My files</v-toolbar-title>
            </v-toolbar>
          <v-list two-line>
            <v-list-tile v-for="item in response" :key="item.file_id" avatar>
              <v-list-tile-avatar>
                <v-icon :class="[item.iconClass]">note</v-icon>
              </v-list-tile-avatar>
              <v-list-tile-content>
                <v-list-tile-title v-tooltip="'Edit file name'" v-if="showName" id="fileName" @click="changeName">{{ item.name }}</v-list-tile-title>
                <v-text-field v-else id="setFileName" v-model="item.name" @keyup.enter="updateName(item)"></v-text-field>
                <v-list-tile-sub-title v-tooltip="'Edit file description'" v-if="showDescription" id="fileDescription" @click="changeDescription" v-html="item.headline"></v-list-tile-sub-title>
                <v-text-field v-else id="setFileDescription" v-model="item.description" @keyup.enter="updateDescription(item)"></v-text-field>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-btn icon ripple @click="alert(item.deleted)">
                  <v-icon color="grey lighten-1">arrow_downward</v-icon>
                </v-btn>
                <v-btn icon ripple @click="deleteFile(item.file_id)">
                  <v-icon color="grey lighten-1">delete</v-icon>
                </v-btn>
              </v-list-tile-action>
            </v-list-tile>
          </v-list>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>

export default {
  name: 'search-view',
  props: {accountId: Number},
  data () {
    return {
      showOptions: true,
      items: ['Documet', 'Directory', 'Groups'],
      selectDocumentType: null,
      searchInput: '',
      response: [],
      showName: true,
      showDescription: true,
      flagFirstEdit: true
    }
  },
  mounted () {
    console.log('search', this.accountId)
  },
  methods: {
    changeName () {
      this.showName = !this.showName
    },
    changeDescription () {
      this.showDescription = !this.showDescription
    },
    updateName (item) {
      this.$http.post(`http://localhost:8088/MedicalDB/updateName` +
      '?file_id=' + item.file_id.toString() +
      '&name=' + item.name).then(response => {
        // JSON responses are automatically parsed.
        this.user = response.data
        console.log(response.data)
        this.httpStatusCode = response.status
        this.httpStatusText = response.statusText
        this.headers = response.headers
        this.fullResponse = response
        console.log(this.headers)
      })
        .catch(e => {
          console.log(e)
        })
      this.showName = !this.showName
      this.sleep(3000)
      this.search()
    },
    updateDescription (item) {
      this.$http.post(`http://localhost:8088/MedicalDB/updateDescription` +
        '?file_id=' + item.file_id.toString() +
        '&description=' + item.description).then(response => {
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
      this.showDescription = !this.showDescription
      this.sleep(3000)
      this.search()
    },
    alert (value) {
      alert(value)
    },
    // Fetches posts when the component is created.
    switchOptions () {
      this.showOptions = !this.showOptions
    },
    deleteFile (fileId) {
      let self = this
      this.$http.post(`http://localhost:8088/MedicalDB/deleteFile?file_id=` + fileId.toString())
        .then(response => {
          // JSON responses are automatically parsed.
          console.log(response.data)
          this.httpStatusCode = response.status
          this.httpStatusText = response.statusText
          this.headers = response.headers
          self.search()
        })
        .catch(e => {
          console.log(e)
        })
    },
    search () {
      this.$http.post(`http://localhost:8088/MedicalDB/searchFile?query=` + this.searchInput +
        '&account_id=' + this.accountId)
        .then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data
          console.log(response.data)
          console.log(response.data[0])
          this.httpStatusCode = response.status
          this.httpStatusText = response.statusText
          this.headers = response.headers
          this.fullResponse = response
        })
        .catch(e => {
          console.log(e)
        })
    },
    sleep (ms) {
      return new Promise(resolve => setTimeout(resolve, ms))
    }
  }

}
</script>

<style>

</style>

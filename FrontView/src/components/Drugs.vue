<template>
  <div>
    <v-container>
      <v-layout>
        <div  class="search-container" style="margin-top: 150px">
          <v-flex xs7>
            <v-text-field
              class=""
              name="drug1"
              label="Drug Name"
              v-model="drug"
              single-line
              light
              @keyup.enter="search"
              append-icon="search"/>
          </v-flex>
          <v-flex xs7>
            <v-text-field
              class=""
              name="drug2"
              label="Symptom"
              v-model="sympthoms"
              single-line
              light
              @keyup.enter="search"
              append-icon="search"/>
          </v-flex>
          <v-flex xs5>
        <span>
        <v-btn flat small @click=updateName>Find</v-btn>
    </span>
          </v-flex>
          <v-flex xs12>
      <span>
        <v-btn flat small  @click= "switchOptions">Advanced options</v-btn>
    </span>
          </v-flex>
        </div>
      </v-layout>
    </v-container>
    <v-layout style="min-width: 700px;min-height: 200px" row>
      <v-flex xs12 sm6 offset-sm3>
        <v-card style="min-width: 700px;min-height: 200px">
          <v-toolbar color="light-blue" dark>
            <v-toolbar-title>Result Searchs: </v-toolbar-title>
          </v-toolbar>
          <v-list two-line>
            <v-list-tile v-if="Object.keys(objeto).length !== 0" v-for="item in objeto.results[0].patient.drug" :key="item" avatar>
              <v-list-tile-avatar>
                <v-icon>search</v-icon>
              </v-list-tile-avatar>
              <v-list-tile-content>
                <v-list-tile-title v-tooltip="'Drugs search'" v-if="showName" id="fileName" @click="changeName">{{ item.medicinalproduct }}</v-list-tile-title>
                <v-list-tile-sub-title v-tooltip="'description'" v-if="showDescription" id="fileDescription" @click="changeDescription" >{{item}}</v-list-tile-sub-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-btn icon ripple @click="alert('?')">
                  <v-icon color="grey lighten-1">remove_red_eye</v-icon>
                </v-btn>
                <v-btn icon ripple @click="deleteFile()">
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
  name: 'drugs',
  props: {accountId: Number},
  data () {
    return {
      showOptions: true,
      drug: '',
      sympthoms: '',
      selectDocumentType: null,
      searchInput: '',
      response: [],
      showName: true,
      showDescription: true,
      flagFirstEdit: true,
      searchs: [],
      searchId: 0,
      objeto: {
        results: [{patient: {
          drug: {
            medicinalproduct: '',
            openfda: {
              manufacturer_name: ['']
            }
          }
        }
        }]
      }
    }
  },
  beforeCreate () {
    this.searchs = []
    this.$http.get('http://localhost:8088/MedicalDB/searchlastid').then(response => {
      // JSON responses are automatically parsed.
      this.searchId = response.data
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
  mounted () {
    console.log('search', this.accountId)
  },
  updated () {
    this.searchs = []
    this.$http.get('http://localhost:8088/MedicalDB/searchlastid').then(response => {
      // JSON responses are automatically parsed.
      this.searchId = response.data
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
    changeName () {
      this.showName = !this.showName
    },
    changeDescription () {
      this.showDescription = !this.showDescription
    },
    updateName () {
      this.$http.get(`http://localhost:8088/MedicalDB/drugSearch` +
        `?search_id=` + (this.searchId + 1).toString() + `&user_id=` + this.accountId + `&search=patient.reaction.reactionmeddrapt:"` + this.sympthoms +
        `"+substance_name:` + this.drug + `&limit=10`).then(response => {
        // JSON responses are automatically parsed.
        this.report = response
        console.log(this.report.bodyText.toString().substr(5, this.report.bodyText.toString().search('},{')))
        let end = this.report.bodyText.toString().search('},{')
        console.log(this.report.bodyText.toString().substring(122570, end + 1))
        console.log(end)
        this.objeto = JSON.parse(this.report.bodyText.toString().substring(5, end + 1))
        console.log(this.objeto)
        this.httpStatusCode = response.status
        this.httpStatusText = response.statusText
        this.headers = response.headers
        this.fullResponse = response
        console.log(this.headers)
      })
        .catch(e => {
          console.log(e)
        })
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
      this.$http.get(`https://www.ehealthme.com/api/v1/drug-interaction/` + this.drug + `/` + this.sympthoms +
        '?account_id=' + this.accountId)
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

<style scoped>

</style>

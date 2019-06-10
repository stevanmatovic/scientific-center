<template>
    <div v-if="!submitted">
        <h6>Choose a journal: </h6>
        <select v-model="selected">
            <option disabled value="">Please select one</option>
            <option v-for="journal in journals" v-bind:value="{ name: journal.title, openaccess: journal.openAccess }">{{journal.title}}</option>
        </select>
        <button v-on:click="submit()">Submit</button>
    </div>
</template>
<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'JournalSelection',
        props: ["task"],
        data(){
          return {
              submitted: false,
              selected: '',
              journals: []
          }
        },
        mounted: function() {
            AXIOS
                .get('/journal/all')
                .then(response=>{
                    this.journals = response.data;
                })
        },
        methods: {
            submit(){
                if(!this.selected)
                    return;
                let body = {
                    "variables":{
                        "journalName" :{
                            "value" : this.selected.name,
                            "type" : "String"
                        },
                        "journalOpenAccess":{
                            "value" : this.selected.openaccess,
                            "type" : "Boolean"
                        }
                    }
                }
                AXIOS
                    .post('/rest/task/'+this.task.id+'/complete', body)
                    .then(response=>{
                        this.submitted = true;
                    })
            }
        }
    }
</script>
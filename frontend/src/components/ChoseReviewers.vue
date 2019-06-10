<template>
    <div v-if="!submitted">
        <select v-model="selected" multiple>
            <option v-for="reviewer in reviewers">{{reviewer.user.username}}</option>
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
                selected: [],
                reviewers: []
            }
        },
        mounted: function() {
            AXIOS
                .get('/camunda/'+ this.task.processInstanceId +'/reviewers')
                .then(response=>{
                    this.reviewers = response.data;
                })
        },
        methods: {
            submit(){
                AXIOS
                    .post('/camunda/'+this.task.id+'/reviewers', this.selected)
                    .then(response=>{
                        this.submitted = true;
                    })
            }
        }
    }
</script>
<template>
    <div v-if="!submitted">
        <h6>Subscribe to a {{journalName}} in order to publish a paper</h6>
        <button v-on:click="submit()">Subscribe</button>
    </div>
</template>
<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'Subscription',
        props: ["task"],
        data(){
            return {
                submitted: false,
                journalName : ''
            }
        },
        mounted: function() {
            AXIOS
                .get('/rest/process-instance/'+this.task.processInstanceId+'/variables',{})
                .then(response=>{
                    this.journalName = response.data.journalName.value;
                })
        },
        methods: {
            submit(){
                AXIOS
                    .post('/journal/'+this.journalName+'/subscribe/' + this.task.assignee)
                    .then(response=>{
                        AXIOS
                            .post('/rest/task/'+this.task.id+'/complete',{})
                            .then(response=>{
                                this.submitted = true;
                            })
                    })
            }
        }
    }
</script>
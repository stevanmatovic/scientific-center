<template>
    <div v-if="!submitted">
        <div v-for="review in reviews">
            <table border="1">
                <tr>
                    <td>Reviewer:</td>
                    <td>{{review.reviewer}}</td>
                </tr>
                <tr>
                    <td>Comment:</td>
                    <td>{{review.comment}}</td>
                </tr>
                <tr>
                    <td>Comment for editors:</td>
                    <td>{{review.commentForEditors}}</td>
                </tr>
                <tr>
                    <th>Opinion:</th>
                    <th>{{review.acceptance}}</th>
                </tr>
            </table>
            <br/>
        </div>
        <select v-model="selected">
            <option>Accept</option>
            <option>Reject</option>
            <option>Accept with big changes</option>
            <option>Accept with small changes</option>
            <option>Additional review</option>
        </select>
        <button :click="submit()">Submit</button>
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
                reviews: [],
                selected: ''
            }
        },
        mounted: function() {
            AXIOS
                .get('/rest/process-instance/'+ this.task.processInstanceId +'/variables')
                .then(response=>{
                    this.reviews = response.data.reviews.value;
                })
        },
        methods: {
            submit(){
                if(this.selected=='')
                    return
                let body = {
                    "variables":{
                        "editorDecision" :{
                            "value" : this.selected,
                            "type" : "String"
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
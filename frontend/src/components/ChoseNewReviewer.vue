<template>
    <div v-if="!submitted">
        <p>Reviewers that successfully reviewed the paper: {{successfulReviewers}} </p>
        <p>Reviewers that didn't reviewe the paper: {{unsuccessfulReviewers}} </p>
        <div v-if="potentialReviewers.length > 0">
            Chose substitude reviewers:
            <br/>
            <select v-model="selected" multiple>
                <option v-for="reviewer in potentialReviewers">{{reviewer}}</option>
            </select>
        </div>
        <div v-else>
            No more reviewers available!
        </div>
        <button v-on:click="submit()">Submit</button>
    </div>

</template>
<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'ChoseNewReviewer',
        props: ["task"],
        data(){
            return {
                submitted: false,
                selected: [],
                reviewers: [],
                allReviewers: [],
                successfulReviewers: [],
                unsuccessfulReviewers : [],
                potentialReviewers : []
            }
        },
        mounted: function() {
            AXIOS
                .get('/camunda/'+ this.task.processInstanceId +'/reviewers')
                .then(response=>{
                    this.allReviewers = response.data.map(a=>a.user.username);
                })
            AXIOS
                .get('/rest/process-instance/'+ this.task.processInstanceId +'/variables')
                .then(response=>{
                    this.reviewers = response.data.reviewers.value
                    this.previousReviewers = response.data.previousReviewers.value
                    this.successfulReviewers = response.data.reviews.value.map(a=>a.reviewer)
                    this.unsuccessfulReviewers = this.previousReviewers.filter(x => !this.successfulReviewers.includes(x))
                    this.potentialReviewers = this.allReviewers.filter(x => !this.previousReviewers.includes(x))
                })
        },
        methods: {
            submit(){
                AXIOS
                    .post('/camunda/'+this.task.id+'/newReviewers', this.selected)
                    .then(response=>{
                        this.submitted = true;
                    })
            }
        }
    }
</script>
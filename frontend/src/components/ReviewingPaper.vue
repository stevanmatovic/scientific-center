<template>
    <div v-if="!submitted">
        <h6>Review paper:</h6>
        <p><b>Title:</b> {{title}}</p>
        <p><b>Keywords:</b> {{keywords}}</p>
        <p><b>Abstract:</b> {{abstract}}</p>
        <p><a href="#" v-on:click="download()">{{filename}}</a></p>
        <br/>
        <textarea v-model="comment" placeholder="Comment" cols="64" rows="5"></textarea>
        <textarea v-model="commentForEditors" placeholder="Comment visible only to the jourunal editors" cols="64" rows="5"></textarea>
        <select v-model="acceptance">
            <option>Accept</option>
            <option>Accept with small changes</option>
            <option>Accept with big changes</option>
            <option>Reject</option>
        </select>
        <br/>
        <button v-on:click="submitReview()">Submit</button>
    </div>
</template>
<script>
    import {AXIOS} from './http-common'

    var fileDownload = require('js-file-download');
    export default {
        name: 'ReviewingPaper',
        props: ["task"],
        data(){
            return {
                submitted: false,
                title: '',
                abstract: '',
                filename: '',
                keywords: '',
                acceptance: '',
                commentForEditors: '',
                comment: ''
            }
        },
        mounted: function() {
            AXIOS
                .get('/rest/process-instance/'+this.task.processInstanceId+'/variables',{})
                .then(response=>{
                    this.keywords = response.data.keywords.value
                    this.abstract = response.data.abstract.value
                    this.title = response.data.title.value
                    this.filename = response.data.fileVariable.valueInfo.filename
                })
        },
        methods: {
            download(){
                AXIOS
                    .get('/rest/process-instance/'+ this.task.processInstanceId+'/variables/fileVariable/data',{
                        responseType: 'blob'
                    })
                    .then(response =>{
                        const url = window.URL.createObjectURL(new Blob([response.data]));
                        const link = document.createElement('a');
                        link.href = url;
                        link.setAttribute('download', this.filename);
                        document.body.appendChild(link);
                        link.click();
                    })


            },
            submitReview(){
                let body = {
                    "comment": this.comment,
                    "commentForEditors" : this.commentForEditors,
                    "acceptance" : this.acceptance,
                    "reviewer" : localStorage.getItem('username')
                }
                AXIOS
                    .post('/camunda/'+this.task.id+'/review', body)
                    .then(response=>{
                        this.submitted = true;
                    })
            }


        }
    }
</script>
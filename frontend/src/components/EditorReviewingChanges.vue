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
        <p><b>Title:</b> {{title}}</p>
        <p><b>Keywords:</b> {{keywords}}</p>
        <p><b>Abstract:</b> {{abstract}}</p>
        <p><a href="#" v-on:click="download()">{{filename}}</a></p>
        <button v-on:click="approve(true)">Approve changes</button>
        <button v-on:click="approve(false)">Don't approve changes</button>

    </div>
</template>
<script>
    import {AXIOS} from './http-common'

    var fileDownload = require('js-file-download');
    export default {
        name: 'EditorReviewingChanges',
        props: ["task"],
        data(){
            return {
                submitted: false,
                title: '',
                abstract: '',
                filename: '',
                keywords: '',
                reviews: []
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
                    this.reviews = response.data.reviews.value
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
            approve(approval){
                let body = {
                    "variables":{
                        "approvedChanges" :{
                            "value" : approval,
                            "type" : "Boolean"
                        }
                    }
                }
                AXIOS
                    .post('/rest/task/'+this.task.id+'/complete', body)
                    .then(response=>{
                        this.submitted = true;
                    })
            },
        }
    }
</script>
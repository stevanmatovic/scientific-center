<template>
    <div v-if="!submitted">
        <h6>Relevance check:</h6>
        <p><b>Title:</b> {{title}}</p>
        <p><b>Keywords:</b> {{keywords}}</p>
        <p><b>Abstract:</b> {{abstract}}</p>
        <p><a href="#" v-on:click="download()">{{filename}}</a></p>
        <div v-if="showRelevance">
            <button v-on:click="relevant()">Relevant</button>
            <button v-on:click="notRelevant()">Not relevant</button>
        </div>
        <div v-if="showFormatting">
            <textarea v-model="comment" placeholder="Comment" cols="64" rows="5"></textarea>
            <button v-on:click="formatting()">Needs formating</button>
            <button v-on:click="noFormatting()">Doesn't need formating</button>
        </div>
    </div>
</template>
<script>
    import {AXIOS} from './http-common'

    var fileDownload = require('js-file-download');
    export default {
        name: 'Subscription',
        props: ["task"],
        data(){
            return {
                submitted: false,
                showRelevance: true,
                showFormatting: false,
                comment: '',
                title: '',
                abstract: '',
                filename: '',
                keywords: ''
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
            notRelevant(){
                let body = {
                    "variables":{
                        "relevance" :{
                            "value" : "notRelevant",
                            "type" : "String"
                        }
                    }
                }
                AXIOS
                    .post('/rest/task/'+this.task.id+'/complete', body)
                    .then(response=>{
                        this.submitted = true;
                    })
            },
            relevant(){
                this.showRelevance = false;
                this.showFormatting = true;
            },
            formatting(){
                let body = {
                    "variables":{
                        "relevance" :{
                            "value" : "formatting",
                            "type" : "String"
                        },
                        "formattingComment" : {
                            "value" : this.comment,
                            "type" : "String"
                        }
                    }
                }
                AXIOS
                    .post('/rest/task/'+this.task.id+'/complete', body)
                    .then(response=>{
                        this.submitted = true;
                    })
            },
            noFormatting(){
                let body = {
                    "variables":{
                        "relevance" :{
                            "value" : "relevant",
                            "type" : "String"
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
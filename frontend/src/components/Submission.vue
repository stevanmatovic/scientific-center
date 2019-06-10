<template>
    <div class="container" v-if="!submitted">
        <form>
            <input v-model="paper.title" placeholder="Title" size="70">
            <br/><br/>
            <textarea v-model="paper.paperAbstract" placeholder="Abstract" cols="64" rows="10"></textarea>
            <br/><br/>
            <input v-model="paper.keywords" placeholder="Keywords" size="70">
            <br/><br/>
            <input v-model="paper.researchField" placeholder="Research field" size="70">
        </form>
        <br><br/>
        <div class="large-12 medium-12 small-12 cell">
            <label>File
                <input type="file" cam-variable-name="paper" cam-variable-type="File"
                       cam-max-filesize="5000000" id="file" ref="file" v-on:change="handleFileUpload()"/>
            </label>
            <button v-on:click="submit()">Submit</button>
        </div>
    </div>
</template>
<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'Submission',
        props: ["task"],
        data() {
            return {
                paper: {
                    title: '',
                    paperAbstract: '',
                    keywords: '',
                    researchField: '',
                },
                submitted : false
            }
        },
        methods: {
            handleFileUpload() {
                this.file = this.$refs.file.files[0];
            },
            submitFile() {
                let formData = new FormData();
                formData.append('file', this.file);
                AXIOS
                    .post('/camunda/paperFile/' + this.task.processInstanceId, formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(function () {
                    console.log("File uploaded successfully")
                    this.submitted = true
                }).catch(function () {
                    console.log('File not uploaded successfully')
                });
            },
            submit() {
                if (!this.file)
                    return;
                AXIOS
                    .post('/camunda/paperData/' + this.task.processInstanceId, this.paper)
                    .then(response => {
                        this.submitFile();
                    }).catch(e => {
                    console.log('File not uploaded successfully')
                    this.submitted=true
                    console.log(e)
                });
            },

        }
    }

</script>
<template>
    <div class="container">
        <form>
            <input v-model="paper.title" placeholder="Title" size="70">
            <br/><br/>
            <textarea v-model="paper.abstract" placeholder="Abstract" cols="64" rows="10"></textarea>
            <br/><br/>
            <input v-model="paper.keywords" placeholder="Keywords" size="70">
            <br/><br/>
            <input v-model="paper.researchField" placeholder="Research field" size="70">

        </form>
        <br><br/>
        <div class="large-12 medium-12 small-12 cell">
            <label>File
                <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
            </label>
            <button v-on:click="submit()">Submit</button>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from '../components/http-common'

    export default {
        name: 'PaperUpload',
        data() {
            return {
                paper: {
                    title: '',
                    abstract: '',
                    keywords: '',
                    researchField: '',
                },
            }
        },
        methods: {
            handleFileUpload() {
                this.file = this.$refs.file.files[0];
            },
            submitFile(paperId) {
                let formData = new FormData();
                formData.append('file', this.file);
                AXIOS
                    .post('/paper/' + paperId, formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(function () {
                    console.log("File uploaded successfully")
                    alert("File uploaded successfully")
                }).catch(function () {
                    console.log('File not uploaded successfully')
                });
            },
            submit() {
                AXIOS
                    .post('/paper', this.paper)
                    .then(response => {
                        let paperId = response.data;
                        this.submitFile(paperId);
                    }).catch(function () {
                });
            }
        }
    }
</script>

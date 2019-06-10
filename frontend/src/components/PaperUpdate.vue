<template>
    <div class="container" v-if="!submitted">
        <h6>Update paper details:</h6>
        <p v-if="task.name == 'Update paper'">Editor comment: {{comment}}</p>
        <div v-if="task.name == 'Author making changes'" v-for="review in reviews">
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
                    <th>Opinion:</th>
                    <th>{{review.acceptance}}</th>
                </tr>
            </table>
            <br/>
        </div>
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
        name: 'PaperUpdate',
        props: ["task"],
        data() {
            return {
                paper: {
                    title: '',
                    paperAbstract: '',
                    keywords: '',
                    researchField: '',

                },
                comment: '',
                submitted: false,
                reviews: []
            }
        },
        mounted: function () {
            AXIOS
                .get('/rest/process-instance/' + this.task.processInstanceId + '/variables', {})
                .then(response => {
                    this.paper.title = response.data.title.value
                    this.paper.paperAbstract = response.data.abstract.value
                    this.paper.keywords = response.data.keywords.value
                    this.paper.researchField = response.data.researchField.value
                    if(response.data.formattingComment)
                        this.comment = response.data.formattingComment.value
                    if(response.data.reviews)
                        this.reviews = response.data.reviews.value
                })
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
                    console.log(e)
                });
            },

        }
    }

</script>
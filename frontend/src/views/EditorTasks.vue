<template>
    <div class="container">
        <PaperRelevanceCheck v-for="task in relevanceCheck" :task="task"/>
        <ChoseReviewers v-for="task in choseReviewerTasks" :task="task"/>
        <ChoseNewReviewer v-for="task in choseNewReviewerTasks" :task="task"/>
        <EditorReviewing v-for="task in editorReviewingTasks" :task="task"/>
        <EditorReviewingChanges v-for="task in editorReviewingChangesTasks" :task="task"/>
    </div>
</template>
<script>
    import {AXIOS} from '../components/http-common'
    import PaperRelevanceCheck from '../components/PaperRelevanceCheck.vue'
    import ChoseReviewers from '../components/ChoseReviewers.vue'
    import ChoseNewReviewer from '../components/ChoseNewReviewer.vue'
    import EditorReviewing from '../components/EditorReviewing.vue'
    import EditorReviewingChanges from '../components/EditorReviewingChanges.vue'

    export default {
        components: {PaperRelevanceCheck, ChoseReviewers, ChoseNewReviewer, EditorReviewing, EditorReviewingChanges},
        data(){
            return{
                relevanceCheck: [],
                choseReviewerTasks: [],
                choseNewReviewerTasks: [],
                editorReviewingTasks: [],
                editorReviewingChangesTasks: [],
                tasks: [],
            }
        },
        name: 'EditorTasks',
        mounted: function(){
            let username = localStorage.getItem('username');
            AXIOS
                .get('/rest/task?assignee=' + username)
                .then(response=>{
                    this.choseReviewerTasks = response.data.filter(task=> task.name == "Chose reviewers")
                    this.relevanceCheck = response.data.filter(task=> task.name == "Paper Relevance Check")
                    this.choseNewReviewerTasks = response.data.filter(task=> task.name == "Chose new reviewer")
                    this.editorReviewingTasks = response.data.filter(task=> task.name == "Editor reviewing")
                    this.editorReviewingChangesTasks = response.data.filter(task=> task.name == "Editor reviewing changes")
                    this.tasks = response.data;
                })
        }
    }
</script>
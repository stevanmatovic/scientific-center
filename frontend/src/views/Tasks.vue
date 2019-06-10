<template>
    <div class="container">
        <JournalSelection v-for="task in selectJournalTasks" :task="task"/>
        <Subscription v-for="task in subscriptionTasks" :task="task"/>
        <Submission v-for="task in submitTasks" :task="task"/>
        <PaperUpdate v-for="task in paperUpdateTasks" :task="task"/>
        <ReviewingPaper v-for="task in reviewingPaperTasks" :task="task"/>
        <PaperUpdate v-for="task in authorMakingChangesTasks" :task="task"/>
    </div>
</template>
<script>
    import {AXIOS} from '../components/http-common'
    import JournalSelection from '../components/JournalSelection.vue'
    import Subscription from '../components/Subscription.vue'
    import Submission from '../components/Submission.vue'
    import PaperUpdate from '../components/PaperUpdate.vue'
    import ReviewingPaper from '../components/ReviewingPaper.vue'

    export default {
        components: {JournalSelection, Subscription, Submission, PaperUpdate, ReviewingPaper},
        data(){
          return{
              selectJournalTasks: [],
              subscriptionTasks: [],
              submitTasks: [],
              tasks: [],
              journals: [],
              paperUpdateTasks: [],
              reviewingPaperTasks: [],
              authorMakingChangesTasks: []
          }
        },
        name: 'Tasks',
        mounted: function(){
            let username = localStorage.getItem('username');
            AXIOS
                .get('/rest/task?assignee=' + username)
                .then(response=>{
                    this.selectJournalTasks = response.data.filter(task=> task.name == "Journal Selection");
                    this.subscriptionTasks = response.data.filter(task=> task.name == "Subscription");
                    this.submitTasks = response.data.filter(task=> task.name == "Paper Submission");
                    this.paperUpdateTasks = response.data.filter(task=> task.name == "Update paper");
                    this.reviewingPaperTasks = response.data.filter(task=> task.name == "Reviewing paper");
                    this.authorMakingChangesTasks = response.data.filter(task=> task.name == "Author making changes")
                    this.tasks = response.data;
                })

        }
    }
</script>
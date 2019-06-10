import Vue from 'vue'
import Router from 'vue-router'
import PaperUpload from './views/PaperUpload.vue'
import Login from './views/Login.vue'
import Search from './views/Search.vue'
import BooleanSearch from './views/BooleanSearch.vue'
import Tasks from './views/Tasks.vue'
import EditorTasks from './views/EditorTasks.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'home',
            component: Search
        },
        {
            path: '/submission',
            name: 'paper submission',
            component: PaperUpload
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/boolean',
            name: 'Boolean Search',
            component: BooleanSearch
        },
        {
            path: '/tasks',
            name: 'Tasks',
            component: Tasks
        },
        {
            path: '/editor-tasks',
            name: 'EditorTasks',
            component: EditorTasks
        },
    ]
})

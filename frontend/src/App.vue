<template>
    <div id="app">
        <nav class="navbar navbar-light bg-light justify-content-between">
            <a class="navbar-brand">Scientific central</a>
            <form class="form-inline">
                <a v-if="username != ''" v-on:click="startProcess()" class="nav-link" href="#">Submit paper</a>
                <a v-if="username != ''" v-on:click="logout()" class="nav-link" href="#">Logout</a>
                <router-link v-else to="/login">Login</router-link>
            </form>

        </nav>
        <div id="nav">
            <router-link to="/">Search</router-link>
            |
            <router-link to="/boolean">Boolean Search</router-link>
            |
            <router-link to="/tasks">Tasks</router-link>
            |
            <router-link to="/editor-tasks">Editing Tasks</router-link>
        </div>
        <router-view/>
    </div>
</template>
<script>
    import {AXIOS} from './components/http-common'
    import router from './router'
    import Vue from 'vue'
    export default {
        data(){
            return {
                get username(){
                    return localStorage.getItem('username') || '';
                },
                set username(value){
                    localStorage.setItem('username',value);
                }
            }
        },
        methods :{
            logout(){
                AXIOS
                    .delete('/auth', this.credentials)
                    .then(function(response){
                        localStorage.setItem('username','');
                        this.username = '';
                        router.push('')
                    }).catch(function (e) {
                    console.log(e)
                });
            },
            startProcess(){
                let body = {
                    "variables" :{
                        "author" : {
                            "value" : localStorage.getItem('username'),
                            "type" : "String"
                        }
                    }
                }
                AXIOS
                    .post('/rest/process-definition/key/submit_paper/start',body)
                    .then(response=>{
                        router.push('tasks');
                    }).catch(e=>{
                        console.log(e);
                })
            }
        }
    }
</script>
<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #nav {
        padding: 30px;
    }

    #nav a {
        font-weight: bold;
        color: #2c3e50;
    }

    #nav a.router-link-exact-active {
        color: #42b983;
    }
</style>

<template>
    <div class="container">
        <form>
            <input v-model="credentials.username" placeholder="Username" size="70">
            <br/><br/>
            <input v-model="credentials.password" placeholder="Password" size="70">
        </form>
        <br><br/>
        <button v-on:click="login()">Log in</button>
    </div>
</template>

<script>
    import {AXIOS} from '../components/http-common'
    import router from '../router'

    export default {
        name: 'Login',
        data() {
            return {
                credentials: {
                    username: '',
                    password: ''
                },
            }
        },
        methods: {
            login() {
                AXIOS
                    .post('/auth', this.credentials)
                    .then(function (response) {
                        localStorage.setItem('username',response.data.username)
                        router.push('')

                    }).catch(function (e) {
                    console.log('Failure')
                    console.log(e)
                });
            }
        }
    }
</script>

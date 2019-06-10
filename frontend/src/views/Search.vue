<template>
    <div class="container">
        <input v-model="query.value" size="80">
        <button v-on:click="search()">Search!</button>
        <br/><br/>
        <input v-model="query.field" type="radio" name="field" value="content" checked="checked"> Content
        <input v-model="query.field" type="radio" name="field" value="title"> Title
        <input v-model="query.field" type="radio" name="field" value="journalName"> Journal
        <input v-model="query.field" type="radio" name="field" value="author"> Author
        <input v-model="query.field" type="radio" name="field" value="keywords"> Keywords
        <input v-model="query.field" type="radio" name="field" value="researchField"> Research Field
        <br/>
        <SearchHit v-for="hit in result" :hit="hit" />

    </div>
</template>

<script>
    import {AXIOS} from '../components/http-common'
    import SearchHit from '../components/SearchHit.vue'

    export default {
        name: 'Search',
        components: {SearchHit},
        data() {
            return {
                query: {
                    field: 'content',
                    value: ''
                },
                result: []
            }
        },
        methods: {
            search() {
                let url = '/search/term'
                if (this.query.value.startsWith("\"") && this.query.value.endsWith("\""))
                    url = '/search/phrase';
                AXIOS
                    .post(url, this.query)
                    .then(response => {
                        console.log(response.data);
                        this.result = response.data;
                    }).catch(e => {
                    console.log(e);
                })
            }
        }
    }
</script>

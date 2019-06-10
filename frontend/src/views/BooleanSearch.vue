<template>
    <div class="container">
        <input v-model="query.field1" type="radio" name="field1" value="content" checked="checked"> Content
        <input v-model="query.field1" type="radio" name="field1" value="title"> Title
        <input v-model="query.field1" type="radio" name="field1" value="journalName"> Journal
        <input v-model="query.field1" type="radio" name="field1" value="author"> Author
        <input v-model="query.field1" type="radio" name="field1" value="keywords"> Keywords
        <input v-model="query.field1" type="radio" name="field1" value="researchField"> Research Field
        <br/><br/>
        <input v-model="query.value1"  size="60">

        <br/><br/>
        <select v-model="query.operation">
            <option value="AND">AND</option>
            <option value="OR">OR</option>
            <option value="NOT">NOT</option>
        </select>
        <br/><br/>


        <input v-model="query.value2"  size="60">
        <br/><br/>
        <input v-model="query.field2" type="radio" name="field2" value="content" checked="checked"> Content
        <input v-model="query.field2" type="radio" name="field2" value="title"> Title
        <input v-model="query.field2" type="radio" name="field2" value="journalName"> Journal
        <input v-model="query.field2" type="radio" name="field2" value="author"> Author
        <input v-model="query.field2" type="radio" name="field2" value="keywords"> Keywords
        <input v-model="query.field2" type="radio" name="field2" value="researchField"> Research Field
        <br/>
        <button v-on:click="search()">Search!</button>

        <SearchHit v-for="hit in result" :hit="hit"/>

    </div>
</template>
<script>
    import {AXIOS} from '../components/http-common'
    import SearchHit from '../components/SearchHit.vue'

    export default {
        name: 'BooleanSearch',
        components: {SearchHit},
        data() {
            return {
                query: {
                    field1: 'content',
                    value1: '',
                    field2: 'content',
                    value2: '',
                    operation: 'AND'
                },
                result: []
            }
        },
        methods: {
            search() {
                AXIOS
                    .post('/search/boolean', this.query)
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
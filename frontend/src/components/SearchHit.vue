<template>
    <div  class="search-hit">
        <h3>{{hit.title}}</h3>
        <b>Download: </b> <a href="#" v-on:click="download(hit.filename)">{{hit.filename}}</a>
        <p>{{hit.highlight}}</p>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'

    export default {
        name: 'SearchHit',
        props: ["hit"],
        methods: {
            download(filename) {
                AXIOS
                    .get('/paper/download/' + filename, {responseType: 'blob'})
                    .then((response) => {
                        const url = window.URL.createObjectURL(new Blob([response.data]));
                        const link = document.createElement('a');
                        link.href = url;
                        link.setAttribute('download', filename); //or any other extension
                        document.body.appendChild(link);
                        link.click();
                    });
            }
        }
    }
</script>

<style>

    .search-hit {
        text-align: left;
        border: 10px solid #fff;
        border-bottom: 15px solid #fff;
        -webkit-box-shadow: 3px 3px 3px #777;
        -moz-box-shadow: 3px 3px 3px #777;
        box-shadow: 3px 3px 3px #777;

        -webkit-transition: margin 0.2s ease-out;
        -moz-transition: margin 0.2s ease-out;
        -o-transition: margin 0.2s ease-out;
        transition: margin 0.2s ease-out;
    }
    em{
        background-color: red;
    }
</style>
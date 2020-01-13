<template>
    <div>
        <article v-for="post in viewPosts" :key="post.id">
            <div class="title">
                <a href="#page=PostView" @click="viewPost(post.id)">{{post.title}}</a>
            </div>
            <div class="information">
                By {{post.user.login}}
            </div>
            <div class="body">
                {{post.text}}
            </div>
            Commentaries now: <a href="#page=PostView" @click="viewPost(post.id)">{{counter(post.id)}}</a>
        </article>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "Index",
        props: ["posts"],
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        },
        methods: {
            viewPost: function (postId) {
                this.$root.$emit("onViewPost", postId);
            },
            counter: function (postId) {
                return this.comments.filter(u => u.post.id === postId).length
            }
        },
        data:  function() {
            return {
                users: {},
                comments: []
            }
        },
        beforeMount: function () {
            axios.get('/api/1/users').then((response) => {
                this.users = response.data;
            });
            axios.get('/api/1/allcomments').then((response) => {
                this.comments = response.data;
            });
        },
    }
</script>

<style scoped>

</style>

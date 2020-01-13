<template>
    <article v-if="post !== undefined" >
        <div class="title">{{post.title}}</div>
        <div class="information">By {{users[post.userId].login}}</div>
        <div class="body">{{post.text}}</div>
        <div v-for="comment in viewComments" :key="comment.id">
            <div class="information_comment">By {{users[comment.userId].login}}</div>
            <div class="body_comment" >{{comment.text}}</div>
        </div>

    </article>
</template>

<script>
    export default {
        name: "PostView",
        props: ['post', 'users', 'comments'],
        computed: {
            viewComments: function () {
                return Object.values(this.comments).filter(u => u.postId === this.post.id);
            }
        }
    }
</script>

<style scoped>
</style>
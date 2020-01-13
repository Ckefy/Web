<template>
    <div>
        <article v-if="post !== undefined" >
            <div class="title">{{post.title}}</div>
            <div class="information">By {{post.user.login}}</div>
            <div class="body">{{post.text}}</div>
            <div v-for="comment in loadComments()" :key="comment.id">
                <div class="information_comment">By {{comment.user_com.login}}</div>
                <div class="body_comment" >{{comment.text}}</div>
            </div>
        </article>
        <div class="form-box">
            <div class="header">New comment</div>
            <div class="body">
                <form method="post" @submit.prevent="onAdd">
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" rows="10" name="text" v-model="text"></textarea>
                        </div>
                        <div class="error">{{error}}</div>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Add">
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "PostView",
        props: ['post'],
        data: function() {
            return {
                comments: [],
                error: "",
                text: "",
            }
        },
        methods: {
            loadComments: function(){
                axios.get("/api/1/comments", {params: {postId: this.post.id}}).then(comments => this.comments = comments["data"]);
                return this.comments;
            },
            onAdd: function () {
                this.$root.$emit("onAddComment", this.post, this.text);
                this.text="";
            }
        }, beforeMount() {
            this.text = this.error = "";
            this.$root.$on("onAddCommentValidationError", error => this.error = error);
        }
    }
</script>

<style scoped>
</style>
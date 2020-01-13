<template>
    <section>
        <div class="header">
            By {{post.user.name}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a href="#page=PostView" @click="viewPost(post.id)">View all</a>
        </div>
    </section>

</template>

<script>
    export default {
        props: ['post'],
        name: "SidebarPost",
        methods: {
            viewPost: function (postId) {
                this.$root.$emit("onViewPost", postId);
            }
        }
    }
</script>

<style scoped>

</style>

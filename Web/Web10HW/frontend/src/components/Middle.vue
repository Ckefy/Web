<template>
    <div class="middle">
        <aside>
            <SidebarPost v-for="post in viewPosts" :post="post" :key="post.id"/>
        </aside>
        <main>
            <Index :posts="posts" v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'"/>
            <AddPost v-if="page === 'AddPost'"/>
            <PostView :post="curPost" v-if="page === 'PostView'"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import AddPost from './middle/AddPost';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import SidebarPost from './SidebarPost';
    import Users from './middle/Users';
    import PostView from './middle/PostView';

    export default {
        name: "Middle",
        props: ["posts"],
        data: function () {
            return {
                page: "Index",
                curPost: undefined
            }
        },
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            }
        },
        components: {
            Index,
            PostView,
            AddPost,
            Enter,
            Register,
            SidebarPost,
            Users
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
            this.$root.$on("onViewPost", (postId) => {
                this.page = "PostView";
                this.curPost = this.posts.filter(u => u.id === postId)[0];
            });
        }
    }
</script>

<style scoped>

</style>

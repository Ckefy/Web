<template>
    <!--suppress HtmlUnknownTag -->
    <body id="app">
    <Header :user="user"/>
    <Middle :posts="posts"/>
    <Footer/>
    </body>
</template>

<script>
    import Header from './components/Header'
    import Middle from './components/Middle'
    import Footer from './components/Footer'
    import axios from 'axios'

    export default {
        name: 'app',
        data: function () {
            return {
                user: null,
                posts: []
            }
        },
        components: {
            Header,
            Middle,
            Footer
        }, beforeCreate() {
            axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);

            this.$root.$on("onLogout", () => {
                localStorage.removeItem("jwt");
                this.user = null;
            });

            this.$root.$on("onJwt", (jwt, enter) => {
                axios.defaults.headers = {
                    Authorization: "Bearer " + jwt
                };

                axios.get("/api/1/users/authorized").then(response => {
                    this.user = response.data;
                    if (enter) {
                        this.$root.$emit("onEnterSuccess");
                    }
                });
            });

            this.$root.$on("onEnter", (login, password) => {
                axios.post("/api/1/jwt", {
                    login: login,
                    password: password
                }).then(response => {
                    localStorage.setItem("jwt", response.data);
                    this.$root.$emit("onJwt", response.data, true);
                }).catch(error => {
                    this.$root.$emit("onEnterValidationError", error.response.data);
                });
            });
            this.$root.$on("onRegister", (login, name, password) => {
                axios.post("/api/1/users", {
                    login: login, name: name, password: password,
                }).then(() => {
                    axios.post("/api/1/jwt", {
                            login: login,
                            password: password
                        }).then(response => {
                            localStorage.setItem("jwt", response.data);
                            this.$root.$emit("onJwt", response.data, true);
                        })
                }).catch(error => {
                    this.$root.$emit("onRegisterValidationError", error.response.data);
                });
            });
            this.$root.$on("onAddPost", (title, text) => {
                if (this.user) {
                    axios.post("/api/1/posts", {
                        title: title, text: text, author: this.user.id,
                    }).then(() => {
                        axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);
                        this.$root.$emit("onAddPostSuccess");
                    }).catch(error => {
                        this.$root.$emit("onAddPostValidationError", error.response.data);
                    });
                } else {
                    this.$root.$emit("onAddPostValidationError", "No access");
                }
            });

            this.$root.$on("onAddComment", (post, text) => {
                if (this.user) {
                    axios.post("/api/1/posts/comment", {
                        text: text, author: this.user.id, post: post,
                    }).then(() => {
                        this.$root.$emit("onAddCommentSuccess", post.id);
                    }).catch(error => {
                        this.$root.$emit("onAddCommentValidationError", error.response.data);
                    });
                } else {
                    this.$root.$emit("onAddCommentValidationError", "No access");
                }
            });

        }, beforeMount() {
            if (localStorage.getItem("jwt") && !this.user) {
                this.$root.$emit("onJwt", localStorage.getItem("jwt"), true);
            }
        }
    }
</script>

<style>
</style>

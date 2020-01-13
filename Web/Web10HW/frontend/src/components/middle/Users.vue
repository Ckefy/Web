<template>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="user in users" :key="user.id">
                <td>{{user.id}}</td>
                <td>{{user.login}}</td>
                <td>{{user.name}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "Users",
        data:  function() {
            return {
                users: {}
            }
        },
        beforeMount: function () {
            axios.get('/api/1/users').then((response) => {
                this.users = response.data;
            });
        },
    }
</script>

<style scoped>
</style>
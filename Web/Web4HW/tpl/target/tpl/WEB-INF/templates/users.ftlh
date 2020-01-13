<#import "common.ftl" as c/>

<@c.page>
<div class="datatable">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Handle</th>
        </tr>
        </thead>
        <tbody>
        <#list users as u>
        <tr>
            <td>${u.id}</td>
            <td><@c.userlink user=u nameOnly=false/></td>
            <td>${u.handle}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
</@c.page>

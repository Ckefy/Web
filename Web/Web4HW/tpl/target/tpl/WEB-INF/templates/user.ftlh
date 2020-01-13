<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.String" -->
<#import "common.ftl" as c/>

<@c.page>
        <div class="profile-box">
        <#if handle??>
            <#assign viewedUser=c.findBy(users, "handle", handle)!/>
        <#else>
            <#assign viewedUser=c.findBy(users, "id", user_id)!/>
        </#if>
        <#if viewedUser.id??>
            <div>
                <img src="/img/user.jpg" alt="No Image"/>
            </div>
            <div class="link">
                <@c.userlink user=viewedUser nameOnly=false/>
            </div>
            <ul>
                <li>id: ${viewedUser.id}</li>
                <li>handle: ${viewedUser.handle}</li>
                <li>name: ${viewedUser.name}</li>
                <#assign counter=0>
                <#list posts as p>
                    <#if p.user_Id==viewedUser.id>
                        <#assign counter++>
                    </#if>
                </#list>
                <li>posts: <a href="/posts?user_id=${viewedUser.id}">${counter}</a></li>
            </ul>
        </div>
        <div class="scroll">
            <#assign prevUser=c.findPrevUser(viewedUser.handle)!/>
            <#if prevUser.id??>
                <a href="/user?handle=${prevUser.handle}">⟵</a>
            <#else>
                <span>⟵</span>
            </#if>

            <#assign nextUser=c.findNextUser(viewedUser.handle)!/>
            <#if nextUser.id??>
                <a href="/user?handle=${nextUser.handle}">⟶</a>
            <#else>
                <span>⟶</span>
            </#if>
        </div>
        <#else>
            <h1>No such user</h1>
        </#if>
</@c.page>
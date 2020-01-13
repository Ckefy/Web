<#macro header>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            <@userlink user=user nameOnly=true/>
            |
            <a href="#">Logout</a>
        <#else>
            <a href="#">Enter</a>
            |
            <a href="#">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <#list points as p>
                <#if currentTopic?? && p == currentTopic>
                    <li class="currentTopic"><a href=${p.link}>${p.name}</a></li>
                <#else>
                    <li><a href=${p.link}>${p.name}</a></li>
                </#if>
            </#list>
        </ul>
    </nav>
</header>
</#macro>

<#macro sidebar>
<aside>
    <#list posts as p>
        <section>
            <div class="header">
                Post #${p.id}
            </div>
            <div class="body">
                <#if (p.text?length>=250)>
                    ${p.text?substring(0, 250)}...
                <#else>
                    ${p.text}
                </#if>
            </div>
            <div class="footer">
                <a href="/post?post_id=${p.id}">View all</a>
            </div>
        </section>
    </#list>
</aside>
</#macro>

<#macro footer>
<footer>
    <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
</footer>
</#macro>

<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
</head>
<body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
</body>
</html>
</#macro>

<#macro userlink user nameOnly>
    <#if !nameOnly>
        <a style="color: ${user.color}; text-decoration: none; font-weight: bold" href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    </#if>
</#macro>

<#macro printPosts isLong array>
    <#list array?reverse as p>
        <#assign curUser=findBy(users, "id", p.user_Id)/>
    <article>
        <div class="title">${p.title}</div>
        <div class="information">By ${curUser.name}. Post # ${p.id}</div>
        <div class="body">
            <#if !isLong && (p.text?length>250)>
                ${p.text?substring(0, 250)}...
            <#else>
                ${p.text}
            </#if>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
    </#list>
</#macro>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findNextUser id>
    <#list users as item>
        <#if !item?is_last>
            <#if item["handle"]==id>
                <#assign ind=item?index/>
                <#return users[ind+1]>
            </#if>
        </#if>
    </#list>
</#function>

<#function findPrevUser id>
    <#list users as item>
        <#if !item?is_first>
            <#if item["handle"]==id>
                <#assign ind=item?index/>
                <#return users[ind-1]>
            </#if>
        </#if>
    </#list>
</#function>
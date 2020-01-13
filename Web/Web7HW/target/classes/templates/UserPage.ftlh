<#import "commons.ftlh" as c>

<@c.page>
<div class="informuser">
     <#if userData??>
         <div class="caption">${userData.login}</div>
         <div class="inform">
             <p>Id user : ${userData.id}</p>
             <p>Creation time : ${userData.creationTime}</>
         </div>
     <#else>
        <div class="caption">No such user</div>
     </#if>
</div>
</@c.page>
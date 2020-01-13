<#-- @ftlvariable name="post_id" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.String" -->

<#import "common.ftl" as c/>

<@c.page>
<#assign array=[]/>
    <#list posts as p>
        <#if p.user_Id == user_id>
            <#assign array=array+[p]/>
        </#if>
    </#list>
    <@c.printPosts isLong=true array=array/>
</@c.page>
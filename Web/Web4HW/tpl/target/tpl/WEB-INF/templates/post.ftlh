<#-- @ftlvariable name="post_id" type="java.lang.String" -->
<#import "common.ftl" as c/>

<@c.page>
    <#assign curPost=c.findBy(posts, "id", post_id)!/>
    <#if curPost??>
        <@c.printPosts isLong=true array=[curPost]/>
    </#if>
</@c.page>
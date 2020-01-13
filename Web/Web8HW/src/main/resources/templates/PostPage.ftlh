<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
<div>
    <#if post??>
        <article>
            <div class="information">By ${post.user.login}</div>
            <div class="body">${post.text}</div>
            <h3>Tags: </h3>
            <#if post.tags?size != 0>
                <div class="tags">
                    <#list post.tags as tag><div>#${tag.name}</div> </#list>
                </div>
            <#else>
                No tags :(
            </#if>
            <h2>Comments: </h2>
            <#if post.comments?size == 0>
                No comments :(
            <#else>
                <#list post.comments as com>
                    <div style="padding-left: 2em">
                        <article>
                            <div class="information">By ${com.user.login}</div>
                            <div class="body">${com.text}</div>
                        </article>
                    </div>
                </#list>
            </#if>
        </article>
        <#if user??>
        <div class="form-box">
            <div class="header">Add comment</div>
            <div class="body">
                <form method="post" action="">
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text"></textarea>
                        </div>
                        <@c.error 'comment.text'/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Add">
                    </div>
                </form>
            </div>
        </div>
        </#if>
    <#else>
    No such notice :(
    </#if>
</div>
</@c.page>

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
<div class="add-notice form-box">
    <div class="header">Add new notice</div>
    <div class="body">
        <form method="post" action="/notice/add">
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text"></textarea>
                </div>
                <@c.error "notice.text"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Add">
            </div>
        </form>
    </div>
</div>
</@c.page>
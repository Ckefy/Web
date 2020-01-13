<#import "commons.ftlh" as c>

<@c.page>
<div class="form-box">
    <div class="header">Create article</div>
    <div class="body">
        <form method="post" action="/article">
            <input type="hidden" name="action" value="createArticle">
            <div class="field">
                <div class="name">
                    <label for="name">Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title"/>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="message">Text</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text"></textarea>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
            <div class="error">
                ${error!}
            </div>
        </form>
    </div>
</div>
<script>
    $(function () {
        $(".form-box form").submit(function () {
            var title = $(this).find("input[name='title']").val();
            var text = $(this).find("textarea[name='text']").val();
            var $error = $(this).find(".error");
            $.ajax({
                type: "POST",
                url: "",
                dataType: "json",
                data: {
                    action: "createArticle",
                    title: title,
                    text: text
                },
                success: function (response) {
                    if (response["error"]) {
                        $error.text(response["error"]);
                    } else {
                        location.href = response["redirect"];
                    }
                }
            });
            return false;
        });
    })
</script>
</@c.page>
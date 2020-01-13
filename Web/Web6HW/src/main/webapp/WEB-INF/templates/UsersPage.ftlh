<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.isAdmin()><th>Admin</th></#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                </tr>
            </template>
            <template class="buttonAdmin">
                <td>
                    <div class="show-hide-button">
                        <div class="id" style="display: none"></div>
                        <button></button>
                    </div>
                </td>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function(response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        var $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);
                        if (response["user"]!==undefined && response["user"]["admin"]==="true"){
                            var $tr2 = $($(".buttonAdmin").prop("content")).clone();
                            $tr2.find(".id").text(user["id"]);
                            if(user["admin"]==="true"){
                                $tr2.find("button").text("Yes");
                            } else{
                                $tr2.find("button").text("No");
                            }
                            $tr.find("tr").append($tr2);
                        }
                        $(".users tbody").append($tr);
                    });
                    $(".show-hide-button").click(function () {
                        var id = $(this).find('.id').text();
                        var value = $(this).find('button').text();
                        if (value === 'No') {
                            $(this).find('button').text('Yes');
                        } else {
                            $(this).find('button').text('No');
                        }
                        $.ajax({
                            type: "POST",
                            url: "",
                            dataType: "json",
                            data: {
                                action: "update",
                                id: id,
                                type: value
                            },
                            success: function(response) {
                                if (response["error"])
                                    notify(response["error"]);
                            }
                        });
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>
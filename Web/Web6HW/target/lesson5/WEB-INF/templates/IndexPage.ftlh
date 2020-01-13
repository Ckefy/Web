<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!

   <div class="articles datatable">
       <div class="caption">Article</div>
       <table>
           <thead>
           <tr>
               <th>Title</th>
               <th>Text</th>
               <th>Creation Time</th>
           </tr>
           </thead>
           <tbody>
           <template>
               <tr>
                   <td class="title"></td>
                   <td class="text"></td>
                   <td class="information"></td>
               </tr>
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
            var $noData = $(".articles .noData");
            $noData.find("td").text("Loading...");
            $.ajax({
                type: "POST",
                url: "",
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function (response) {

                    if (response["articles"] != null && response["articles"].length > 0) {

                        response["articles"].forEach(function (article) {

                            $noData.hide();
                            var $tr = $($(".articles template").prop("content")).clone();
                            $tr.find(".title").text(article.title);
                            $tr.find(".text").text(article.text);
                            $tr.find(".information").html('At ' + article.creationTime);
                            $(".articles tbody").append($tr);
                        });
                    } else {

                        $noData.find("td").text("No data");
                    }
                }
            });
        })
    </script>
</@c.page>

window.notify = function(message) {
    $.notify(message, {position: "bottom right", className: "success"})
};

ajax = function (action, login, password, $error) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: {
            action: action,
            login: login,
            password: password
        },
        success: function (response) {
            if (response["error"]) {
                $error.text(response["error"]);
            } else {
                location.href = response["redirect"];
            }
        }
    })
};
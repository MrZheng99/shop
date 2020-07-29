function getAdmName(){
    $.ajax({
        type: 'GET',
        url: "/manager/name",
        success: function (msg) {
            if (msg.success) {
                $("#admName").text("当前用户："+msg.data);
            } else {
                $("#admName").after("<a class='text-primary' href='/index'>"+msg.msg+"</a>")

            }
        }

    });
}
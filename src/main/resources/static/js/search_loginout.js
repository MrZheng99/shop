function addSearch() {
    $("#inputSearch").keyup(function (e) {
        if (e.keyCode == 13) {
            let goodsName = $(this).val().trim();
            if (goodsName == null || goodsName == "") {
                alert("请输入需要搜索的商品");
                return;
            }
            $.ajax({
                type: 'GET',
                url: "/home/saveKeyWord/" + goodsName,
                success: function (msg) {
                    if (msg.success) {
                        window.location.href = "/search";
                    } else {
                        alert("网络错误,稍后重试");
                    }
                }

            });
        }
    });
}
function loginOut() {
   //let flag =  confirm("客官请三思啊！！！");
   //if(flag){
       $.ajax({url:"/loginOut",success:function (data) {
              location.href = "/index";
       }})
   //}
}

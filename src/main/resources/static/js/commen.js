//跳转到浏览商品详情界面
function goShopping(goodsId) {
    window.location.href = "/shopping/" + goodsId;
}
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
//分页
function loadPageNumber(value,op) {
    let url = null;
    if(op=="type"){
        url="/goods/number/type/" + value;
    }else if(op=="name"){
        url="/goods/number/name/" + value;
    }
    $.ajax({
        type: 'GET',
        url: url,
        success: function (msg) {
            if (msg.success) {
                let pages = Math.ceil(msg.data / 8);
                console.log(pages);
                $("#pagination").html("");
                $("#pagination").showPage(pages,value,op);
            } else {
               alert("加载失败");
            }
        }

    });
}

/**
 * 获取商品并显示
 * @param value
 * @param op 是按类别查找还是姓名
 */
function getGoods(value,op) {
    if(value==null||value==""){
        alert("请输入需要搜索的商品名或者类别");
        return;
    }
    console.log(op);
    loadPageNumber(value,op);

    let url = null;
    if(op=="type"){
        url="/goods/type/" + value;
    }else if(op=="name"){
        url="/goods/name/" + value;
    }
    $.ajax({
        type: 'GET',
        url: url,
        success: function (msg) {
            if (msg.success) {
                $(".content").empty();
                let ele;
                for (let i = 0, len = msg.data.length; i < len; i++) {
                    ele = msg.data[i];
                    let d = "<div class='disp'><img src='../" + ele["images"].split(",")[0] + "'><span class='description' onclick='goShopping(" + ele["id"] + ")'>" + ele["name"] +  "</span> <span class='price'>" + "￥" + ele["price"] + "</span><button id='btn" + ele["id"] + "' onclick='joinShoppingCart(" + ele["id"] + ")'>加入购物车</button></div>"
                    $(".content").append(d);
                }
            }
        }

    });
}

/**
 * 加入购物车
 * @param goodsId
 */
function joinShoppingCart(goodsId) {
    console.log(goodsId)
    $.ajax({
        type: 'GET',
        url: "../shopping/addGoods2ShoppingCart/" + goodsId,
        success: function (msg) {
            if (msg.success) {
                alert("添加成功");
                $("#btnJoinShoppingCart").text("已在购物车");
                $("#btnJoinShoppingCart").attr("disabled", false);

            } else {
                if (msg.data === "未登录") {
                    alert("请登录!!!");
                    window.location.href = "../index";
                }

                if (msg.data === "已在购物车之中") {
                    alert("已在购物车之中!!!");
                    $("#btnJoinShoppingCart").val("已在购物车");
                    $("#btnJoinShoppingCart").attr("disabled", true);
                }
                if (msg.data === "加入购物车失败") {
                    alert("加入购物车失败!!!");
                }
            }
        }

    });
}

/**
 * 获取用户名并显示
 */
function getUserName(){
    $.ajax({
        type: 'GET',
        url: "/user/name",
        success: function (msg) {
            if (msg.success) {
                $("#userName").text(msg.data);
            } else {
                $("#userName").text(msg.msg);
            }
        }

    });
}
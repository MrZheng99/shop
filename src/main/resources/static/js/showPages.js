/**
 * 显示页码
 * @param count
 * @param categroy
 */
$.fn.showPage = function(count,value,op) {
    var str = "<a href='javascript:void(0)' class='current'>1</a>";
    for (var i = 2; i <= count; i++) {
        str += "<a href='javascript:void(0)'>" + i + "</a>";
    }

    var currentObj = $(this);
    currentObj.append($(str));

    var objs = currentObj.children("a");
    if (count > 10) {
        objs.slice(5, count - 5).addClass("show");
        objs.eq(4).after($("<span>...</span>"));
    }
    objs.unbind("click");
    objs.click(function() {
        let obj = this;
        $("#pagination>a").removeClass("current"); //移除超链接上的选中样式
        //当前对象选中
        let num = $(obj).index() + 1;
        // findByPage(num,obj);//调用外部函数按页查询
        refreshGoods(value ,op,(num-1));
        $(obj).addClass("current"); //给当前点击的这个页码添加样式
        if (num < 5 || num > pageCount - 5) {
            return;
        }
        if (pageCount >= 10) {
            $("#pagination>span").remove(); //移除这个分页栏中的span标签
            var nextObj = $(obj).nextAll(); //获取当前点击的这个页码后面的所有页码对象
            var nextSize = nextObj.length;
            if (num >= 5) {
                nextObj.slice(2, nextSize - 5).addClass("show");
                nextObj.slice(0, 2).removeClass("show");
                var preObj = $(obj).prevAll();
                var prvSize = preObj.length;
                preObj.removeClass("show");
                if (prvSize >= 8) {
                    preObj.slice(2, num - 6).addClass("show");
                    //补上 ...
                    if ($(obj).prevAll(".show").length > 0) { //说明当前这个页码前面有隐藏的页码
                        $(obj).prev().prev().before($("<span>...</span>"));
                    }
                    if ($(obj).nextAll(".show").length > 0) { //说明当前这个页码后面有隐藏的页码
                        $(obj).next().next().after($("<span>...</span>"));
                    }
                }
            }

        }
    })
}
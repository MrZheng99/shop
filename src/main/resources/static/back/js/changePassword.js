$(function (){
    $(".passinbut").click(function(){
        var password=$(".aginpassword").val();
        var ypassword=$("#inputPassword").val();
        var npassword=$(".newspassword").val();
        if(password=='' || password==null && ypassword=='' || ypassword==null && npassword=='' || npassword==null){
            layer.msg('密码不能为空');
        }else{
            passSubmission()
        }
    });
})
//新码判断
function passSubmission(){
    var ypassword=$("#inputPassword").val();
    var password=$(".aginpassword").val();
    var npassword=$(".newspassword").val();
     if(password != npassword){
        layer.msg('两次密码输入不一致！');
        return;
    }else{
        loadingSubmission(ypassword, password);
    }
}
function loadingSubmission(ypassword,password){
    $.ajax({
        url:"/back/pwd",
        type:"post",
        dataType:"json",
        data:{
            "password_older":ypassword,
            "password_new":password
        },
        success:function(data){
            if(data.state==0){
                alert("密码修改成功");
                location.reload();
            }else{
                layer.msg('密码修改失败');
            }
        }
    });
}
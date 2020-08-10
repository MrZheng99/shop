$(function (){
    $("#passinbut").click(function(){
        var password=$("#aginpassword").val();
        var ypassword=$("#inputPassword").val();
        var npassword=$("#newspassword").val();
        console.log(password+''+ypassword+npassword);
        if(password=='' || password==null && ypassword=='' || ypassword==null && npassword=='' || npassword==null){
            alert('密码不能为空');
        }else{
            passSubmission()
        }
    });
})
//新码判断
function passSubmission(){
    var ypassword=$("#inputPassword").val();
    var password=$("#aginpassword").val();
    var npassword=$("#newspassword").val();
     if(password != npassword){
        alert('两次密码输入不一致！');
        return;
    }else{
        loadingSubmission(ypassword, password);
    }
}
function loadingSubmission(ypassword,password){
    $.ajax({
        url:"/manager/changePassword",
        type:"post",
        dataType:"json",
        data:{
            "password_older":hex_md5(ypassword),
            "password_new":hex_md5(password)
        },
        success:function(data){
            if(data.success){
                alert("密码修改成功,请重新登录");
                location.href="/back/index";
            }else{
                layer.msg('密码修改失败');
            }
        }
    });
}
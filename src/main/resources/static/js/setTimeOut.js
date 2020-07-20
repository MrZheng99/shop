function setTimeOut(){
    $("#btnGetCode").attr('disabled',true);
	$("#btnGetCode").removeClass("btn-primary");
    $("#btnGetCode").addClass("btn-default");
    var i = 30;
    interval = setInterval(function() {
        $("#btnGetCode").val(i);

        i--;
        if (i === 0) {
            clearInterval(interval);
            $("#btnGetCode").attr('disabled',false);
            $("#btnGetCode").val('获取验证码');
            $("#btnGetCode").addClass("btn-primary");
            $("#btnGetCode").removeClass("btn-default");
        }
    }, 1000);	
	
}

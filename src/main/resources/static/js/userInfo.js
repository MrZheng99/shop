function fnError(mid, msg) {
    $("#" + mid).text(msg);
    $("#" + mid).css("color", "red");
    $("#" + mid).css("backgroundImage", "url(../images/onError.gif)");
}

function fnSuccess(mid) {
    $("#" + mid).text("");
    $("#" + mid).css("background-image", "url(../images/onCorrect.gif)");

}
$(document).ready(function() {
    $("#account").blur(function() {
        if (checkAccount($(this).val())) {
            fnSuccess("accountTip");
        } else {
            fnError("accountTip", "会员名以字母或汉字开头，由3-18位的数字、字母、下划线、汉字组成");
        }
    });
    $("#uname").blur(function() {
        if (checkName($(this).val())) {
            fnSuccess("unameTip");
        } else {
            fnError("unameTip", "请输入您的真实姓名");
        }
    });
    $("#pwd").blur(function() {
        if (checkPwd($(this).val())) {
            fnSuccess("pwdTip");
        } else {
            fnError("pwdTip", "密码由6-16位的数字、字母、下划线组成");
        }
    });

    $("#pwdAgain").blur(function() {
        if ($(this).val() == $("#pwd").val() && checkPwd($(this).val())) {
            fnSuccess("pwdAgainTip");
        } else {
            fnError("pwdAgainTip", "两次输入的密码必须一致");
        }
    });
    $("#mid").blur(function() {

        if (checkIdCard($(this).val().trim())) {
            fnSuccess("midTip");
        } else {
            fnError("midTip", "请输入您的有效证件号");
        }
    });
    $("#memail").blur(function() {
        if (checkEmail($(this).val())) {
            fnSuccess("memailTip");
        } else {
            fnError("memailTip", "请填写常用电子邮件");
        }
    });
    $("#tel").blur(function() {
        if (checkTel($(this).val().trim())) {
            fnSuccess("telTip");
        } else {
            fnError("telTip", "请输入您的常用联系方式");
        }
    });

    $("#province").blur(function() {
        if ($(this).val() === "-请选择-") {
            fnError("provinceTip", "请选择您所在的省份");
        } else {
            fnSuccess("provinceTip");
        }
    });
    $("#city").blur(function() {
        if ($(this).val() === "-请选择-") {
            fnError("cityTip", "请选择您所在的城市");
        } else {
            fnSuccess("cityTip");
        }
    });
    $("#area").blur(function() {
        if ($(this).val() === "-请选择-") {
            fnError("areaTip", "请选择您所在的县或区");
        } else {
            fnSuccess("areaTip");
        }
    });
})
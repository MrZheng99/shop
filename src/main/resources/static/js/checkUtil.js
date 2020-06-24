function regCheck(account) {
    if (checkEmail(account) || checkName(account) || checkTel(account)) {
        return true;
    }
    return false;　　　

}

function checkName(account) {
    let regName = new RegExp("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$"); //用户名
    if (regName.test(account)) {
        return true;
    }
    return false;　　　

}

function checkTel(account) {
    let regPhone = new RegExp("^1[34578]\d{9}$");
    let regTelPhone = new RegExp("^((0\d{2,4})-)(\d{7,8})(-(\d{3,}))?$");
    if (regPhone.test(account) || regTelPhone.test(account)) {
        return true;
    }
    return false;　　　

}

function checkEmail(account) {
    let regEmail = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //邮箱
    if (regEmail.test(account)) {
        return true;
    }
    return false;　　　

}

function checkPwd(pwd) {
    let regPassword = new RegExp("([a-zA-Z0-9]){6,16}"); //密码
    console.log(pwd);

    if (regPassword.test(pwd)) {
        return true;
    }
    return false;　　　

}
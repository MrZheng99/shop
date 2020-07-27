function regCheck(account) {
    if (checkEmail(account) || checkName(account) || checkTel(account)) {
        return true;
    }
    return false;　　　

}

function checkName(account) {
    let regNameZ = /[\u4e00-\u9fa5]{2,15}/ //用户名
    let regNameE = new RegExp("[a-zA-Z]{2,20}"); //用户名
    if (regNameZ.test(account)||regNameE.test(account)) {
        return true;
    }
    return false;　　　

}

function checkTel(account) {
    let regPhone = new RegExp("^1[34578]\\d{9}$");
    let regTelPhone = new RegExp("^((0\\d{2,4})-)(\\d{7,8})(-(\\d{3,}))?$");
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
/*****忘记密码和注册******/
function checkAccount(account) {
    if (account === "") { //输入不能为空
        document.getElementById('userNameError').textContent = '不能为空';
        return false;
    } else if (!checkName(account)) {

        document.getElementById('userNameError').textContent = '格式不正确';
        return false;
    }
    return true;
}

function checkPassword(password) {
    if (password === "" && !account === "") { //输入不能为空
        return false;

    } else if (!checkPwd(password)) {
        return false;

    }
    return true;

}

function checkPasswordAgain(passwordAgain) {
    let password = document.getElementById('pwd').value;
    if (passwordAgain === "" && !password === "") { //输入不能为空
        document.getElementById('pwdAgainError').textContent = '不能为空';　
        return false;

    } else if (!(password === passwordAgain)) {

        document.getElementById('pwdAgainError').textContent = '密码不一致';
        return false;


    }
    return true;

}

function checkMail(email) {

    if (email === "") { //输入不能为空
        document.getElementById('emailError').textContent = '不能为空';
        return false;

    } else if (!checkEmail(email)) {
        document.getElementById('emailError').textContent = '格式不正确';
        return false;

    }
    return true;

}

function checkPwd(pwd) {
    let regPassword = new RegExp("([a-zA-Z0-9]){6,16}"); //密码
    console.log(pwd);

    if (regPassword.test(pwd)) {
        return true;
    }
    return false;　　　

}
jQuery(function($) {
    $.supersized({
        slide_interval: 5000,
        transition: 1,
        transition_speed: 3000,
        performance: 1,
        min_width: 0,
        min_height: 0,
        vertical_center: 1,
        horizontal_center: 1,
        fit_always: 0,
        fit_portrait: 1,
        fit_landscape: 0,
        slide_links: 'blank',
        slides: [{
            image:staticResourcesPath + '/resources/img/bj04.jpg'
        }]
    });
});

function refresh(obj){
    obj.src="/chkcode/gen?time="+ Date.now();
}

$(function () {
    $("#loginUsername").focus();//输入焦点
    $("#loginUsername").keydown(function (event) {
        if (event.which == "13") {//回车键,移动光标到密码框
            $("#loginPassword").focus();
        }
    });

    $("#loginPassword").keydown(function (event) {    //loginVerify
        if (event.which == "13") {//回车键，用.ajax提交表单
            //$("#loginVerify").focus();
            $("#loginSubmit").trigger("click");
        }
    });

    $("#loginVerify").keydown(function (event) {
        if (event.which == "13") {//回车键，用.ajax提交表单
            $("#loginSubmit").trigger("click");
        }
    });
    $("#loginSubmit").click(function () { //“登录”按钮单击事件
        //获取用户名称
        var strTxtName = encodeURI($("#loginUsername").val());
        //获取输入密码
        var strTxtPass = encodeURI($("#loginPassword").val());
        strTxtPass = md5(strTxtPass);
        //获取验证码
        var strTxtVerify = encodeURI($("#loginVerify").val());
        //开始发送数据

        $.post(actionDefaultPath + '/login',{ username: strTxtName, password: strTxtPass ,verifyCode: strTxtVerify},function(data){
            if (data && data.status == 200) {//注意是True,不是true
                if(top.$(".navbar").length == 0){
                    location.href = actionDefaultPath + '/main';
                }else{
                    top.freshFrame();
                }
            } else {
                var message = '帐号或密码不正确';
                if(data.message){
                    message = data.message
                }
                layer.msg(message, {
                    icon: 2,
                    time: 2000
                });
            }
        });
    })
})


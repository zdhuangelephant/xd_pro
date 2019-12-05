var loginCheckVo = {};
$(document).ready(function () {
    $("#loginCheckBtn").click(function () {
        var post = {};
        post.uin = $("#uinInput").val();
        post.password = $("#passwordInput").val();
        $.post("/message/loginCheck.do",
            post,
            function (data) {
                data = $.parseJSON(data);
                loginCheckVo = data;
                if (data.code == 0) {
                    alert("登录成功!不需要输入验证码");
                    open_poll();
                } else {
                    alert("需要输入验证码!");
                    $("#loginCheckBtn").hide();
                    $("#verifyCodeDiv").show();
                    $("#verifyCodeImg").attr("src", "data:image/png;base64," + data.data);
                }
            });
    });
    $("#loginBtn").click(function () {
        var post = {};
        post.uin = $("#uinInput").val();
        post.password = $("#passwordInput").val();
        post.verifyCode = $("#verifyCodeInput").val();
        post.salt = loginCheckVo.salt;
        post.loginSig = loginCheckVo.loginSig;
        post.verifySession = loginCheckVo.verifySession;
        $.post("/message/login.do",
            post,
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    alert("登录成功!");
                    open_poll();
                } else {
                    alert("登录失败:" + data.desc + ",请刷新");
                }
            });
    });
    $("#verifyCodeImg").click(function () {
        var post = {};
        post.uin = $("#uinInput").val();
        $.post("/message/refreshImage.do",
            post,
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    $("#verifyCodeImg").attr("src", "data:image/png;base64," + data.data);
                }
            });
    })
});
function open_poll() {
    $.get("/message/poll.do", function (closePollData) {
        closePollData = $.parseJSON(closePollData);
        if (closePollData.retcode == 0) {
            alert("开启poll线程成功!" + closePollData.retcode + " " + closePollData.retdesc);
        } else {
            alert("开启poll线程失败!" + closePollData.retcode + " " + closePollData.retdesc);
        }
    });
}
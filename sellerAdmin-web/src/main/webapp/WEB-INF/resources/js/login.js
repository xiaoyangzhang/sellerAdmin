var checkSubmitFlg = false;

function chkLen(field_name, allow_len, label_name,defaultEmpty) {
	var s = field_name.value;
	if (typeof(defaultEmpty) == "undefined") defaultEmpty = true;
	if(isEmpty(s)) {
		if (!defaultEmpty) {
			field_name.focus();
			field_name.style.background=fieldbg;
			layer.alert("\u8bf7\u8f93\u5165" + label_name+"\u3002");
			return false;
		} else return true;
	}
	if (getTextLen(s) > allow_len) {
		field_name.focus();
		field_name.style.background=fieldbg;
		layer.alert(label_name + " \u7684\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e" + allow_len + " \u4e2a\u5b57\u8282\uff0c\u8bf7\u91cd\u65b0\u586b\u5199\u3002");
		return false;
	}
	return true;
}

function is_CheckSubmit() {
	if (checkSubmitFlg == true) {
		return false;
	}
	checkSubmitFlg = true;
	return true;
}

var loginVerify = function() {
	var form = document.getElementById('loginForm');
	if (!chkLen(form.company, 20, '企业编码', false)) {
		return;
	}
	if (!chkLen(form.username, 20, '用户名', false)) {
		return;
	}
	if (!chkLen(form.password, 20, '密码', false)) {
		return;
	}
	if (!chkLen(form.verify, 4, '验证码', false)) {
		return;
	}

}

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
			image: '/resources/img/bj01.jpg'
		}, {
			image: '/resources/img/bj02.jpg'
		}, {
			image: '/resources/img/bj03.jpg'
		}, {
			image: '/resources/img/bj04.jpg'
		}]
	});


	//document.getElementById("verify").src = "image.jsp?" + Math.random();

	$("#btnSubmit").bind("click", function() {
		loginVerify();
	});

});
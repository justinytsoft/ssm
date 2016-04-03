//校验邮箱是否合格
function isEmail(email) {
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	return reg.test(email);
}

// 验证电话号码
function isPhoneNumber(phone) {
	patrn = /^[\d\+\-\s]+$/;
	if (!patrn.exec(phone)) {
		return false;
	}
	return true;
}

// 验证手机号码
function isCellPhone(mobile) {
	if (mobile.length == 0) {
		return false;
	}
	if (mobile.length != 11) {
		return false;
	}
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	return myreg.test(mobile);
}

// 检查上传图片的后缀
function checkImgFile(val) {
	var filePath = val;
	var pix = filePath.split("\.");
	var extension = pix[pix.length - 1];
	if (extension != 'jpg' && extension != 'jpeg' && extension != 'png'
			&& extension != 'gif') {
		return false;
	}
	return true;
}

// 只能输入数字96(0) 105(9) 48(0) 57(9)
function isNumber(event) {
	if (event.keyCode < 48 || event.keyCode > 57) {
		if (event.keyCode == 8) { // BackSpace 退格
			return true;
		}
		if (event.keyCode >= 96 && event.keyCode <= 105) {
			return true;
		}
		return false;
	}
}

// 只能输入数字和一个点 110 190
function isNumberOrPoint(event, obj) {
	var ascii = event.keyCode;
	var value = $(obj).val();
	if (ascii < 48 || event.keyCode > 57) {
		if (ascii == 8) { // BackSpace 退格
			return true;
		}
		if (ascii >= 96 && ascii <= 105) {
			return true;
		}
		if (ascii == 110 || ascii == 190) {
			if (value == "") { // 第一个位置输入.返回false
				return false;
			}
			if (value.indexOf(".") != -1) { // 只能输入一个点且不能在第一个位置上输入
				return false;
			}
			return true;
		}
		return false;
	}
}

//保存Cookie
function saveCookie() {
	if ($("#rmbUser").attr("checked") == true) {
		var userName = $("#j_username").val();
		var passWord = $("#j_password").val();
		$.cookie("rmbUser", "true", {expires : 7}); // 存储一个带7天期限的 cookie
		$.cookie("userName", userName, {expires : 7}); // 存储一个带7天期限的 cookie
		$.cookie("passWord", passWord, {expires : 7}); // 存储一个带7天期限的 cookie
	} else {
		$.cookie("rmbUser", "false", {expires : -1});
		$.cookie("userName", '', {expires : -1});
		$.cookie("passWord", '', {expires : -1});
	}
}


// 判断开始时间是否大于结束时间
function isGreaterBeginTime(bId, eId) {
	var beginTime = $("#" + bId + "").val();
	var endTime = $("#" + eId + "").val();
	var data1 = Date.parse(beginTime.replace(/-/g, "/"));
	var data2 = Date.parse(endTime.replace(/-/g, "/"));
	var datadiff = data2 - data1;
	if (datadiff < 0) { // 开始时间小于结束时间
		return false;
	}
	return true;
}

// 计算两个日期的间隔天数
function interval(startDate, endDate) {
	var d1 = new Date(startDate.replace(/-/g, "/"));
	var d2 = new Date(endDate.replace(/-/g, "/"));
	var time = d2.getTime() - d1.getTime();
	return parseInt(time / (1000 * 60 * 60 * 24)) + 1;
}

// 格式化日期
function formatDateTime(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? ('0' + m) : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	var h = date.getHours();
	var minute = date.getMinutes();
	minute = minute < 10 ? ('0' + minute) : minute;
	var second = date.getSeconds();
	second = second < 10 ? ('0' + second) : second;
	return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
};

// 将表单数据转换为JSON对象
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this["name"]]) {
			o[this["name"]] = o[this["name"]] + "," + this["value"];
		} else {
			o[this["name"]] = this["value"];
		}
	});
	return o;
}

var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ]; // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]; // 身份证验证位值.10代表X
//省份证验证
function IdCardValidate(idCard) {
	idCard = trim(idCard.replace(/ /g, "")); // 去掉字符串头尾空格
	if (idCard.length == 15) {
		return isValidityBrithBy15IdCard(idCard); // 进行15位身份证的验证
	} else if (idCard.length == 18) {
		var a_idCard = idCard.split(""); // 得到身份证数组
		if (isValidityBrithBy18IdCard(idCard) && isTrueValidateCodeBy18IdCard(a_idCard)) { // 进行18位身份证的基本验证和第18位的验证
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * 
 * @param a_idCard
 *            身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
	var sum = 0; // 声明加权求和变量
	if (a_idCard[17].toLowerCase() == 'x') {
		a_idCard[17] = 10; // 将最后位为x的验证码替换为10方便后续操作
	}
	for (var i = 0; i < 17; i++) {
		sum += Wi[i] * a_idCard[i]; // 加权求和
	}
	valCodePosition = sum % 11; // 得到验证码所位置
	if (a_idCard[17] == ValideCode[valCodePosition]) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * 
 * @param idCard
 *            18位书身份证字符串
 * @return
 */
function isValidityBrithBy18IdCard(idCard18) {
	var year = idCard18.substring(6, 10);
	var month = idCard18.substring(10, 12);
	var day = idCard18.substring(12, 14);
	var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
	// 这里用getFullYear()获取年份，避免千年虫问题
	if (temp_date.getFullYear() != parseFloat(year)
			|| temp_date.getMonth() != parseFloat(month) - 1
			|| temp_date.getDate() != parseFloat(day)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 验证15位数身份证号码中的生日是否是有效生日
 * 
 * @param idCard15
 *            15位书身份证字符串
 * @return
 */
function isValidityBrithBy15IdCard(idCard15) {
	var year = idCard15.substring(6, 8);
	var month = idCard15.substring(8, 10);
	var day = idCard15.substring(10, 12);
	var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
	// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
	if (temp_date.getYear() != parseFloat(year)
			|| temp_date.getMonth() != parseFloat(month) - 1
			|| temp_date.getDate() != parseFloat(day)) {
		return false;
	} else {
		return true;
	}
}

// 去掉字符串头尾空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

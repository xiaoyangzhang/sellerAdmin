// 字符验证
jQuery.validator.addMethod("stringCheck", function(value, element) {
    return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "只能包括中文字、英文字母、数字和下划线");
// 中文字两个字节
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
    var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
    return this.optional(element) || ( length >= param[0] && length <= param[1] );
}, "请确保输入的值在3-15个字节之间(一个中文字算2个字节)");

// 身份证号码验证
jQuery.validator.addMethod("isIdCardNo", function(value, element) {
    return this.optional(element) || idCardNoUtil.checkIdCardNo(value);
}, "请正确输入您的身份证号码");
//护照编号验证
jQuery.validator.addMethod("passport", function(value, element) {
    return this.optional(element) || checknumber(value);
}, "请正确输入您的护照编号");

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

// 电话号码验证
jQuery.validator.addMethod("isTel", function(value, element) {
    var tel = /^\d{3,4}-?\d{7,9}$/; //电话号码格式010-12345678
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的电话号码");

// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isPhone", function(value,element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    var tel = /^\d{3,4}-?\d{7,9}$/;
    return this.optional(element) || (tel.test(value) || mobile.test(value));

}, "请正确填写您的联系电话");

// 邮政编码验证
jQuery.validator.addMethod("isZipCode", function(value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

//多个同名元素验证
jQuery.validator.addMethod("requiredAll",function(value,element,params){
    var flag = true;
    var name = $(element).attr("name");
    //var name = element.name;
    var eleNode = element.nodeName;
    if(eleNode == "INPUT"){
        $("input[name="+name+"]").each(function(){
            if(!$(this).val() || isNaN($(this).val())) {
                flag = false;
            }
        });
    }else{
        $("select[name="+name+"]").each(function(){
            if($(this)[0].options[$(this)[0].selectedIndex].value == ""){
                flag = false;
            }
        });
    };
    return flag;
});

//判断两个时间的先后顺序
jQuery.validator.addMethod("compareDate",function(value, element, param) {
    //var startDate = jQuery(param).val() + ":00";补全yyyy-MM-dd HH:mm:ss格式
    //value = value + ":00";

    var startDate = jQuery(param).val();

    var date1 = new Date(Date.parse(startDate.replace("-", "/")));
    var date2 = new Date(Date.parse(value.replace("-", "/")));
    return date1 < date2;
});

jQuery.validator.addMethod("numTwoPoint", function(value, element) {
    return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);
}, "请输入正确的数字，如是小数，小数位不能超过两位");
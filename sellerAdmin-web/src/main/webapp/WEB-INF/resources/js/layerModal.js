layer.config({
	extend : 'extend/layer.ext.js'
});
var openModalForForm = function(url, title, callBack) {// 弹出框url，标题，回调返回参数
	layer.open({
		type : 2,
		btn : [ '确定', '取消' ],
		title : title,
		fix : false,
		shadeClose : true,
		maxmin : true,
		area : [ '1000px', '500px' ],
		content : url,
		success : function(layero, index) {
			win = window[layero.find('iframe')[0]['name']];
		},
		yes : function(index) {
			if (callBack(win, index)) {
				layer.close(index);
			} else {
				return false;
			}
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

var openModal = function(url, title, callBack) {// 弹出框url，标题，回调返回参数
	layer.open({
		type : 2,
		btn : [ '确定', '取消' ],
		title : title,
		fix : false,
		shadeClose : true,
		maxmin : true,
		area : [ '1000px', '500px' ],
		content : url,
		yes : function(index) {
			if (top.resultValue && top.resultValue.length == 0) {
				layer.alert('请选择', {
					icon : 2
				});
				return false;
			} else {
				callBack(top.resultValue);
				layer.close(index);
			}
		}
	});
}

var showPic = function(pictureArrData, index, width, height) {// 图片url数组，预览开始的索引，宽，高
	if (!width) {
		width = '450px';
	}
	if (!height) {
		height = '300px';
	}
	var pictureArr = new Array();
	for ( var picture in pictureArrData) {
		pictureArr.push({
			"alt" : "图片名",
			"pid" : "", // 图片id
			"src" : tfsRootPath + pictureArrData[picture], // 原图地址
			"thumb" : "" // 缩略图地址
		});
	}
	var json = {
		"title" : "图片预览", // 相册标题
		"start" : index, // 初始显示的图片序号，默认0
		"data" : pictureArr
	};

	layer.photos({
		area : [ width, height ],
		photos : json
	});
}
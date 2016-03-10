//不是插件
var fileUpload = function(id,type,callBack){//id：上传控件筛选器（‘#id’或‘。class’），type：1单文件；2多文件，callBack：回调函数接收data
    var oFiles = document.querySelector(id).files;
    // 实例化一个表单数据对象
    var formData = new FormData();
    // 遍历文件列表，插入到表单数据中
    for (var i = 0, file; file = oFiles[i]; i++) {
        if(file.size > 2000 * 1024){
            layer.msg('图片不能大于2M',{icon:2});
            return;
        }
        // 文件名称，文件对象
        formData.append(file.name, file);
    }
    var xhr = new XMLHttpRequest();
    xhr.onload = function(data) {
        var data = JSON.parse(data.target.response);
        if(data && data.status == 200){
            var errMessage = new Array();
            for(key in data.data){
                alert(data.data[key]);
                if(!data.data[key] || data.data[key] === "null" || data.data[key].length < 10){
                    delete data.data[key];
                    errMessage.push(key);
                }
            }
            if(errMessage.length > 0){
                layer.msg("以下图片上传失败：" + errMessage.join(','),{icon:2});
            }
        }
        //执行回调
        callBack(data,id);

    };
    //xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", function(a,b,data){
        layer.msg('提交出错',{icon:2});
    }, false);
    xhr.addEventListener("abort", function(a,b,data){
        layer.msg('提交出错',{icon:2});
    }, false);
    if(type==1){
        xhr.open("POST", uploadFile, true);
    }else{
        xhr.open("POST",  uploadFiles, true);
    }
    // 发送表单数据
    xhr.send(formData);
}
(function($){
	
	Array.prototype.indexOf = function(val) {              
		for (var i = 0; i < this.length; i++) {  
			if (this[i] == val) return i;  
		}  
		return -1;  
	};
	Array.prototype.getFlag = function(flag) {              
		for (var i = 0; i < this.length; i++) {  
			if (this[i].flag == flag) return i;  
		}  
		return -1;  
	};
    Array.prototype.remove = function(val) {  
        var index = this.indexOf(val);  
        if (index > -1) {  
            this.splice(index, 1);  
        }  
    };
	
	$.fn.setimagesInputVal = function(options){
		var $this = $(this);
		var _config = {
			itemClass : '.itemlist li'
		}
		var opts = $.extend(_config,options);
		var objArr = $this.val()==""?[]:JSON.parse($this.val());
		$(opts.itemClass).each(function(i){
			var itemObj = {}
			var dataid = $(this).attr("data-id")?$(this).attr("data-id"):0;
			var flag = $(this).attr("data-flag");
			var name = $(this).attr("data-name");
			var value = $(this).attr("data-value");
			var index = objArr.getFlag(flag);
			if(dataid == 0 && index==-1){
				itemObj.id = dataid;
				itemObj.name = name;
				itemObj.value = value;
				itemObj.flag = flag;
				itemObj.istop = false;
				itemObj.isdel = false;
				itemObj.modify = false;
				itemObj.istop = false;
				objArr.push(itemObj);
			}
		});
		var jsonStr = objArr.length>0?JSON.stringify(objArr):"";
		console.log(jsonStr);
		$this.val(jsonStr);
	};
	$.fn.setimages = function(options){
		var $this = $(this);
		var init = {
			settopClass : '.settop',
			delClass : '.delimg',
			itemClass : '.itemlist li',
			formId : '#saveimgform',
			inputId : 'imgids',
			inputName : 'imgids',
			topNum : 3,
			delCallback : function(){},
			settopCallback : function(){}
		}
		var opts = $.extend(init,options);
		var input = '<input type="hidden" name="'+opts.inputName+'" id="'+opts.inputId+'" value="" />';
		
		var topFlag = {},objArr=[];
		
		if($("#"+opts.inputId).length == 0 ){
			$(opts.formId).append(input);
		}else{
			$("#"+opts.inputId).val('');
		}
		
		var getTopNumber = function(){
			var num = 0;
			$(opts.itemClass).each(function(i){
				if($(this).attr("istop")) num++;
			});
			return num;
		}
		
		var setInputVal = function(){
			var jsonStr = objArr.length>0?JSON.stringify(objArr):"";
			$("#"+opts.inputId).val(jsonStr);
			console.log(jsonStr);
		}
		
		var setItemInitVal = function(){
			$(opts.itemClass).each(function(i){
				var _istop = $(this).attr("istop")?true:false;
				var _id = $(this).attr("data-id");
				var _flag = new Date().getTime()+i;
				$(this).attr("data-flag",_flag);
				topFlag[_flag]=_istop;
			});
		}
		
		
		var setTopEvent = function(){
			objArr = $("#"+opts.inputId).val()==""?[]:JSON.parse($("#"+opts.inputId).val());
			var itemObj = {};
			var _parent = $(this).closest("li");
			var _flag = _parent.attr("data-flag");
			var _arrIndex = objArr.getFlag(_flag);
			var _istop = _parent.attr("istop")?true:false;
			var _id = _parent.attr("data-id")?_parent.attr("data-id"):0;
			
			if(!_istop && getTopNumber()>=opts.topNum){
				alert("您只能置顶"+opts.topNum+"个图片");
				return false;
			}
			
			itemObj.id = _id;
			itemObj.name = _parent.attr("data-name");
			itemObj.value = _parent.attr("data-value");
			itemObj.flag = _flag;
			itemObj.istop = _istop?false:true;
			itemObj.isdel = false;
			itemObj.modify = true;
			itemObj.istop = _istop?false:true;

			if(_id == 0){
				if(_arrIndex>-1){
					objArr[_arrIndex] = itemObj;
				}else{
					objArr.push(itemObj);
				}
			}else{
				if(topFlag[_flag]==_istop){
					objArr.push(itemObj);
				}else{
					objArr.splice(_arrIndex,1);
				}
			}
			
			setInputVal();
			opts.settopCallback($(this),_parent);
			return false;
		}
		
		var delImgEvent = function(){
			objArr = $("#"+opts.inputId).val()==""?[]:JSON.parse($("#"+opts.inputId).val());
			var itemObj = {};
			var _parent = $(this).closest("li");
			var _flag = _parent.attr("data-flag");
			var _arrIndex =  objArr.getFlag(_flag);

			itemObj.id = _parent.attr("data-id")?_parent.attr("data-id"):0;
			itemObj.name = _parent.attr("data-name");
			itemObj.value = _parent.attr("data-value");
			itemObj.flag = _flag;
			itemObj.istop = false;
			itemObj.isdel = true;
			itemObj.modify = false;
			
			if(_arrIndex==-1){
				objArr.push(itemObj);
			}else{
				objArr[_arrIndex] = itemObj;
			}
			
			setInputVal();
			opts.delCallback($(this),_parent);
			return false;
		}
		
		setItemInitVal();
		
		$(document).on("click",opts.settopClass,setTopEvent);
		$(document).on("click",opts.delClass,delImgEvent);
		
	}

})(jQuery);

$(function(){
	$("#img-manage").setimages({
		settopClass : '.settop',
		delClass : '.delimg',
		itemClass : '.itemlist li',
		formId : '#saveimgform',
		inputId : 'imgids',
		inputName : 'imgids',
		topNum : 3,		
		delCallback : function($this){
			var _parent = $this.closest("li");
			_parent.remove();
		},
		settopCallback : function($this){
			var _parent = $this.closest("li");
			if(_parent.attr("istop")){
				_parent.removeAttr("istop");
				$this.html("置顶");
			}else{
				_parent.attr("istop",true);
				$this.html("取消");
			}
		}
	});
	var batchCallBack = function(dataVal){
		if(dataVal && dataVal.status == 200){
			var i=0;
			for(var key in dataVal.data){
				i++;
				if(dataVal.data.hasOwnProperty(key)){
					var addImgs = '<li data-id="0" data-flag="'+(new Date().getTime()+i)+'" data-name="'+key+'" data-value="'+dataVal.data[key]+'">'
						+'<a href="" class="img"><img src="' + tfsRootPath + dataVal.data[key] + '" width="240" height="192"/></a>'
						+'<span><a href="javascript:" class="settop">置顶</a><a href="javascript:" class="delimg">删除</a></span>'
						+'</li>';

					$(".itemlist").append(addImgs);
				}
			}

			$("#imgids").setimagesInputVal();
		}
	}
	/****************批量上传*****************/
	$(document).delegate("#batchUploadBtn",'change',function(){
		fileUpload('#batchUploadBtn',2,batchCallBack);
	});
	
});	
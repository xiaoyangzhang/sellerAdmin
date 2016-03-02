// 只能输入浮点数 START
$(document).delegate(".double-only","keyup",function(){
	if (event.keyCode != 8 && event.keyCode != 9) {
		if(isNaN(this.value)) {
			document.execCommand('undo');
		}
	}
});
$(document).delegate(".double-only","onafterpaste",function(){
	if (event.keyCode != 8 && event.keyCode != 9) {
		if(isNaN(this.value)) {
			document.execCommand('undo');
		}
	}
});
// 只能输入浮点数 END
// 只能输入整数 START
$(document).delegate(".int-only","keyup",function(){
	if (event.keyCode != 8 && event.keyCode != 9) {
		var temp = parseInt(this.value);
		if(isNaN(temp)) {
			document.execCommand('undo');
		} else {
			this.value = temp
		}
	}
});
$(document).delegate(".int-only","onafterpaste",function(){
	if (event.keyCode != 8 && event.keyCode != 9) {
		if(isNaN(this.value)) {
			document.execCommand('undo');
			return true;
		} else {
			this.value = parseInt(this.value);
		}
	}
});
// 只能输入整数 END
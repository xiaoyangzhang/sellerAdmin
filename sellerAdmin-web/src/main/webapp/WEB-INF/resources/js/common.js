
function openwin(url) {
    var a = document.createElement("a");
    a.setAttribute("href", url);
    a.setAttribute("target", "_blank");
    a.setAttribute("id", "camnpr");
    document.body.appendChild(a);
    a.click();
}

/************父页面刷新子页面************/
function freshFrame(callback){
    top.$(".tab-pane").each(function(){
        if($(this).css("display") == "block"){
            //console.log($(this));
            var th = $(this)[0]
            var srcFrame = $(th).find(".tabIframe").attr("src");
            $(th).find(".tabIframe").attr("src",srcFrame);
            if(callback){
                callback();
            }
        }
    })
}

/************子页面操作刷新前一个子页面************/
function freshPrevFrame(callback){
    top.$(".tab-pane").each(function(){
        if($(this).css("display") == "block"){
            //console.log($(this));
            var th = $(this)[0];
            var parentTabId = $(th).attr("parenttabid");
            var parentSrc = $("#"+parentTabId).find(".tabIframe").attr("src");
            $("#"+parentTabId).find(".tabIframe").attr("src",parentSrc);
            if(callback){
                callback();
            }
        }
    })
}
var local;
var getPoint= function(){
    var city = document.getElementById("txtCity").value;
    local.search(city);                 // 初始化地图,设置城市和地图级别。
    //启用滚轮放大缩小
}
function openBaiduMap(pointX,pointY,callback){   //获取经纬度，pointX初始化经度，pointY初始化纬度，callback回调函数
    var bdMap = '<div class="demo" id="baiduMap" >'+
                '<p style="height:40px;">'+
                '输入城市：<input id="txtCity" type="text"/>'+
                '<button  onClick="getPoint()">获取坐标</button>'+
                '经度：<input id="txtPointX" type="text"/>'+
                '纬度<input id="txtPointY" type="text"/></p>'+
                '<div id="l-map"></div>'+
                '<div id="r-result"></div>'+
                '</div>'
    $("body").append(bdMap);

    var map = new BMap.Map("l-map");            // 创建Map实例
    map.centerAndZoom(new BMap.Point(pointX, pointY), 4);
    map.enableScrollWheelZoom();
    map.addEventListener("click",function(e){

        document.getElementById("txtPointX").value=e.point.lng;
        document.getElementById("txtPointY").value=e.point.lat;
    });
    local = new BMap.LocalSearch("全国", {
        renderOptions: {
            map: map,
            panel : "r-result",
            autoViewport: true,
            selectFirstResult: false
        }
    });

    layer.open({
        type : 1, title : '地图', closeBtn : false,
        area : [ '880px', '450px' ], shadeClose : false,
        content : $('#baiduMap'),
        btn : [ '确认', '取消' ],
        yes : function(index) {
            var data = {"pointX":$("#txtPointX").val(),"pointY":$("#txtPointY").val()};
            callback(data);
            $(".layui-layer").remove();
            $(".layui-layer-shade").remove();
            layer.close(index);
        },
        cancel : function(index) {
            layer.close(index);
        }
    });
}
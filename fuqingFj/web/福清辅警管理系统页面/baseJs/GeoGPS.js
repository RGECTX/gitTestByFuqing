    var map, geolocation,userSite=null;
	if(getCookie("userSite")!="" && getCookie("userSite")!="\"\""){
		userSite=getCookie("userSite");
	}
    //加载地图,调用浏览器定位服务
    map = new AMap.Map('mapContainer', {
        resizeEnable: true
    });
    map.plugin('AMap.Geolocation', function() {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位,默认:true
            timeout: 10000,          //超过10秒后停止定位,默认:无穷大
            maximumAge: 10000,           //定位结果缓存10秒,默认:0
            convert: true,           //自动偏移坐标,偏移后的坐标为高德坐标,默认:true
            showButton: true,        //显示定位按钮,默认:true
            buttonPosition: 'LB',    //定位按钮停靠位置,默认:'LB',左下角
            buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量,默认:Pixel(10, 20)
            showMarker: true,        //定位成功后在定位到的位置显示点标记,默认:true
            showCircle: true,        //定位成功后用圆圈表示定位精度范围,默认:true
            panToLocation: true,     //定位成功后将定位到的位置作为地图中心点,默认:true
            zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见,默认:false
        });
        map.addControl(geolocation);
        AMap.event.addListener(geolocation, 'complete', getUserSiteComplete);//返回定位信息
        AMap.event.addListener(geolocation, 'error', getUserSiteError);      //返回定位出错信息
    });
    //获取当前位置信息
    function getCurrentPosition() {
        geolocation.getCurrentPosition();
    }
    //监控当前位置并获取当前位置信息
    function watchPosition() {
        geolocation.watchPosition();
    }
    
	
	//已知点坐标
    function geocoder(userSite) {
		if(userSite==null){
			getCurrentPosition();
			return;
		}
		var lnglatXY=userSite.split(",");
        var MGeocoder;
        //加载地理编码插件
        AMap.service(["AMap.Geocoder"], function() {
            MGeocoder = new AMap.Geocoder({
                radius: 1000,
                extensions: "all"
            });
            //逆地理编码
            MGeocoder.getAddress(lnglatXY, function(status, result) {
                if (status === 'complete' && result.info === 'OK') {
                    getAddressComplete(result);
                }
            });
        });
    }
	
	
	//获取用户所在城市信息
    function showCityInfo() {
        //实例化城市查询类
        var citysearch = new AMap.CitySearch();
        //自动获取用户IP,返回当前城市
        citysearch.getLocalCity(function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
                if (result && result.city && result.bounds) {
                    var cityinfo = result.city;
                    var citybounds = result.bounds;
                    document.getElementById('tip').innerHTML = '您当前所在城市:'+cityinfo;
                    //地图显示当前城市
                    map.setBounds(citybounds);
                }
            } else {
                document.getElementById('tip').innerHTML = result.info;
            }
        });
    }
	
	
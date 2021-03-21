// JavaScript Document
//适用于单个按钮
(function($) {
    $.fn.greathackButton = function(options) {
        var buttonElm = this;
        var settings = {
            'enableClass': '',//可用状态样式class
            'disableClass': '',//禁用状态样式class
			'target': buttonElm,//状态样式应用对象
			'mouseDownClass': '',//鼠标按下效果样式class,放开后恢复原来的样式
			'isClickDisable': false,//点击后是否禁用,防手快二次点击
			'isEnable':true,//初始化状态是否可用
            onClickInEnable: function(jqobj) {},//可用状态下点击事件
            onClickInDisable: function(jqobj) {},//禁用状态下点击事件
            onEnable: function(jqobj) {},//变成可用事件
			onDisable: function(jqobj) {},//变成禁用事件
			onLongDown: function(jqobj) {}//长按事件
        };
        if (options) {
            $.extend(settings, options);
        }
		function getBrowserVersions(){//获取浏览器版本
			var u = navigator.userAgent, app = navigator.appVersion;
			return {//移动终端浏览器版本信息
				trident: u.indexOf('Trident') > -1, //IE内核
				presto: u.indexOf('Presto') > -1, //opera内核
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile: !!u.match(/AppleWebKit.*Mobile/i) || !!u.match(/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/), //是否为移动终端
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
				iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
				iPad: u.indexOf('iPad') > -1, //是否iPad
				webApp: u.indexOf('Safari') == -1 //是否web应该程序,没有头部与底部
			};
		}
		function isMobile(){//是否手机端
			var browserVersions=getBrowserVersions();
			if(browserVersions.mobile){
				return true;
			}else{
				return false;	
			}
		}
		//触摸事件
		var touchEvents = {		
			touchstart: isMobile()?"touchstart":"mousedown",
			touchmove: isMobile()?"touchmove":"mousemove",
			touchend: isMobile()?"touchend":"mouseup",
			touchcancel: isMobile()?"touchcancel":"mouseout"	,
		}
		var isEnable=null;
		var touchTimeout=null;
		var touchstartTime=0;
		var private={
			click:function(){
				if(isEnable==true){
					settings.onClickInEnable(buttonElm);
					if(settings.isClickDisable!=false){//是否防手快
						GreathackButton.setDisable();
					}
				}else{
					settings.onClickInDisable(buttonElm);
				}
			},
			init:function(){
				if(settings.isEnable){
					GreathackButton.setEnable();
				}else{
					GreathackButton.setDisable();
				}
				buttonElm.delegate('',touchEvents.touchcancel,{},function(e){
					if(new Date().getTime()-touchstartTime<1000){//按的时间短,取消长按事件
						clearTimeout(touchTimeout);
						touchTimeout=null;
						touchstartTime=0;
					}
					if(settings.mouseDownClass!=""){//鼠标点击效果//有设置效果才执行
						if(isEnable==true){
							settings.target.removeClass(settings.mouseDownClass).removeClass(settings.disableClass).addClass(settings.enableClass);
						}
					}
				});
				buttonElm.delegate('',touchEvents.touchstart,{},function(e){
					if(settings.mouseDownClass!=""){//鼠标点击效果//有设置效果才执行
						if(isEnable==true){
							settings.target.removeClass(settings.enableClass).removeClass(settings.disableClass).addClass(settings.mouseDownClass);
						}
					}
					
					touchstartTime=new Date().getTime();
					if(isEnable==true){//可用状态才能长按						
						e.preventDefault(); 
						touchTimeout=setTimeout(function(){//长按
							settings.onLongDown(buttonElm);
							clearTimeout(touchTimeout);
							touchTimeout=null;
							touchstartTime=0;
							
						},1000);
					}
				});
				buttonElm.delegate('',touchEvents.touchend,{},function(e){//鼠标点击效果恢复
					if(new Date().getTime()-touchstartTime<1000){//按的时间短,取消长按事件
						clearTimeout(touchTimeout);
						touchTimeout=null;
						touchstartTime=0;
						//var touch = event.touches[0];
						//if(this.style.top<=touch.pageY && this.style.left<=touch.pageX && this.style.left+$(this).width()>=touch.pageX && this.style.top+$(this).height()>=touch.pageY){
							private.click();
						//}
					}
					if(settings.mouseDownClass!=""){//鼠标点击效果//有设置效果才执行
						if(isEnable==true){
							settings.target.removeClass(settings.mouseDownClass).removeClass(settings.disableClass).addClass(settings.enableClass);
						}
					}
				});
			}
		}
		var GreathackButton = {			
			setEnable:function(){//设为可用
				var oldState=isEnable;
				isEnable=true;
				settings.target.removeClass(settings.disableClass).removeClass(settings.mouseDownClass).addClass(settings.enableClass);			
				if(oldState!=true){
					settings.onEnable(buttonElm);
				}
			},
			setDisable:function(){//设为禁用
				var oldState=isEnable;
				isEnable=false;
				settings.target.removeClass(settings.enableClass).removeClass(settings.mouseDownClass).addClass(settings.disableClass);
				if(oldState!=false){
					settings.onDisable(buttonElm);
				}
			},
			toggle:function(){//变成反状态
				if(isEnable){
					this.setDisable();
				}else{
					this.setEnable();
				}
			},
			returnFunc:function(){
				private.init();
				return this;
			}
		}
		return GreathackButton.returnFunc();
	};
})(jQuery);

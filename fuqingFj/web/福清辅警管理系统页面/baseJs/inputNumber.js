// JavaScript Document
(function($) {
    $.fn.inputNumber = function(options) {
		var inputElm = this;
		if(inputElm.data("instance")!=undefined){//单例,保证多次调用时不会再生成一个
			return inputElm.data("instance");
		}
        var settings = {
            'min': 0,//最小值
            'max': 10,//最大值
			'initNum':0,//初始值
			'step': 1,//增加或减少的步长
			'delay': 1000,//与onKeyupDelay配合,输入多少秒后执行
			'decimal':0,//小数字后保留的位数,必须是零或正整数
            onMoreThanMax: function(num,jqobj) {},//超过最大值时执行,如果isLimit是true,则不会执行
            onLessThanMin: function(num,jqobj) {},//小于最小值时执行,如果isLimit是true,则不会执行
            onIncrease: function(num,jqobj) {},//增加时执行,没变化不会执行
            onDecrease: function(num,jqobj) {},//减少时执行,没变化不会执
            onKeyupDelay: function(event,jqobj) {},//输入多少秒后执行,在delay时间内连续输入不执行,只执行最后一次delay
			onChange: function(event,jqobj) {},//改变时执行,,没变化不会执,删除变空,不算改变
			onEmpty: function(preNum,jqobj) {},//删除变空时触发
			'isLimit':true,//是否有限制最大值、最小值
			'allowEmpty':true,//是否允许变空
			'isOnClickSelect':true//是否点击选中
        };
        if (options) {
            $.extend(settings, options);
        }
		function isNumber(){//根据设定检查是否数字,整数或小数
			var reg=null;
			if(settings.decimal==0){
				reg=/^(\d+[\s,]*)+$/;
			}else{
				reg=new RegExp("^(\\d+[\\s,]*)+\\.?\\d{0,"+settings.decimal+"}$")
			}
			return reg.test(inputElm.val());
		}		
		function parseNumber(number){//字符串根据设定转成数字型
			if(settings.decimal==0){
				return parseInt(number);
			}else{
				return parseFloat(number);
			}
		}
		var timeInterval = null;
		var private={
			checkInput:function (){//检查输入是否数字,不是的话删除最后一个输入,直到是为止
				while(inputElm.val().length>0 && !isNumber(inputElm.val())){
					inputElm.val(inputElm.val().substring(0,inputElm.val().length-1));
				}
				if(inputElm.val()==""){
					if(!settings.allowEmpty){//不允许空
						inputElm.val(settings.min);
					}
				}
			},
			checkOnInterval:function (){//实时监听
				var self=this;
				timeInterval = setInterval(function() {//实时监听
					self.checkInput();
					if(eventObj.num!=parseNumber(inputElm.val())){
						inputElm.change();
					}
				},
				500);
			},
			init:function(){
				this.checkOnInterval();
			}
		};
		var timeout = null;
		var eventObj={
			'preNum':settings.initNum,
			'num':settings.initNum
		};
		var InputNumber = {
			setMin:function(minNum){
				settings.min=minNum;
			},
			setMax:function(maxNum){
				settings.max=maxNum;
			},
			decrease:function(){//减少
				clearInterval(timeInterval);//调用的时候先关掉检测
				var step=settings.step;
				var num=parseNumber(inputElm.val());
				if(num>=settings.min+step){
					inputElm.val(num-step);	
				}else{
					if(settings.isLimit==true){
						inputElm.val(settings.min);
					}else{
						inputElm.val(num-step);
					}
				}
				if(eventObj.num!=parseNumber(inputElm.val())){
					settings.onDecrease(parseNumber(inputElm.val()),inputElm);
					inputElm.change();
				}		
				private.checkOnInterval();//恢复检测		
			},
			increase:function(){//增加
				clearInterval(timeInterval);//调用的时候先关掉检测
				var step=settings.step;
				if(parseNumber(inputElm.val())<=settings.max-step){
					inputElm.val(parseNumber(inputElm.val())+step);
				}else{
					if(settings.isLimit==true){
						inputElm.val(settings.max);
					}else{
						inputElm.val(parseNumber(inputElm.val())+step);
					}
				}
				if(eventObj.num!=parseNumber(inputElm.val())){
					settings.onIncrease(parseNumber(inputElm.val()),inputElm);
					inputElm.change();					
				}
				private.checkOnInterval();//恢复检测
			},
			returnFunc:function(){
				private.init();//检测
				var self=this;
				if(settings.isOnClickSelect){
					inputElm.delegate('','click',function(){//点击全选文本框内容
						inputElm.focus();
						inputElm.select();
					});
				}
				inputElm.delegate('','keyup',function(){
					clearInterval(timeInterval);//输入的时候先关掉检测
					private.checkInput();
					if(parseNumber(inputElm.val())>=settings.max){
						if(settings.isLimit==true){
							inputElm.val(settings.max);
						}
					}
					if(parseNumber(inputElm.val())<=settings.min){
						if(settings.isLimit==true){
							inputElm.val(settings.min);
						}
					}
					clearTimeout(timeout);
					timeout=setTimeout(function(){//delay秒后执行
						settings.onKeyupDelay(eventObj,inputElm);
						//if(eventObj.num!=parseNumber(inputElm.val())){
						//	inputElm.change();
						//}
						
					},settings.delay);
					private.checkOnInterval();//恢复检测
				});
				inputElm.delegate('','change',function(){
					if(settings.allowEmpty && inputElm.val()==""){//变成空不算改变
						settings.onEmpty(eventObj.num,inputElm);//触发变空事件
						return;
					}
					if(parseNumber(inputElm.val())>=settings.max){
						if(settings.isLimit==true){
							inputElm.val(settings.max);
						}else{
							settings.onMoreThanMax(parseNumber(inputElm.val()),inputElm);
						}
					}
					if(parseNumber(inputElm.val())<=settings.min){
						if(settings.isLimit==true){
							inputElm.val(settings.min);
						}else{
							settings.onLessThanMin(parseNumber(inputElm.val()),inputElm);
						}
					}					
					eventObj.preNum=eventObj.num;
					eventObj.num=parseNumber(inputElm.val());
					settings.onChange(eventObj,inputElm);
				});
				inputElm.data("instance",self);
				return self;
			}
		};
		return InputNumber.returnFunc();
		
	};		
})(jQuery);

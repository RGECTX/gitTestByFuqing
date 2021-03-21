// JavaScript Document
/*
融合属性:单选覆盖多选,必选覆盖可选
*/
(function($) {
    $.fn.checkbox = function(options) {
		var checkboxElm = this;
        var settings = {
            'checkboxType': 'single',//single:单选,multiple:多选
			'isLeastOne':false,//是否必选,至少要选一个
            onCheck: function(self) {},//选中时执行
            onUnCheck: function(self) {},//取消选中时执行
			onChange: function(self,isChecked) {}//改变时执行,没变化不会执行
        };
        if (options) {
            $.extend(settings, options);
        }
		var listenerInterval = null;
		var firstItem=null;
		var private={
			changeState:function (self,isChecked){
				clearInterval(listenerInterval);//先关掉检测
				$(self).attr("greathackChecked",isChecked);
				$(self).data("greathackChecked",isChecked);
				private.listener();//恢复检测
			},
			unCheckAll:function (){				
				checkboxElm.each(function(index, element) {
					var self=this;
					if($(self).attr("greathackChecked")=="true"){
						private.changeState(self,"false");
						settings.onUnCheck(self);
						settings.onChange(self,"false");
					}                    
                });				
			},
			checkAll:function (){
				checkboxElm.each(function(index, element) {
					var self=this;
					if($(self).attr("greathackChecked")=="false"){
						private.changeState(self,"true");
						settings.onCheck(self);
						settings.onChange(self,"true");
					}
                });
			},
			reverse:function (){//反选
				checkboxElm.each(function(index, element) {
					var self=this;
					if($(self).attr("greathackChecked")=="false"){
						private.changeState(self,"true");
						settings.onCheck(self);
						settings.onChange(self,"true");
					}else{
						private.changeState(self,"false");
						settings.onUnCheck(self);
						settings.onChange(self,"false");
					}
                });
			},
			check:function (self){
				var preIsChecked=$(self).attr("greathackChecked");
				if($(self).attr("checkboxType")!="single"){//如果是多选
					if(preIsChecked!="true" || $(self).data("greathackChecked")!="true"){
						private.changeState(self,"true");
						settings.onCheck(self);
						settings.onChange(self,"true");
					}
				}else{//单选
					if(preIsChecked!="true" || $(self).data("greathackChecked")!="true"){
						this.unCheckAll();
						private.changeState(self,"true");
						settings.onCheck(self);
						settings.onChange(self,"true");						
					}
				}
				
			},
			unCheck:function (self){
				var preIsChecked=$(self).attr("greathackChecked");
				if($(self).attr("isLeastOne")=="false"){//非必选					
					if(preIsChecked=="true" || $(self).data("greathackChecked")=="true"){
						private.changeState(self,"false");
						settings.onUnCheck(self);
						settings.onChange(self,"false");
					}
				}else{//必选
					if($(self).attr("checkboxType")=="single"){//单选的情况,只能选中,不能取消
						return;
					}else{//多选可以取消,但要检查是不是只有一个框被选中
						var checkedNum=0;
						checkboxElm.each(function(index, element) {
							if($(element).attr("greathackChecked")=="true"){
								checkedNum++;
							}							
						});
						if(checkedNum>1){
							if(preIsChecked=="true" || $(self).data("greathackChecked")=="true"){
								private.changeState(self,"false");
								settings.onUnCheck(self);
								settings.onChange(self,"false");
							}
						}else{
							$(self).data("greathackChecked",$(self).attr("greathackChecked"));
						}
					}
				}
			},
			listener:function (){
				var self=this;
				listenerInterval = setInterval(function() {//实时监听
					checkboxElm.each(function(index, element) {
						var self=this;
						var currentIsCheck=$(self).attr("greathackChecked");
						if(currentIsCheck!=$(self).data("greathackChecked")){
							$(self).data("greathackChecked",currentIsCheck);
							if(currentIsCheck=="true"){
								private.check(self);
							}else{
								private.unCheck(self);
							}
						}
						
					});
				},
				500);
			},
			init:function(){		
				var checkedNum=0;		
				checkboxElm.each(function(index, element) {
					var self=this;
					if(firstItem==null){
						firstItem=self;
					}
					if($(self).attr("checkboxType")!="single"){
						$(self).attr("checkboxType",settings.checkboxType);
					}
					if($(self).attr("isLeastOne")!="true"){
						$(self).attr("isLeastOne",settings.isLeastOne);
					}
					if($(self).attr("greathackChecked")!="true"){
						$(self).attr("greathackChecked","false");
						settings.onUnCheck(self);
					}else{
						checkedNum++;
						settings.onCheck(self);
					}
					$(self).data("greathackChecked",$(self).attr("greathackChecked"));
                });
				if(settings.isLeastOne==true && checkedNum==0){
					private.check(firstItem);
				}
			}
		};
		var Checkbox = {
			unCheckAll:function(){
				if(settings.isLeastOne==false){//只有在非必选的情况下才能使用此方法
					private.unCheckAll();
				}else{
					alert("这是必选项,不能取消全部");
				}
			},
			checkAll:function(){
				if(settings.checkboxType!="single"){//只有在多选的情况下才能使用此方法
					private.checkAll();
				}else{
					alert("这是单选项,不能选择全部");
				}
			},
			reverse:function(){
				if(settings.checkboxType!="single"){//只有在多选的情况下才能使用此方法
					private.reverse();
				}else{
					alert("这是单选项,不能反选");
				}
			},
			check:function (self){
				private.check(self);
			},
			unCheck:function (self){
				private.unCheck(self);
			},
			toggle:function (self){
				if($(self).attr("greathackChecked")=="true"){
					private.unCheck(self);					
				}else{
					private.check(self);
				}
				return $(self).attr("greathackChecked");
			},
			returnFunc:function(){
				private.init();//检测
				var self=this;
				/*
				checkboxElm.each(function(index, element) {
					var self=this;
					$(self).unbind();
					$(self).delegate('','click',function(){
						if($(self).attr("greathackChecked")=="true"){
							private.unCheck(self);
							alert("uncheck");
						}else{
							private.check(self);
							alert("check");
						}
					});
                });
				*/
				return self;
			}
		};
		return Checkbox.returnFunc();
		
	};		
})(jQuery);

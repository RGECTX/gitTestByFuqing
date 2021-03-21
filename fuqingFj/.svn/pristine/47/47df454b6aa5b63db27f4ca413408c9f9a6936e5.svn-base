// JavaScript Document
(function($) {
    $.fn.greathackTimer = function(options) {
        var timerElm = this;
        var settings = {
            'milliseconds': 60000,//最大值为86400000(1天),单位毫秒
            'interval': 10,//间隔,单位毫秒
			'step': 5000,//间隔多久执行一次everyStepCallback,单位毫秒
			'stepTimes':1,//从第几次开始每隔step执行一次everyStepCallback
            'timeFormat': 'hh:mm:ss.SSS',//计时格式,hh:小时,mm:分钟,ss:秒,SSS:毫秒, seconds:全部转成秒来计时
            'startButton': '',
            'resetButton': '',
            stopTimerCallback: function(currentMilliSeconds) {},
            startTimerCallback: function(currentMilliSeconds) {},
            resetTimerCallback: function(currentMilliSeconds) {},
			everyIntervalCallback: function(currentMilliSeconds) {},//每个间隔执行
            everyStepCallback: function(currentMilliSeconds) {},
            'isCountDown': true,//倒计时还是正计时
			'isShow': true//是否显示
        };
        if (options) {
            $.extend(settings, options);
        }
        var timeInterval = null;
        var GreathackTimer = {
			UTCTime:new Date().getTimezoneOffset()*60*1000,
			currentMilliSeconds: settings.milliseconds,
			beginTime: new Date().getTime()-this.UTCTime,
			endTime: new Date().getTime() + settings.milliseconds-this.UTCTime,
			init:function() {
				var self = this;
				self.currentMilliSeconds=settings.milliseconds;
				if (settings.isCountDown) {
					clearInterval(timeInterval);
					timeInterval = null;
					if(settings.isShow){
						timerElm.textShow(self.timerFormat(new Date(settings.milliseconds+self.UTCTime),settings.timeFormat));
					}
				} else {
					clearInterval(timeInterval);
					timeInterval = null;
					if(settings.isShow){
						timerElm.textShow(self.timerFormat(new Date(0+self.UTCTime),settings.timeFormat));
					}
				}
				if (settings.startButton || settings.resetButton) {
                    self.bindEvents();
                } else {
                    self.startTimer();
                }
			},
			startDownInterval: function() {
                var self = this;
				timeInterval = setInterval(function() {
					self.updateTimeDown(self.endTime);
					settings.everyIntervalCallback(self.currentMilliSeconds);
				},
				settings.interval);
            },
            startUpInterval: function() {
                var self = this;
				timeInterval = setInterval(function() {
					self.updateTimeUp(self.beginTime);
					settings.everyIntervalCallback(self.currentMilliSeconds);
				},
				settings.interval);
                
            },
			timerFormat:function(date,format){
				var o = {
					"h+" : date.getHours(),   //hour
					"m+" : date.getMinutes(), //minute
					"s+" : date.getSeconds(), //second
					"S+" : date.getMilliseconds() //millisecond
				};
				if (/(seconds)/.test(format)) format = format.replace(RegExp.$1,Math.floor((date.getTime()-this.UTCTime)/1000));
				if (/(S+)/.test(format)) format = format.replace(RegExp.$1, ("000"+date.getMilliseconds()).substr(("000"+date.getMilliseconds()).length-3,RegExp.$1.length));
				for (var k in o) if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
				return format;
			},
			updateTimeDown:function(endTime){
				var self = this;
				var now=new Date().getTime();
				self.currentMilliSeconds=endTime-now;
				if(settings.isShow){
					timerElm.textShow(self.timerFormat(new Date(self.currentMilliSeconds+self.UTCTime),settings.timeFormat));
				}
				if(now-self.beginTime>=settings.step*settings.stepTimes){
					settings.stepTimes++;
					settings.everyStepCallback(self.currentMilliSeconds);
				}
				if(endTime-now<=0){//倒数完毕
					clearInterval(timeInterval);
					timeInterval = null;
					self.currentMilliSeconds=0;
					if(settings.isShow){
						timerElm.textShow(self.timerFormat(new Date(0+self.UTCTime),settings.timeFormat));
					}
					self.pauseTimer();
				}
			},
			updateTimeUp:function(beginTime){
				var self = this;				
				var now=new Date().getTime();
				self.currentMilliSeconds=settings.milliseconds+beginTime-now;
				if(settings.isShow){
					timerElm.textShow(self.timerFormat(new Date(settings.milliseconds-self.currentMilliSeconds+self.UTCTime),settings.timeFormat));
				}
				if(now-beginTime>=settings.step*settings.stepTimes){
					settings.stepTimes++;
					settings.everyStepCallback(self.currentMilliSeconds);
				}
				if(now-beginTime>=settings.milliseconds){//正数完毕
					clearInterval(timeInterval);
					timeInterval = null;
					self.currentMilliSeconds=0;
					if(settings.isShow){
						timerElm.textShow(self.timerFormat(new Date(settings.milliseconds+self.UTCTime),settings.timeFormat));
					}
					self.pauseTimer();
				}
			},
            pauseTimer: function() {//暂停
				var self = this;
                clearInterval(timeInterval);
                timeInterval = null;
                settings.stopTimerCallback(self.currentMilliSeconds);
            },
            startTimer: function() {//运行
				var self = this;
				self.beginTime=new Date().getTime();
				self.endTime=new Date().getTime()+ self.currentMilliSeconds;
                if (settings.isCountDown) {
					endTime=new Date().getTime() + self.currentMilliSeconds,
                    self.startDownInterval();
                } else {
                    self.startUpInterval();
                }
                settings.startTimerCallback(this.currentMilliSeconds);
            },
            resetTimer: function() {
                clearInterval(timeInterval);
                timeInterval = null;
                this.init();
                settings.resetTimerCallback(this.currentMilliSeconds);
            },
            setStartStop: function(direction) {
                var self = this;
                $(settings.startButton).unbind('click');
                if (direction == 'stop') {
                    $(settings.startButton).bind('click',
                    function() {
                        self.pauseTimer();
                        self.setStartStop();
                        if (this.nodeName == 'A') {
                            return false;
                        }
                    });
                } else {
                    $(settings.startButton).bind('click',
                    function() {
                        self.startTimer();
                        self.setStartStop('stop');
                        if (this.nodeName == 'A') {
                            return false;
                        }
                    });
                }
            },
            bindEvents: function() {
                var self = this;
                self.setStartStop();
                if (settings.resetButton) {
                    $(settings.resetButton).bind('click',
                    function() {
                        self.resetTimer();
                        if (this.nodeName == 'A') {
                            return false;
                        }
                    });
                }
            },
            destroy: function() {
                clearInterval(timeInterval);
				timeInterval = null;
				if(settings.isShow){
               		timerElm.empty();
				}
				this.currentMilliSeconds = 0;
            },			
			returnFunc:function(){
				var self=this;
				//timerElm.each(function() {
					self.init();
				//});
				return self;
			}			
		};
        return GreathackTimer.returnFunc();
	};
})(jQuery);


(function($) {
    $.fn.textShow = function(text) {
		return this.each(function() {
            if(this.nodeName.toLowerCase() == 'input'){
				$(this).val(text);
			}else{
				$(this).html(text);
			}
        });
	}
})(jQuery);

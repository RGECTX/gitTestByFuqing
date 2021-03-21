// JavaScript Document
//上传图片预览
//
/*
使用格式:
$('#文件域id').uploadImagePreview({"#要显示的img标签id"});

*/
(function($) {
	$.fn.uploadImagePreview = function(options) {
		var uploadImageElm = this;
		var imgSrc=null;
		var settings={
			'imgElm':'',	//	要显示的img选择器
			onChange:function(e){}//改变事件
		};
		var UploadImagePreview={
			getImage:function(){
				return imgSrc;
			}
		};
		$(function(){
			uploadImageElm.change(function(){
				var file = this.files[0];
				var r = new FileReader();
				r.readAsDataURL(file);
				$(r).load(function(){
					settings.imgElm.attr("src",this.result);
					imgSrc=this.result;
				})
			});
		});
		return UploadImagePreview;
	};	
})(jQuery);

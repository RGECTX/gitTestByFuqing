//调用微信JS api 支付
function jsApiCall()
{
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest',
		jsApiParameters,
		function(res){
			//WeixinJSBridge.log(res.err_msg);
			if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				location.href=returnUrl;
			}
			//alert(res.err_code+"|"+res.err_desc+"|"+res.err_msg);
		}
	);
}
function callpay()
{
	if (typeof WeixinJSBridge == "undefined"){
		if( document.addEventListener ){
			document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
		}else if (document.attachEvent){
			document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
			document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
		}
	}else{
		jsApiCall();
	}
}
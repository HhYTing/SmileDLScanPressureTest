package com.telpo.wxpay.app.api.alipayapi.bean;


public class AlipayRespond {

	private String sign;// 签名 String 否 请参照安全规范中的签名部分
						// fc209d86b57b2adb23254a897ebaa7d9
	private Respond alipay_trade_precreate_response, // 扫二维码预支付response
			alipay_trade_query_response, // 查询response
			alipay_trade_pay_response,// 条形码response
			alipay_trade_cancel_response, // 订单response
			alipay_trade_refund_response;// 退款response

	public Respond getAlipay_trade_precreate_response() {
		return alipay_trade_precreate_response;
	}

	public Respond getAlipay_trade_pay_response() {
		return alipay_trade_pay_response;
	}

	public void setAlipay_trade_pay_response(Respond alipay_trade_pay_response) {
		this.alipay_trade_pay_response = alipay_trade_pay_response;
	}

	public void setAlipay_trade_precreate_response(Respond alipay_trade_precreate_response) {
		this.alipay_trade_precreate_response = alipay_trade_precreate_response;
	}

	public Respond getAlipay_trade_query_response() {
		return alipay_trade_query_response;
	}

	public void setAlipay_trade_query_response(Respond alipay_trade_query_response) {
		this.alipay_trade_query_response = alipay_trade_query_response;
	}

	public Respond getAlipay_trade_cancel_response() {
		return alipay_trade_cancel_response;
	}

	public void setAlipay_trade_cancel_response(Respond alipay_trade_cancel_response) {
		this.alipay_trade_cancel_response = alipay_trade_cancel_response;
	}

	public Respond getAlipay_trade_refund_response() {
		return alipay_trade_refund_response;
	}

	public void setAlipay_trade_refund_response(Respond alipay_trade_refund_response) {
		this.alipay_trade_refund_response = alipay_trade_refund_response;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


}

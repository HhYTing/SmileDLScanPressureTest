package com.telpo.wxpay.app.api.alipayapi.bean;

public class Respond {
	private String code;// 结果码 String(32) 是 业务处理结果。参照 “结果码” 10000
	
	private String msg;// 结果码描述 String(256) 是 业务处理结果描述 订单支付成功
	
	private String sub_code;// 错误子代码 String(64) 否 对结果码进行原因说明， 当业务
							//结果为 10000 时，不返回该参数。
							//详细可参考:“错误码” ACQ.INVALID_PARAMETER
	
	private String sub_msg;// 错误子代码描述 String(256) 否 错误子代码描述信息。
						   //	当业务结果为10000 时，不返回该参数。
	private String qr_code;// 二维码连接
	
	private String out_trade_no;// 交易号
	
	private String trade_status;//交易状态
	
	private String trade_no;//  支付宝交易号String（64）否
	
	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	
	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getSub_msg() {
		return sub_msg;
	}

	public void setSub_msg(String sub_msg) {
		this.sub_msg = sub_msg;
	}

}

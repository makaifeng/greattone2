package com.greattone.greattone.wxpay;

public class WxUnifiedorder {
	String Appid;// 公众账号ID
	String Code_url;// 二维码连接
	String Device_info;// 设备号
	String Err_code;// 错误代码
	String Err_code_des;// 错误代码描述
	String Mch_id;// 商户号
	String Nonce_str;// 随机字符串
	String Prepay_id;// 预支付交易会话标识
	String Result_code;// 业务结果
	String Return_code;// 返回状态码
	String Return_msg;// 返回信息
	String Sign;// 签名
	String Trade_type;// 交易类型
	public String getAppid() {
		return Appid;
	}
	public void setAppid(String appid) {
		Appid = appid;
	}
	public String getCode_url() {
		return Code_url;
	}
	public void setCode_url(String code_url) {
		Code_url = code_url;
	}
	public String getDevice_info() {
		return Device_info;
	}
	public void setDevice_info(String device_info) {
		Device_info = device_info;
	}
	public String getErr_code() {
		return Err_code;
	}
	public void setErr_code(String err_code) {
		Err_code = err_code;
	}
	public String getErr_code_des() {
		return Err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		Err_code_des = err_code_des;
	}
	public String getMch_id() {
		return Mch_id;
	}
	public void setMch_id(String mch_id) {
		Mch_id = mch_id;
	}
	public String getNonce_str() {
		return Nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		Nonce_str = nonce_str;
	}
	public String getPrepay_id() {
		return Prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		Prepay_id = prepay_id;
	}
	public String getResult_code() {
		return Result_code;
	}
	public void setResult_code(String result_code) {
		Result_code = result_code;
	}
	public String getReturn_code() {
		return Return_code;
	}
	public void setReturn_code(String return_code) {
		Return_code = return_code;
	}
	public String getReturn_msg() {
		return Return_msg;
	}
	public void setReturn_msg(String return_msg) {
		Return_msg = return_msg;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	public String getTrade_type() {
		return Trade_type;
	}
	public void setTrade_type(String trade_type) {
		Trade_type = trade_type;
	}

}

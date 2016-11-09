package com.greattone.greattone.allpay;

import java.util.HashMap;
import java.util.Map;

public class BackgroundOTP extends BackgroundBase {
	private String OtpCode;	
	private String TradeNo;
	
	
	public void setOtpCode(String OtpCode) { this.OtpCode = OtpCode; }
	public String getOtpCode() { return this.OtpCode; }
	
	public void setTradeNo(String TradeNo) { this.TradeNo = TradeNo; }
	public String getTradeNo() { return this.TradeNo; }
	
	
	public BackgroundOTP(String MerchantID, String MerchantTradeNo, String TradeNo, String OtpCode, ENVIRONMENT Environment) {
		super(MerchantID, "", MerchantTradeNo, "", 0, "", "", null, Environment);
		// TODO Auto-generated constructor stub
		this.OtpCode = OtpCode;
		this.TradeNo = TradeNo;
		
	}public BackgroundOTP(String MerchantID, String PlatformID, String MerchantTradeNo, String TradeNo, String OtpCode, ENVIRONMENT Environment) {
		super(MerchantID, "", MerchantTradeNo, "", 0, "", "", null, Environment, PlatformID);
		// TODO Auto-generated constructor stub
		this.OtpCode = OtpCode;
		this.TradeNo = TradeNo;
		
	}
	
	
	public Map<String, String> getPostData(){
		Map<String, String> mapParams = new HashMap<String, String>();		
		mapParams.put("MerchantID", this.getMerchantID());
		mapParams.put("MerchantTradeNo", this.getMerchantTradeNo());
		mapParams.put("TradeNo", this.getTradeNo());
		mapParams.put("OtpCode", this.getOtpCode());
		
		return mapParams;
	}
	
	
}

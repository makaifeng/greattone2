package com.greattone.greattone.allpay;

import java.util.HashMap;
import java.util.Map;

public class BackgroundBase {
	private String MerchantID;
	private String AppCode;
	private String MerchantTradeNo;
	private String MerchantTradeDate;
	private Integer TotalAmount;
	private String TradeDesc;
	private String ItemName;
	private PAYMENTTYPE ChoosePayment;	
	private ENVIRONMENT Environment;
	private String PlatformID;
	private String PlatformChargeFee;
	private String PlatformMemberNo;
	
	
	public void setMerchantID(String MerchantID) { this.MerchantID = MerchantID; }
	public String getMerchantID() { return this.MerchantID; }
	
	public void setAppCode(String AppCode) { this.AppCode = AppCode; }
	public String getAppCode() { return this.AppCode; }
	
	public void setMerchantTradeNo(String MerchantTradeNo) { this.MerchantTradeNo = MerchantTradeNo; }
	public String getMerchantTradeNo() { return this.MerchantTradeNo; }
	
	public void setMerchantTradeDate(String MerchantTradeDate) { this.MerchantTradeDate = MerchantTradeDate; }
	public String getMerchantTradeDate() { return this.MerchantTradeDate; }
	
	public void setTotalAmount(Integer TotalAmount) { this.TotalAmount = TotalAmount; }
	public Integer getTotalAmount() { return this.TotalAmount; }
	
	public void setTradeDesc(String TradeDesc) { this.TradeDesc = TradeDesc; }
	public String getTradeDesc() { return this.TradeDesc; }
	
	public void setItemName(String ItemName) { this.ItemName = ItemName; }
	public String getItemName() { return this.ItemName; }	
	
	public void setChoosePayment(PAYMENTTYPE ChoosePayment) { this.ChoosePayment = ChoosePayment; }
	public PAYMENTTYPE getChoosePayment() { return this.ChoosePayment; }
	
	public void setEnvironment(ENVIRONMENT Environment) { this.Environment = Environment; }
	public ENVIRONMENT getEnvironment() { return this.Environment; }
	
	public void setPlatformID(String PlatformID) { this.PlatformID = PlatformID; }
	public String getPlatformID() { return this.PlatformID; }
	
	public void setPlatformChargeFee(String PlatformChargeFee) { this.PlatformChargeFee = PlatformChargeFee; }
	public String getPlatformChargeFee() { return this.PlatformChargeFee; }
	
	public void setPlatformMemberNo(String PlatformMemberNo) { this.PlatformMemberNo = PlatformMemberNo; }
	public String getPlatformMemberNo() { return this.PlatformMemberNo; }
	
	
	public BackgroundBase(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			String PlatformID, String PlatformChargeFee, String PlatformMemberNo){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.PlatformID = PlatformID;
		this.PlatformChargeFee = PlatformChargeFee;
		this.PlatformMemberNo = PlatformMemberNo;
	}
	public BackgroundBase(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, String PlatformID, String PlatformChargeFee){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.PlatformID = PlatformID;
		this.PlatformChargeFee = PlatformChargeFee;
	}
	public BackgroundBase(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, String PlatformID){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.PlatformID = PlatformID;
	}
	public BackgroundBase(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
						String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment){
		this.MerchantID = MerchantID;
		this.AppCode = AppCode;
		this.MerchantTradeNo = MerchantTradeNo;
		this.MerchantTradeDate = MerchantTradeDate;
		this.TotalAmount = TotalAmount;
		this.TradeDesc = TradeDesc;
		this.ItemName = ItemName;
		this.ChoosePayment = ChoosePayment;
		this.Environment = Environment;	
	}
	
	
	protected Map<String, String> getPostData(){
		Map<String, String> mapParams = new HashMap<String, String>();		
		mapParams.put("MerchantID", this.getMerchantID());
		mapParams.put("AppCode", this.getAppCode());
		mapParams.put("MerchantTradeNo", this.getMerchantTradeNo());
		mapParams.put("MerchantTradeDate", this.getMerchantTradeDate());
		mapParams.put("TotalAmount",String.valueOf(this.getTotalAmount()) );
		mapParams.put("TradeDesc", this.getTradeDesc());
		mapParams.put("ItemName", this.getItemName());
		mapParams.put("ChoosePayment", this.getChoosePayment().toString());
		if(this.PlatformID != null && this.PlatformID.length() > 0)
			mapParams.put("PlatformID", this.PlatformID);
		
		if(this.PlatformChargeFee != null && this.PlatformChargeFee.length() > 0)
			mapParams.put("PlatformChargeFee", this.PlatformChargeFee);
		
		if(this.PlatformMemberNo != null && this.PlatformMemberNo.length() > 0)
			mapParams.put("PlatformMemberNo", this.PlatformMemberNo);
		
		return mapParams;
	}
	
	
	public String getJSON() throws Exception
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
}

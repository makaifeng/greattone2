package com.greattone.greattone.allpay;

import java.util.Map;

public class BackgroundATM extends BackgroundBase {
	private BANKNAME BankName;
	private Integer ExpireDate;
	
	
	public void setBankName(BANKNAME BankName) { this.BankName = BankName; }
	public BANKNAME getBankName() { return this.BankName; }
	
	public void setExpireDate(Integer ExpireDate) { this.ExpireDate = ExpireDate; }
	public Integer getExpireDate() { return this.ExpireDate; }
	
	
	public BackgroundATM(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName) {
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc
				, ItemName,  ChoosePayment, Environment, BankName, 0);
	}
	public BackgroundATM(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName, Integer ExpireDate) {
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		// TODO Auto-generated constructor stub
		this.BankName = BankName;
		this.ExpireDate = ExpireDate;	
	}
	public BackgroundATM(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName){
		this(MerchantID, PlatformID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc
				, ItemName,  ChoosePayment, Environment, BankName, 0);
	}
	public BackgroundATM(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName){
		this(MerchantID, PlatformID, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc
				, ItemName,  ChoosePayment, Environment, BankName, 0);
	}
	public BackgroundATM(String MerchantID, String PlatformID, String PlatformMemberNo, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName){
		this(MerchantID, PlatformID, PlatformMemberNo, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc
				, ItemName,  ChoosePayment, Environment, BankName, 0);
	}
	public BackgroundATM(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName, Integer ExpireDate){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID);
		this.BankName = BankName;
		this.ExpireDate = ExpireDate;
	}
	public BackgroundATM(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName, Integer ExpireDate){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID, PlatformChargeFee);
		this.BankName = BankName;
		this.ExpireDate = ExpireDate;
	}
	public BackgroundATM(String MerchantID, String PlatformID, String PlatformMemberNo, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, 
			BANKNAME BankName, Integer ExpireDate){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID, PlatformChargeFee, PlatformMemberNo);
		this.BankName = BankName;
		this.ExpireDate = ExpireDate;
	}
	

	public Map<String, String> getPostData(){
		Map<String, String> mapParams = super.getPostData();
		mapParams.put("ChooseSubPayment", this.getBankName().toString());
		if(this.getExpireDate() > 0 && this.getExpireDate() <=60){
			mapParams.put("ExpireDate", String.valueOf(this.getExpireDate()));
		}
		
		
		return mapParams;
	}
	

}

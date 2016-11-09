package com.greattone.greattone.allpay;

import java.util.Map;

public class BackgroundCVS extends BackgroundBase {
	private String Desc_1;
	private String Desc_2;
	private String Desc_3;
	private String Desc_4;
	private STORETYPE StoreType;
	
	
	public void setDesc_1(String Desc_1) { this.Desc_1 = Desc_1; }
	public String getDesc_1() { return this.Desc_1; }
	
	public void setDesc_2(String Desc_2) { this.Desc_2 = Desc_2; }
	public String getDesc_2() { return this.Desc_2; }
	
	public void setDesc_3(String Desc_3) { this.Desc_3 = Desc_3; }
	public String getDesc_3() { return this.Desc_3; }
	
	public void setDesc_4(String Desc_4) { this.Desc_4 = Desc_4; }
	public String getDesc_4() { return this.Desc_4; }
	
	public void setStoreType(STORETYPE StoreType){this.StoreType = StoreType;}
	public STORETYPE getStoreType(){return this.StoreType;}
	
	
	public BackgroundCVS(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, "", "", "", "");
	}
	public BackgroundCVS(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, "", "", "");
	}
	public BackgroundCVS(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, Desc_2, "", "");
	}
	public BackgroundCVS(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2, String Desc_3){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, Desc_2, Desc_3, "");
	}
	public BackgroundCVS(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
						String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
						String Desc_1, String Desc_2, String Desc_3, String Desc_4){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.StoreType = StoreType;
		this.Desc_1 = Desc_1;
		this.Desc_2 = Desc_2;
		this.Desc_3 = Desc_3;
		this.Desc_4 = Desc_4;
	}
	
	public BackgroundCVS(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType){
		this(MerchantID, PlatformID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, "", "", "", "");
	}
	public BackgroundCVS(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType){
		this(MerchantID, PlatformID, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, "", "", "", "");
	}
	
	public BackgroundCVS(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1){
		this(MerchantID, PlatformID, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, "", "", "");
	}
	public BackgroundCVS(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1){
		this(MerchantID, PlatformID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, "", "", "");
	}
	
	public BackgroundCVS(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2){
		this(MerchantID, PlatformID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, Desc_2, "", "");
	}
	public BackgroundCVS(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2){
		this(MerchantID, PlatformID, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, Desc_2, "", "");
	}
	
	public BackgroundCVS(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2, String Desc_3){
		this(MerchantID, PlatformID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, Desc_2, Desc_3, "");
	}
	public BackgroundCVS(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2, String Desc_3){
		this(MerchantID, PlatformID, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, StoreType, Desc_1, Desc_2, Desc_3, "");
	}
	
	public BackgroundCVS(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2, String Desc_3, String Desc_4){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID);
		this.StoreType = StoreType;
		this.Desc_1 = Desc_1;
		this.Desc_2 = Desc_2;
		this.Desc_3 = Desc_3;
		this.Desc_4 = Desc_4;
	}
	public BackgroundCVS(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2, String Desc_3, String Desc_4){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID, PlatformChargeFee);
		this.StoreType = StoreType;
		this.Desc_1 = Desc_1;
		this.Desc_2 = Desc_2;
		this.Desc_3 = Desc_3;
		this.Desc_4 = Desc_4;
	}
	public BackgroundCVS(String MerchantID, String PlatformID, String PlatformMemberNo, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount,
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment, STORETYPE StoreType,
			String Desc_1, String Desc_2, String Desc_3, String Desc_4){
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID, PlatformChargeFee, PlatformMemberNo);
		this.StoreType = StoreType;
		this.Desc_1 = Desc_1;
		this.Desc_2 = Desc_2;
		this.Desc_3 = Desc_3;
		this.Desc_4 = Desc_4;
	}
	
	
	public Map<String, String> getPostData(){
		Map<String, String> mapParams = super.getPostData();
		mapParams.put("ChooseSubPayment", this.getStoreType().toString());
		if(this.getDesc_1() != null && !this.getDesc_1().equalsIgnoreCase("")){
			mapParams.put("Desc_1", this.getDesc_1());
		}
		if(this.getDesc_2() != null && !this.getDesc_2().equalsIgnoreCase("")){
			mapParams.put("Desc_2", this.getDesc_2());
		}
		if(this.getDesc_3() != null && !this.getDesc_3().equalsIgnoreCase("")){
			mapParams.put("Desc_3", this.getDesc_3());
		}
		if(this.getDesc_4() != null && !this.getDesc_4().equalsIgnoreCase("")){
			mapParams.put("Desc_4", this.getDesc_4());
		}
		
		return mapParams;
	}
	
	
	
}

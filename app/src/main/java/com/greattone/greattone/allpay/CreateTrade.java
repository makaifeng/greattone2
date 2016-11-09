package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class CreateTrade implements Parcelable {
	private String MerchantID;
	private String PlatformID;
	private String PlatformMemberNo;
	private String PlatformChargeFee;
	private String AppCode;
	private String MerchantTradeNo;
	private String MerchantTradeDate;
	private Integer TotalAmount;
	private String TradeDesc;
	private String ItemName;
	private PAYMENTTYPE ChoosePayment;	
	private ENVIRONMENT Environment;
		
	
	public void setMerchantID(String MerchantID) { this.MerchantID = MerchantID; }
	public String getMerchantID() { return this.MerchantID; }
	
	public void setPlatformID(String PlatformID) { this.PlatformID = PlatformID; }
	public String getPlatformID() { return this.PlatformID; }
	
	public void setPlatformMemberNo(String PlatformMemberNo) { this.PlatformMemberNo = PlatformMemberNo; }
	public String getPlatformMemberNo() { return this.PlatformMemberNo; }
	
	public void setPlatformChargeFee(String PlatformChargeFee) { this.PlatformChargeFee = PlatformChargeFee; }
	public String getPlatformChargeFee() { return this.PlatformChargeFee; }
	
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
		
	
	/**
	 * 訂單產生(幕前)
	 * @param MerchantID : 廠商編號
	 * @param PlatformID : 特約合作平台商代號
	 * @param AppCode : App代碼
	 * @param MerchantTradeNo : 廠商交易編號(不可重複)
	 * @param MerchantTradeDate : 廠商交易時間(格式yyyy/MM/dd HH:mm:ss)
	 * @param TotalAmount : 交易金額
	 * @param TradeDesc : 交易描述
	 * @param ItemName : 商品名稱(如有多筆，請以#號分隔)
	 * @param ChoosePayment : 預設付款方式
	 * @param Environment : 開發環境	
	 */
	public CreateTrade(String MerchantID, String PlatformID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment){
		
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.PlatformID = PlatformID;
	}
	/**
	 * 訂單產生(幕前)
	 * @param MerchantID : 廠商編號
	 * @param PlatformID : 特約合作平台商代號
	 * @param PlatformMemberNo : 特約合作平台商會員編號
	 * @param PlatformChargeFee : 特約合作平台商手續費
	 * @param AppCode : App代碼
	 * @param MerchantTradeNo : 廠商交易編號(不可重複)
	 * @param MerchantTradeDate : 廠商交易時間(格式yyyy/MM/dd HH:mm:ss)
	 * @param TotalAmount : 交易金額
	 * @param TradeDesc : 交易描述
	 * @param ItemName : 商品名稱(如有多筆，請以#號分隔)
	 * @param ChoosePayment : 預設付款方式
	 * @param Environment : 開發環境	
	 */
	public CreateTrade(String MerchantID, String PlatformID, String PlatformMemberNo, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment){
		
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.PlatformID = PlatformID;
		this.PlatformChargeFee = PlatformChargeFee;
		this.PlatformMemberNo = PlatformMemberNo;
	}
	/**
	 * 訂單產生(幕前)
	 * @param MerchantID : 廠商編號
	 * @param PlatformID : 特約合作平台商代號
	 * @param PlatformChargeFee : 特約合作平台商手續費
	 * @param AppCode : App代碼
	 * @param MerchantTradeNo : 廠商交易編號(不可重複)
	 * @param MerchantTradeDate : 廠商交易時間(格式yyyy/MM/dd HH:mm:ss)
	 * @param TotalAmount : 交易金額
	 * @param TradeDesc : 交易描述
	 * @param ItemName : 商品名稱(如有多筆，請以#號分隔)
	 * @param ChoosePayment : 預設付款方式
	 * @param Environment : 開發環境	
	 */
	public CreateTrade(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode, String MerchantTradeNo, String MerchantTradeDate, Integer TotalAmount, 
			String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment){
		
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		this.PlatformID = PlatformID;
		this.PlatformChargeFee = PlatformChargeFee;
	}
	/**
	 * 訂單產生(幕前)
	 * @param MerchantID : 廠商編號
	 * @param AppCode : App代碼
	 * @param MerchantTradeNo : 廠商交易編號(不可重複)
	 * @param MerchantTradeDate : 廠商交易時間(格式yyyy/MM/dd HH:mm:ss)
	 * @param TotalAmount : 交易金額
	 * @param TradeDesc : 交易描述
	 * @param ItemName : 商品名稱(如有多筆，請以#號分隔)
	 * @param ChoosePayment : 預設付款方式
	 * @param Environment : 開發環境
	 */
	public CreateTrade(String MerchantID, String AppCode, String MerchantTradeNo, String MerchantTradeDate, 
					Integer TotalAmount, String TradeDesc, String ItemName, PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment){		
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
	private CreateTrade(){}
	
	
	public Collection<Map.Entry<String, String>> getPostData(){
		Map<String, String> mapParams = new HashMap<String, String>();		
		mapParams.put("MerchantID", this.MerchantID);
		mapParams.put("AppCode", this.AppCode);
		mapParams.put("MerchantTradeNo", this.MerchantTradeNo);
		mapParams.put("MerchantTradeDate", this.MerchantTradeDate);
		mapParams.put("TotalAmount",String.valueOf(this.TotalAmount) );
		mapParams.put("TradeDesc", this.TradeDesc);
		mapParams.put("ItemName", this.ItemName);
		mapParams.put("ChoosePayment", this.ChoosePayment.toString());
		if(this.PlatformID != null && this.PlatformID.length() > 0)
			mapParams.put("PlatformID", this.PlatformID);
		
		if(this.PlatformChargeFee != null && this.PlatformChargeFee.length() > 0)
			mapParams.put("PlatformChargeFee", String.valueOf(this.PlatformChargeFee));
		
		if(this.PlatformMemberNo != null && this.PlatformMemberNo.length() > 0)
			mapParams.put("PlatformMemberNo", String.valueOf(this.PlatformMemberNo));
		
		return mapParams.entrySet();
	}
	
	
	public String getJSON() throws Exception
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Creator<CreateTrade> CREATOR = new Creator(){

		@Override
		public CreateTrade createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			CreateTrade obj = new CreateTrade();
			obj.setMerchantID(source.readString());
			obj.setPlatformID(source.readString());
			obj.setPlatformMemberNo(source.readString());
			obj.setPlatformChargeFee(source.readString());
			obj.setAppCode(source.readString());
			obj.setMerchantTradeNo(source.readString());
			obj.setMerchantTradeDate(source.readString());
			obj.setTotalAmount(Integer.valueOf(source.readString()));
			obj.setTradeDesc(source.readString());
			obj.setItemName(source.readString());		
			
			String sPaymentType = source.readString();
			if(sPaymentType != null){
				if(sPaymentType.equalsIgnoreCase(PAYMENTTYPE.ALL.toString())){
					obj.setChoosePayment(PAYMENTTYPE.ALL);
				}else if(sPaymentType.equalsIgnoreCase(PAYMENTTYPE.ATM.toString())){
					obj.setChoosePayment(PAYMENTTYPE.ATM);
				}else if(sPaymentType.equalsIgnoreCase(PAYMENTTYPE.CVS.toString())){
					obj.setChoosePayment(PAYMENTTYPE.CVS);
				}else if(sPaymentType.equalsIgnoreCase(PAYMENTTYPE.CREDIT.toString())){
					obj.setChoosePayment(PAYMENTTYPE.CREDIT);
				}else{
					obj.setChoosePayment(null);
				}
				
			}else {
				obj.setChoosePayment(null);
			}
			
			String sEnvirment = source.readString();
			if(sEnvirment != null){
				if(sEnvirment.equalsIgnoreCase(ENVIRONMENT.STAGE.toString())){
					obj.setEnvironment(ENVIRONMENT.STAGE);
				}else if(sEnvirment.equalsIgnoreCase(ENVIRONMENT.OFFICIAL.toString())){
					obj.setEnvironment(ENVIRONMENT.OFFICIAL);
				}else{
					obj.setEnvironment(null);
				}
				
			}else {
				obj.setEnvironment(null);
			}
		
			
			return obj;
		}

		@Override
		public CreateTrade[] newArray(int size) {
			// TODO Auto-generated method stub
			
			return new CreateTrade[size];
		}
		
	};
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(MerchantID);
		dest.writeString(PlatformID);
		dest.writeString(PlatformMemberNo);
		dest.writeString(PlatformChargeFee);
		dest.writeString(AppCode);
		dest.writeString(MerchantTradeNo);
		dest.writeString(MerchantTradeDate);
		dest.writeString(String.valueOf(TotalAmount));
		dest.writeString(TradeDesc);
		dest.writeString(ItemName);
		dest.writeString(ChoosePayment == null ? "" : ChoosePayment.toString());
		dest.writeString(Environment == null ? "" : Environment.toString());
	}
	
	
}

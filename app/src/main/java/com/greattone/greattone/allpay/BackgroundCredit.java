package com.greattone.greattone.allpay;
import java.util.Map;

public class BackgroundCredit extends BackgroundBase {
	private String CreditHolder;
	private String PhoneNumber;
	private String CardNumber;
	private String CardValidYM;
	private String CardCVV2;
	private int CreditInstallment;
	private int  InstallmentAmount;
	private boolean Redeem;
	
	public void setCreditHolder(String CreditHolder) { this.CreditHolder = CreditHolder; }
	public String getCreditHolder() { return this.CreditHolder; }
	
	public void setPhoneNumber(String PhoneNumber) { this.PhoneNumber = PhoneNumber; }
	public String getPhoneNumber() { return this.PhoneNumber; }
	
	public void setCardNumber(String CardNumber) { this.CardNumber = CardNumber; }
	public String getCardNumber() { return this.CardNumber; }
	
	public void setCardValidYM(String CardValidYM) { this.CardValidYM = CardValidYM; }
	public String getCardValidYM() { return this.CardValidYM; }
	
	public void setCardCVV2(String CardCVV2) { this.CardCVV2 = CardCVV2; }
	public String getCardCVV2() { return this.CardCVV2; }
	
	public void setCreditInstallment(Integer CreditInstallment){this.CreditInstallment = CreditInstallment;}
	public int getCreditInstallment(){ return this.CreditInstallment; }
	
	public void setInstallmentAmount(Integer InstallmentAmount){this.InstallmentAmount = InstallmentAmount;}
	public int getInstallmentAmount(){ return this.InstallmentAmount; }
	
	public void setRedeem(boolean Redeem){this.Redeem = Redeem;}
	public boolean getRedeem(){ return this.Redeem; }
	
	
	public BackgroundCredit(String MerchantID, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
			Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
			String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2,
			Integer CreditInstallment, Integer  InstallmentAmount, boolean Redeem){
		this(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate,
				TotalAmount, TradeDesc, ItemName,ChoosePayment, Environment,
				CreditHolder, PhoneNumber, CardNumber, CardValidYM, CardCVV2);
		
		this.CreditInstallment = CreditInstallment;
		this.InstallmentAmount = InstallmentAmount;
		this.Redeem = Redeem;
	}	
	public BackgroundCredit(String MerchantID, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
					Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
					String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2) {
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment);
		// TODO Auto-generated constructor stub		
		
		this.CreditHolder = CreditHolder;
		this.PhoneNumber = PhoneNumber;
		this.CardNumber = CardNumber;
		this.CardValidYM = CardValidYM;
		this.CardCVV2 = CardCVV2;
	}
	
	public BackgroundCredit(String MerchantID, String PlatformID, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
			Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
			String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2,
			Integer CreditInstallment, Integer  InstallmentAmount, boolean Redeem){
		this(MerchantID, PlatformID, AppCode, MerchantTradeNo, MerchantTradeDate,
				TotalAmount, TradeDesc, ItemName,ChoosePayment, Environment,
				CreditHolder, PhoneNumber, CardNumber, CardValidYM, CardCVV2);
		
		this.CreditInstallment = CreditInstallment;
		this.InstallmentAmount = InstallmentAmount;
		this.Redeem = Redeem;
	}
	public BackgroundCredit(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
			Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
			String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2,
			Integer CreditInstallment, Integer  InstallmentAmount, boolean Redeem){
		this(MerchantID, PlatformID, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate,
				TotalAmount, TradeDesc, ItemName,ChoosePayment, Environment,
				CreditHolder, PhoneNumber, CardNumber, CardValidYM, CardCVV2);
		
		this.CreditInstallment = CreditInstallment;
		this.InstallmentAmount = InstallmentAmount;
		this.Redeem = Redeem;
	}
	public BackgroundCredit(String MerchantID, String PlatformID, String PlatformMemberNo, String PlatformChargeFee, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
			Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
			String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2,
			Integer CreditInstallment, Integer  InstallmentAmount, boolean Redeem){
		this(MerchantID, PlatformID, PlatformMemberNo, PlatformChargeFee, AppCode, MerchantTradeNo, MerchantTradeDate,
				TotalAmount, TradeDesc, ItemName,ChoosePayment, Environment,
				CreditHolder, PhoneNumber, CardNumber, CardValidYM, CardCVV2);
		
		this.CreditInstallment = CreditInstallment;
		this.InstallmentAmount = InstallmentAmount;
		this.Redeem = Redeem;
	}
	
	public BackgroundCredit(String MerchantID, String PlatformID, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
					Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
					String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2) {
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID);
		// TODO Auto-generated constructor stub		
		
		this.CreditHolder = CreditHolder;
		this.PhoneNumber = PhoneNumber;
		this.CardNumber = CardNumber;
		this.CardValidYM = CardValidYM;
		this.CardCVV2 = CardCVV2;
	}
	public BackgroundCredit(String MerchantID, String PlatformID, String PlatformChargeFee, String AppCode,String MerchantTradeNo, String MerchantTradeDate,
					Integer TotalAmount, String TradeDesc, String ItemName,PAYMENTTYPE ChoosePayment, ENVIRONMENT Environment,
					String CreditHolder, String PhoneNumber, String CardNumber, String CardValidYM, String CardCVV2) {
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate, TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment, PlatformID, PlatformChargeFee);
		// TODO Auto-generated constructor stub		
		
		this.CreditHolder = CreditHolder;
		this.PhoneNumber = PhoneNumber;
		this.CardNumber = CardNumber;
		this.CardValidYM = CardValidYM;
		this.CardCVV2 = CardCVV2;
	}

	public BackgroundCredit(String MerchantID, String PlatformID, String PlatformMemberNo,
			String PlatformChargeFee, String AppCode, String MerchantTradeNo,
			String MerchantTradeDate, Integer TotalAmount, String TradeDesc,
			String ItemName, PAYMENTTYPE ChoosePayment,
			ENVIRONMENT Environment, String CreditHolder, String PhoneNumber,
			String CardNumber, String CardValidYM, String CardCVV2) {
		super(MerchantID, AppCode, MerchantTradeNo, MerchantTradeDate,
				TotalAmount, TradeDesc, ItemName, ChoosePayment, Environment,
				PlatformID, PlatformChargeFee, PlatformMemberNo);
		// TODO Auto-generated constructor stub

		this.CreditHolder = CreditHolder;
		this.PhoneNumber = PhoneNumber;
		this.CardNumber = CardNumber;
		this.CardValidYM = CardValidYM;
		this.CardCVV2 = CardCVV2;
	}
	
	
	public Map<String, String> getPostData(){
		Map<String, String> mapParams = super.getPostData();
		mapParams.put("CreditHolder", this.getCreditHolder());
		mapParams.put("PhoneNumber", this.getPhoneNumber());
		mapParams.put("CardNumber", this.getCardNumber());
//		mapParams.put("CardValidYM", this.getCardValidYM());
		if(this.getCardValidYM() != null && this.getCardValidYM().length() == 6){
			mapParams.put("CardValidYY", this.getCardValidYM().substring(0, 4));
			mapParams.put("CardValidMM", this.getCardValidYM().substring(4, 6));	
		}
		
		mapParams.put("CardCVV2", this.getCardCVV2());
		
		if(this.getCreditInstallment() > 0){
			mapParams.put("CreditInstallment",String.valueOf( this.getCreditInstallment()));			
			if(this.getCreditInstallment() * this.getInstallmentAmount() > this.getTotalAmount())
				mapParams.put("InstallmentAmount", String.valueOf(this.getInstallmentAmount()));			
			if(this.getRedeem())
				mapParams.put("Redeem", "Y");
		}
		
		return mapParams;
	}
	
	
}

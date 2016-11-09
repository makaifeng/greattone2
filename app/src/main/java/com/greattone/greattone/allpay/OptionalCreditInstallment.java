package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class OptionalCreditInstallment implements Parcelable {
	private Integer CreditInstallment;
	private Integer InstallmentAmount;
	private Boolean Redeem;
	private Boolean UnionPay;
	
	
	public void setCreditInstallment(Integer CreditInstallment) { this.CreditInstallment = CreditInstallment; }
	public Integer getCreditInstallment() { return this.CreditInstallment; }
	
	public void setInstallmentAmount(Integer InstallmentAmount) { this.InstallmentAmount = InstallmentAmount; }
	public Integer getInstallmentAmount() { return this.InstallmentAmount; }
	
	public void setRedeem(Boolean Redeem) { this.Redeem = Redeem; }
	public Boolean getRedeem() { return this.Redeem; }
	
	public void setUnionPay(Boolean UnionPay) { this.UnionPay = UnionPay; }
	public Boolean getUnionPay() { return this.UnionPay; }
	
	
	/**
	 * 選擇性參數，信用卡分期
	 * 當ChoosePayment參數為Credit時帶入，不可與信用卡定期定額一起設定
	 * @param CreditInstallment : 刷卡分期期數
	 * @param InstallmentAmount : 使用刷卡分期的付款金額
	 * @param Redeem : 信用卡是否使用紅利折抵
	 * @param UnionPay : 是否為銀聯卡交易
	 */
	public OptionalCreditInstallment(Integer CreditInstallment, Integer InstallmentAmount, Boolean Redeem, Boolean UnionPay){
		this.CreditInstallment = CreditInstallment;
		this.InstallmentAmount = InstallmentAmount;
		this.Redeem = Redeem;
		this.UnionPay = UnionPay;
	}
	private OptionalCreditInstallment(){}
	
	
	public Collection<Map.Entry<String, String>> getPostData(int TotalAmount){
		Map<String, String> mapParams = new HashMap<String, String>();		
		mapParams.put("CreditInstallment", String.valueOf(this.CreditInstallment));
		if(this.CreditInstallment * this.InstallmentAmount > TotalAmount)
			mapParams.put("InstallmentAmount", String.valueOf(this.InstallmentAmount));
		if(this.Redeem)
			mapParams.put("Redeem", "Y");
		mapParams.put("UnionPay", this.UnionPay ? "1" : "0");
		
		return mapParams.entrySet();
	}
	
	
	public String getJSON()
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Creator<OptionalCreditInstallment> CREATOR = new Creator(){

		@Override
		public OptionalCreditInstallment createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			OptionalCreditInstallment obj = new OptionalCreditInstallment();
			obj.setCreditInstallment(Integer.valueOf(source.readString()));
			obj.setInstallmentAmount(Integer.valueOf(source.readString()));
			obj.setRedeem(Boolean.valueOf(source.readString()));
			obj.setUnionPay(Boolean.valueOf(source.readString()));
			
			return obj;
		}

		@Override
		public OptionalCreditInstallment[] newArray(int size) {
			// TODO Auto-generated method stub
			return new OptionalCreditInstallment[size];
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
		dest.writeString(String.valueOf(CreditInstallment));
		dest.writeString(String.valueOf(InstallmentAmount));
		dest.writeString(String.valueOf(Redeem));
		dest.writeString(String.valueOf(UnionPay));
	}
	
	
}

package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;


public class OptionalATM implements Parcelable {
	private Integer ExpireDate;
	private BANKNAME BankName;
	
	
	public void setExpireDate(Integer ExpireDate) { this.ExpireDate = ExpireDate; }
	public Integer getExpireDate() { return this.ExpireDate; }
	
	public void setBankName(BANKNAME BankName) { this.BankName = BankName; }
	public BANKNAME getBankName() { return this.BankName; }
	
	
	public OptionalATM(Integer ExpireDate){
		this(ExpireDate, null);
	}
	public OptionalATM(Integer ExpireDate, BANKNAME BankName){
		this.ExpireDate = ExpireDate;
		this.BankName = BankName;
	}
	private OptionalATM(){}
	
	
	public Collection<Map.Entry<String, String>> getPostData(){
		Map<String, String> mapParams = new HashMap<String, String>();
		if(this.ExpireDate != null && this.ExpireDate > 0)
			mapParams.put("ExpireDate", String.valueOf(this.ExpireDate));
		if( this.BankName != null)
			mapParams.put("ChooseSubPayment", this.BankName.toString());
		
		return mapParams.entrySet();
	}
	
	
	public String getJSON() 
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Creator<OptionalATM> CREATOR = new Creator(){

		@Override
		public OptionalATM createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			OptionalATM obj = new OptionalATM();
			obj.setExpireDate(Integer.valueOf(source.readString()));
			
			String sBankName = source.readString();
			if(sBankName != null){
				if(sBankName.equalsIgnoreCase(BANKNAME.TAISHIN.toString())){
					obj.setBankName(BANKNAME.TAISHIN);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.HUANAN.toString())){
					obj.setBankName(BANKNAME.HUANAN);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.ESUN.toString())){
					obj.setBankName(BANKNAME.ESUN);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.FUBON.toString())){
					obj.setBankName(BANKNAME.FUBON);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.BOT.toString())){
					obj.setBankName(BANKNAME.BOT);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.CHINATRUST.toString())){
					obj.setBankName(BANKNAME.CHINATRUST);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.FIRST.toString())){
					obj.setBankName(BANKNAME.FIRST);
				}else if(sBankName.equalsIgnoreCase(BANKNAME.ESUN_Counter.toString())){
					obj.setBankName(BANKNAME.ESUN_Counter);
				}else{
					obj.setBankName(null);
				}
				
			}else {
				obj.setBankName(null);
			}
			
			return obj;
		}

		@Override
		public OptionalATM[] newArray(int size) {
			// TODO Auto-generated method stub
			return new OptionalATM[size];
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
		dest.writeString(String.valueOf(ExpireDate));
		dest.writeString(BankName == null ? "" : BankName.toString());
	}
	
	
	
}

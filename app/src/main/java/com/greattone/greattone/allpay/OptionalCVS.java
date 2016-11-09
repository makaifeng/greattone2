package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class OptionalCVS implements Parcelable {
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
	
	
	/**
	 * 選擇性參數，當ChoosePayment參數為CVS時帶入
	 * @param Desc_1 : 交易描述1，會出現在超商繳費平台螢幕上
	 * @param Desc_2 : 交易描述2，會出現在超商繳費平台螢幕上
	 * @param Desc_3 : 交易描述3，會出現在超商繳費平台螢幕上
	 * @param Desc_4 : 交易描述4，會出現在超商繳費平台螢幕上
	 * @param StoreType : 付款子項目，超商
	 */
	public OptionalCVS(String Desc_1, String Desc_2,String Desc_3,String Desc_4, STORETYPE StoreType){
		this.Desc_1 = Desc_1;
		this.Desc_2 = Desc_2;
		this.Desc_3 = Desc_3;
		this.Desc_4 = Desc_4;
		this.StoreType = StoreType;
	}
	private OptionalCVS(){};
	
	
	public Collection<Map.Entry<String, String>> getPostData(){
		Map<String, String> mapParams = new HashMap<String, String>();
		if(this.Desc_1 != null && !this.Desc_1.equalsIgnoreCase(""))
			mapParams.put("Desc_1", this.Desc_1);
		if(this.Desc_2 != null && !this.Desc_2.equalsIgnoreCase(""))
			mapParams.put("Desc_2", this.Desc_2);
		if(this.Desc_3 != null && !this.Desc_3.equalsIgnoreCase(""))
			mapParams.put("Desc_3", this.Desc_3);
		if(this.Desc_4 != null && !this.Desc_4.equalsIgnoreCase(""))
			mapParams.put("Desc_4", this.Desc_4);
		if( this.StoreType != null)
			mapParams.put("ChooseSubPayment", this.StoreType.toString());
		
		return mapParams.entrySet();
	}
	
	
	public String getJSON() 
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Creator<OptionalCVS> CREATOR = new Creator(){

		@Override
		public OptionalCVS createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			OptionalCVS obj = new OptionalCVS();
			obj.setDesc_1(source.readString());
			obj.setDesc_2(source.readString());
			obj.setDesc_3(source.readString());
			obj.setDesc_4(source.readString());
			
			String sStoreType = source.readString();
			if(sStoreType != null){
				if(sStoreType.equalsIgnoreCase(STORETYPE.IBON.toString())){
					obj.setStoreType(STORETYPE.IBON);
				}else if(sStoreType.equalsIgnoreCase(STORETYPE.CVS.toString())){
					obj.setStoreType(STORETYPE.CVS);
				}else{
					obj.setStoreType(null);
				}
				
			}else {
				obj.setStoreType(null);
			}
			
			return obj;
		}

		@Override
		public OptionalCVS[] newArray(int size) {
			// TODO Auto-generated method stub
			return new OptionalCVS[size];
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
		dest.writeString(Desc_1);
		dest.writeString(Desc_2);
		dest.writeString(Desc_3);
		dest.writeString(Desc_4);
		dest.writeString(StoreType == null ? "" : StoreType.toString());
	}
	
	
}

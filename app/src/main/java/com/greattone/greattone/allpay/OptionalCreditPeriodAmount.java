package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;


public class OptionalCreditPeriodAmount implements Parcelable {
	private Integer PeriodAmount;
	private PERIODTYPE PeriodType;
	private Integer Frequency;
	private Integer ExecTimes;
	
	
	public void setPeriodAmount(Integer PeriodAmount) { this.PeriodAmount = PeriodAmount; }
	public Integer getPeriodAmount() { return this.PeriodAmount; }
	
	public void setPeriodType(PERIODTYPE PeriodType) { this.PeriodType = PeriodType; }
	public PERIODTYPE getPeriodType() { return this.PeriodType; }
	
	public void setFrequency(Integer Frequency) { this.Frequency = Frequency; }
	public Integer getFrequency() { return this.Frequency; }
	
	public void setExecTimes(Integer ExecTimes) { this.ExecTimes = ExecTimes; }
	public Integer getExecTimes() { return this.ExecTimes; }
	
	
	/**
	 * 選擇性參數，信用卡定期定額
	 * 當ChoosePayment參數為Credit時帶入，不可與信用卡分期一起設定
	 * @param PeriodAmount
	 * @param PeriodType
	 * @param Frequency
	 * @param ExecTimes
	 */
	public OptionalCreditPeriodAmount(Integer PeriodAmount, PERIODTYPE PeriodType, Integer Frequency, Integer ExecTimes){
		this.PeriodAmount = PeriodAmount;
		this.PeriodType = PeriodType;
		this.Frequency = Frequency;
		this.ExecTimes = ExecTimes;
	}
	private OptionalCreditPeriodAmount(){}
	
	
	public Collection<Map.Entry<String, String>> getPostData(){
		Map<String, String> mapParams = new HashMap<String, String>();		
		mapParams.put("PeriodAmount", String.valueOf(this.PeriodAmount));
		mapParams.put("PeriodType", this.PeriodType.toString());
		mapParams.put("Frequency", String.valueOf(this.Frequency));
		mapParams.put("ExecTimes",String.valueOf(this.ExecTimes) );
		
		return mapParams.entrySet();
	}
	
	
	public String getJSON()
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Creator<OptionalCreditPeriodAmount> CREATOR = new Creator(){

		@Override
		public OptionalCreditPeriodAmount createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			OptionalCreditPeriodAmount obj = new OptionalCreditPeriodAmount();
			obj.setPeriodAmount(Integer.valueOf(source.readString()));
			
			String sPeriodType = source.readString();
			if(sPeriodType != null){
				if(sPeriodType.equalsIgnoreCase(PERIODTYPE.DAY.toString())){
					obj.setPeriodType(PERIODTYPE.DAY);
				}else if(sPeriodType.equalsIgnoreCase(PERIODTYPE.MONTH.toString())){
					obj.setPeriodType(PERIODTYPE.MONTH);
				}else if(sPeriodType.equalsIgnoreCase(PERIODTYPE.YEAR.toString())){
					obj.setPeriodType(PERIODTYPE.YEAR);
				}else{
					obj.setPeriodType(null);
				}
			}else{
				obj.setPeriodType(null);
			}
			
			obj.setFrequency(Integer.valueOf(source.readString()));
			obj.setExecTimes(Integer.valueOf(source.readString()));
			
			return obj;
		}

		@Override
		public OptionalCreditPeriodAmount[] newArray(int size) {
			// TODO Auto-generated method stub
			return new OptionalCreditPeriodAmount[size];
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
		dest.writeString(String.valueOf(PeriodAmount));
		dest.writeString(PeriodType == null ? "" : PeriodType.toString());
		dest.writeString(String.valueOf(Frequency));
		dest.writeString(String.valueOf(ExecTimes));
	}
			
	
}

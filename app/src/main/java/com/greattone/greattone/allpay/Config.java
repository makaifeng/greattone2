package com.greattone.greattone.allpay;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;



public class Config {
	public static String LOGTAG = "AllpayMobileSDKDemo";
	
	public static int REQUEST_CODE = 1001;
	
	public static String MerchantID_test = "1386047";
	public static String PlatformID_test = "1000139";
	public static String PlatformMemberNo_test = "222222"; 
	public static String PlatformChargeFee_test = "1";
	public static String AppCode_test = "hqs2";
	public static String AppCode_PlatformID_test = "test_abcd";
	public static int TotalAmount_test = 1;
	public static String TradeDesc_test = "Allpay商城購物";
	public static String ItemName_test = "手機20元X2#隨身碟60元X1";
	
	public static ArrayList<BANKNAME> lstBankName = new ArrayList<BANKNAME>(Arrays.asList(BANKNAME.TAISHIN, BANKNAME.HUANAN, BANKNAME.ESUN, BANKNAME.FUBON, BANKNAME.BOT, BANKNAME.CHINATRUST, BANKNAME.FIRST, BANKNAME.ESUN_Counter));
	public static ArrayList<STORETYPE> lstStoreType = new ArrayList<STORETYPE>(Arrays.asList(STORETYPE.CVS, STORETYPE.IBON));
	
	public static ArrayList<String> aryBankName = new ArrayList<String>(Arrays.asList("All", BANKNAME.TAISHIN.toString(), BANKNAME.HUANAN.toString(), BANKNAME.ESUN.toString(), BANKNAME.FUBON.toString(), BANKNAME.BOT.toString(), BANKNAME.CHINATRUST.toString(), BANKNAME.FIRST.toString(), BANKNAME.ESUN_Counter.toString()));
	public static ArrayList<String> aryStoreType = new ArrayList<String>(Arrays.asList("All", STORETYPE.CVS.toString(), STORETYPE.IBON.toString()));
	
	@SuppressLint("SimpleDateFormat")
	public static String getMerchantTradeNo(){		
		java.util.Date now = new java.util.Date();
		return new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(now);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getMerchantTradeDate(){
		java.util.Date now = new java.util.Date();
		return new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now);
	}
	
	
	
}

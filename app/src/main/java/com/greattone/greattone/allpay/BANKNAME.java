package com.greattone.greattone.allpay;

public enum BANKNAME {
	TAISHIN("TAISHIN"),
	HUANAN("HUANAN"),
	ESUN("ESUN"),
	FUBON("FUBON"),
	BOT("BOT"),
	CHINATRUST("CHINATRUST"),
    FIRST("FIRST"),
    ESUN_Counter("ESUN_Counter");
	
	private BANKNAME(String name){
    	this.setName(name);
    }
    private String name;
    public String getName() {
		return name;
	}
    public void setName(String name){
    	this.name = name;
    }
    public String toString(){
    	return this.getName();
    }
    
    public static BANKNAME parse2BankName(String str){
    	BANKNAME bankName = null;
    	
    	if(str.equalsIgnoreCase("TAISHIN"))
    		bankName = BANKNAME.TAISHIN;
    	else if(str.equalsIgnoreCase("HUANAN"))
    		bankName = BANKNAME.HUANAN;
    	else if(str.equalsIgnoreCase("ESUN"))
    		bankName = BANKNAME.ESUN;
    	else if(str.equalsIgnoreCase("FUBON"))
    		bankName = BANKNAME.FUBON;
    	else if(str.equalsIgnoreCase("BOT"))
    		bankName = BANKNAME.BOT;
    	else if(str.equalsIgnoreCase("CHINATRUST"))
    		bankName = BANKNAME.CHINATRUST;
    	else if(str.equalsIgnoreCase("FIRST"))
    		bankName = BANKNAME.FIRST;
    	else if(str.equalsIgnoreCase("ESUN_Counter"))
    		bankName = BANKNAME.ESUN_Counter;
    	
    	return bankName;
    }
}

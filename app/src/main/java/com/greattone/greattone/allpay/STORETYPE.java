package com.greattone.greattone.allpay;


public enum STORETYPE {
	CVS("CVS"),
    IBON("IBON");
	
	private STORETYPE(String name){
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
    
    public static STORETYPE parse2StoreType(String str){
    	STORETYPE storeType = null;
    	
    	if(str.equalsIgnoreCase("IBON"))
    		storeType = STORETYPE.IBON;
    	else if(str.equalsIgnoreCase("CVS"))
    		storeType = STORETYPE.CVS;
    	
    	return storeType;
    }
}

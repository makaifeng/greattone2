package com.greattone.greattone.allpay;

public enum CREDITTYPE {
	INSTALLMENT("installment"),
    PERIODAMOUNT("periodamount");
    private CREDITTYPE(String name){
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
}

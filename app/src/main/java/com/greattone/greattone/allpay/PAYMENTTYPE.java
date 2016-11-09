package com.greattone.greattone.allpay;

public enum PAYMENTTYPE {
	ALL("ALL"),
	ATM("ATM"),
	CVS("CVS"),
    CREDIT("Credit");
	
	private PAYMENTTYPE(String name){
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

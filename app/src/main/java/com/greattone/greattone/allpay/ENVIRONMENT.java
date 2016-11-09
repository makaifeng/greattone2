package com.greattone.greattone.allpay;
public enum ENVIRONMENT {
	OFFICIAL("https://payment.allpay.com.tw/"),
    STAGE("http://payment-stage.allpay.com.tw/");
    private ENVIRONMENT(String name){
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

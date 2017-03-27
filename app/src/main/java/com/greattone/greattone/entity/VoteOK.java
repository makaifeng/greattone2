package com.greattone.greattone.entity;

import java.util.List;

public class VoteOK {
	String pm;
	String tou_num;
	int type;
	List<ImageData> gg;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public String getTou_num() {
		return tou_num;
	}
	public void setTou_num(String tou_num) {
		this.tou_num = tou_num;
	}
	public List<ImageData> getGg() {
		return gg;
	}
	public void setGg(List<ImageData> gg) {
		this.gg = gg;
	}
	
}

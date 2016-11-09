package com.greattone.greattone.entity;
/**
 * 新闻
 * @author Administrator
 *
 */
public class News {

	String imageUrl;//图片地址
	String title;//标题
	String content;//内容
	int pagerStyle;//展示的样式
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPagerStyle() {
		return pagerStyle;
	}
	public void setPagerStyle(int pagerStyle) {
		this.pagerStyle = pagerStyle;
	}
}

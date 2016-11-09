package com.greattone.greattone.activity;

import java.util.Comparator;

import com.greattone.greattone.util.PinYinUtil;

public class PinyinComparator implements Comparator<String> {  
    /** 
     * 比较两个字符串 
     */  
    public int compare(String name1, String name2) {  
        String str1 = PinYinUtil.getAllFirstSpell(name1);  
        String str2 = PinYinUtil.getAllFirstSpell(name2);  
        int flag = str1.compareTo(str2);  
        return flag;  
    }  
  
}  

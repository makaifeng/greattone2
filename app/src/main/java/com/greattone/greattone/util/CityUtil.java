package com.greattone.greattone.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.activity.PinyinComparator;
import com.greattone.greattone.entity.City;
import com.greattone.greattone.entity.District;
import com.greattone.greattone.entity.Province;

public class CityUtil {
	static List<Province> provinceList = new ArrayList<Province>();
	// static List<Address> cityList = new ArrayList<Address>();
	static List<District> cityList = new ArrayList<District>();
	static List<District> districtList = new ArrayList<District>();
	static CityUtil cityUtil;

	private CityUtil(final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				initProvinceList(context);
				// initCityList(context);
				// initAreaList(context);
			}
		}).start();
		// try {
		// InputStream is = context.getAssets().open("province_data.xml");
		// SAXParserFactory factory = SAXParserFactory.newInstance(); //
		// 取得SAXParserFactory实例
		// SAXParser parser = factory.newSAXParser(); // 从factory获取SAXParser实例
		// MyHandler handler = new MyHandler(); // 实例化自定义Handler
		// parser.parse(is, handler);
		// cityList = handler.getProvinceList();
		// } catch (Exception e) {
		// ((BaseActivity) context).toast("XML解析失败");
		// }
	}

	// private void initAreaList(Context context) {
	// try {
	// String result= getFileString(context, "address.json");
	// JSONArray array = new JSONArray(result);
	// for (int i = 0; i < array.length(); i++) {
	// District district = new District();
	// district.setName(array.getJSONObject(i).getString("name"));
	// // district.setZipcode(array.getJSONObject(i).getString("code"));
	// districtList.add(district);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	/** 读取数据 */
	private void initProvinceList(Context context) {
		try {
			String result = getFileString(context, "address.json");
			provinceList = JSON.parseArray(result.trim(), Province.class);
			// JSONArray array = new JSONArray(result);
			// for (int i = 0; i < array.length(); i++) {
			// District district = new District();
			// district.setName(array.getJSONObject(i).getString("name"));
			// // district.setZipcode(array.getJSONObject(i).getString("code"));
			// provinceList.add(district);
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// /***/
	// private void initCityList(Context context) {
	// try {
	// String result= getFileString(context, "json-array-of-city.json");
	// JSONArray array = new JSONArray(result);
	// for (int i = 0; i < array.length(); i++) {
	// District district = new District();
	// district.setName(array.getJSONObject(i).getString("name"));
	// district.setZipcode(array.getJSONObject(i).getString("code"));
	// cityList.add(district);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// }

	private String getFileString(Context context, String file)
			throws IOException {
		String result = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(context
				.getAssets().open(file)));
		String line = "";
		while ((line = br.readLine()) != null)
			result += line;
		// int len = -1;
		// byte[] buf = new byte[1024];
		// while ((len = is.read(buf)) != -1) {
		// sb.append(new String(buf, 0, len, "UTF-8"));
		// }
		br.close();
		return result;
	}

	/** 初始化城市数据 */
	public static CityUtil initCity(Context context) {
		cityUtil = new CityUtil(context);
		return cityUtil;
	}

	/** 获取省份 */
	public static List<String> getProvince() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < provinceList.size(); i++) {
			list.add(provinceList.get(i).getName());
		}
//		Collections.sort(list,Collator.getInstance(java.util.Locale.CHINA));//注意：是根据的汉字的拼音的字母排序的，而不是根据汉字一般的排序方法
        Collections.sort(list, new PinyinComparator());  
		return list;
	}
	/** 获取某省的城市 */
	public static List<String> getCity(String province) {
		// String str = province.getZipcode().substring(0, 2);
		List<String> list = new ArrayList<String>();
		for (Province province2 : provinceList) {
			if (province2.getName().equals(province)) {
				for (City city : province2.getCityList()) {
					list.add(city.getName());
				}
				return list;
			}
		}
		return list;
	}

	/** 获取某省某城市的地区名 */
	public static List<String> getDistrict(String province, String city) {
		// String str = city.getZipcode().substring(0, 4);
		// List<District> list = new ArrayList<District>();
		// for (District district : districtList) {
		// if (district.getZipcode().startsWith(str)) {
		// list.add(district);
		// }
		// }
		// return list;
		List<String> list = new ArrayList<String>();
		for (Province province2 : provinceList) {
			if (province2.getName().equals(province)) {
				for (City city2 : province2.getCityList()) {
					if (city2.getName().equals(city)) {
						list.addAll(city2.getAreaList());
						return list;
					}
				}
				return list;
			}
		}
		return list;
	}

	// // 需要重写DefaultHandler的方法
	// private class MyHandler extends DefaultHandler {
	//
	// private List<Province> provinceList;
	// private List<City> cityList;
	// private List<District> districtList;
	// private District district;
	// private City city;
	// private Province province;
	//
	// // private StringBuilder builder;
	//
	// // 返回解析后得到的Book对象集合
	// public List<Province> getProvinceList() {
	// return provinceList;
	// }
	//
	// @Override
	// public void startDocument() throws SAXException {
	// super.startDocument();
	// provinceList = new ArrayList<Province>();
	// // builder = new StringBuilder();
	// }
	//
	// @Override
	// public void startElement(String uri, String localName, String qName,
	// Attributes attributes) throws SAXException {
	// super.startElement(uri, localName, qName, attributes);
	// if (localName.equals("province")) {
	// province = new Province();
	// cityList = new ArrayList<City>();
	// province.setName(attributes.getValue("name"));
	// } else if (localName.equals("city")) {
	// city = new City();
	// districtList = new ArrayList<District>();
	// city.setName(attributes.getValue("name"));
	// } else if (localName.equals("district")) {
	// district = new District();
	// district.setName(attributes.getValue("name"));
	// district.setZipcode(attributes.getValue("zipcode"));
	// }
	// // builder.setLength(0); //将字符长度设置为0 以便重新开始读取元素内的字符节点
	// }
	//
	// @Override
	// public void characters(char[] ch, int start, int length)
	// throws SAXException {
	// super.characters(ch, start, length);
	// // builder.append(ch, start, length); //将读取的字符数组追加到builder中
	// }
	//
	// @Override
	// public void endElement(String uri, String localName, String qName)
	// throws SAXException {
	// super.endElement(uri, localName, qName);
	// if (localName.equals("province")) {
	// province.setCity(cityList);
	// provinceList.add(province);
	// } else if (localName.equals("city")) {
	// city.setDistrict(districtList);
	// cityList.add(city);
	// } else if (localName.equals("district")) {
	// districtList.add(district);
	// }
	// // builder.setLength(0);
	// }
	// }

}

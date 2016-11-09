package com.greattone.greattone.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.HttpConstants2;
import com.greattone.greattone.data.PayOrder;
import com.greattone.greattone.data.PayOrder.PayBack;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

public class AliPay  {
	static  Context context;
	
	//商户PID
	public static final String PARTNER = "2088021707986815";
	//商户收款账号
	public static final String SELLER = "admin@greattone.net";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAJYmZw8IBupbRnaeGzyMCH1487V+sAdFjn96tnczeARD7doQVnQS/QwmD8V0QFe6IHmUz914CRPxPMv1nJWWQso0GcgZM/4f4xMFcJDAasAHDgmuA17fu5kfMZW70PJ6SgmwP2Pxm7tvFeCbKN7Ela5ET3U2G/RujcizpdB7LSHDAgMBAAECgYEAjJPx44nhZ6QDieUnnP2CqW8HxgKR7oz6CHKsyVe/40ZyN7saJlzs3GX6WCUqZLhX1V01bKYI4cn34QHZE9h2OIl+WKaLdHVT1sZWdXV33Gco6ISTDCQntp5BiCbxQUVrd8pOs9gFc8PJ/2A+sufBo1cyfMO/xNp69zVjj4DKKUkCQQDGijgttupNoKGd6h1QMya75lAhNvJJAbaTrNXqhY9LZw0hEnUj++Ht8uOQ5RIouL2AzqzTfwIuDbdJclw/dn4VAkEAwZr8J5nJPZZZWNE85VqGSPv+VaG8WfaZaUTDYw9ZW+r/CdoTvqdH+VziiZhLGUSNOp6AUpKyarCiIzM0Q53udwJBALmbdjGmkxzHUtAikgzsBQYcpkSm7ZK6+0jLh6CcA/5l9Kw6aTCexfSB4aUPwg43x1Gn5YJDdnI/eF49f2gFCpECQQCK0WUBxAv+Y+J2g7jlPu5QQJdRsSFLZD0FtO9gBO5usOXjm4FSz8EUtJweSpt2Z6fYIzQhgNv7EeF/2cQcw43rAkEAjgsusztZtR6+Y6OItTcoLGT+8hMVq8KjnRXYNpHwq9/wLY6iNPttBY0fiH+tUZrmynmwRCYlF8e9IYiVrdnu9A==";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWJmcPCAbqW0Z2nhs8jAh9ePO1frAHRY5/erZ3M3gEQ+3aEFZ0Ev0MJg/FdEBXuiB5lM/deAkT8TzL9ZyVlkLKNBnIGTP+H+MTBXCQwGrABw4JrgNe37uZHzGVu9DyekoJsD9j8Zu7bxXgmyjexJWuRE91Nhv0bo3Is6XQey0hwwIDAQAB";


	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	public static String msg="";
	static PayBack payback;
	private static   Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				
//				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//				String resultInfo = payResult.getResult();
				
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					payback.PayBackOK(PayOrder.AIPAY_PAY);
					Toast.makeText(context, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(context, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(context, "支付失败",
								Toast.LENGTH_SHORT).show();
						payback.PayBackError(PayOrder.AIPAY_PAY);
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(context, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.pay_main);
//	}
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public static void pay(final Context context,String name,String contant,String price,String orderId,PayBack payBack) {
		pay(context, name, contant, price, orderId, HttpConstants2.SERVER_URL+"/e/appdemo/notify_url.php", payBack);
	}
	/**
	 * call alipay sdk pay. 调用SDK支付
	 */
	public static void pay(final Context context,String name,String contant,String price,String orderId,String backUrl,PayBack payBack) {
		AliPay.context=context;
		AliPay.payback=payBack;
		// 订单
		String orderInfo = getOrderInfo(name, contant, price,orderId,backUrl);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		msg=payInfo;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(((BaseActivity)context));
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

//	/**
//	 * check whether the device has authentication alipay account.
//	 * 查询终端设备是否存在支付宝认证账户
//	 * 
//	 */
//	public void check(View v) {
//		Runnable checkRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask payTask = new PayTask((BaseActivity) context);
//				// 调用查询接口，获取查询结果
//				boolean isExist = payTask.checkAccountIfExist();
//
//				Message msg = new Message();
//				msg.what = SDK_CHECK_FLAG;
//				msg.obj = isExist;
//				mHandler.sendMessage(msg);
//			}
//		};
//
//		Thread checkThread = new Thread(checkRunnable);
//		checkThread.start();
//
//	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask((BaseActivity)context);
		String version = payTask.getVersion();
		Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public static  String getOrderInfo(String subject, String body, String price,String orderId,String backUrl) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" +backUrl
//		MyApplication.getInstance().parameter.getAlipayNotifyURL()
				
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public  String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public static  String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public static  String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
}

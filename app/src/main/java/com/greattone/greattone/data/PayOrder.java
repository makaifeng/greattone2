package com.greattone.greattone.data;

import android.content.Context;
import android.widget.Toast;

import com.greattone.greattone.alipay.AliPay;
import com.greattone.greattone.dialog.MyProgressDialog;

/**
 * 订单支付
 * 
 * @author pc-002
 *
 */
public class PayOrder {
	private static Context context;
	private static String orderId;
	private static String[] msg;
	public static PayBack payBack;
	public static PayBack payBack2=new PayBack() {
		
		@Override
		public void PayBackOK(int payType) {
//			if (orderType==PRICE_DISPARITIES) {//订单差价
//				payBack.PayBackOK(payType);
//				MyProgressDialog.cancel();
//			}else {//普通订单
//				PayOrderFromApp();//App收到支付成功后通知平台 
//			}
		}
		
		@Override
		public void PayBackError(int payType) {
			payBack.PayBackError(payType);
		}
	};
	/**
	 * 支付宝支付
	 */
	public static int AIPAY_PAY = 1;

	/**
	 * 微信支付
	 */
	public static int WECHAT_PAY = 2;

	/**
	 * 银联支付
	 */
	public static int UNIONPAY_PAY = 3;
	/**
	 * 订单锁定失败
	 */
	public static int LOCK_ORDER_ERROR =4;
	private static int payType;
//	private static int orderType;
	/**
	 * 普通订单
	 */
	public static int ORDINARY_ORDER = 1;

	/**
	 * 订单差价
	 */
	public static int PRICE_DISPARITIES = 2;



	/**
	 * 订单支付
	 * 
	 * @param orderId
	 * @param position
	 */
	public static void toPay(Context context, String orderId,  int payType,int orderType,
			String msg[],  PayBack payBack) {
		PayOrder.context=context;
//		PayOrder.orderType=orderType;
		PayOrder.payBack=payBack;
		PayOrder.orderId=orderId;
		PayOrder.msg=msg;
		PayOrder.payType=payType;
		if (orderType==PRICE_DISPARITIES) {//订单差价
			goToPay();//去支付
		}else {//普通订单
//			LockOrderTime();//订单锁定
		}
	}


//	/**
//	 * 订单锁定
//	 * @param context
//	 * @param orderId
//	 * @param payType
//	 * @param msg
//	 * @param payBack2
//	 */
//	private static void LockOrderTime() {
//		MyProxy.LockOrderTime(context, orderId, new MyStringResponseHandle() {
//			
//			@Override
//			public void setServerErrorResponseHandle(String response)  {
//				MyProgressDialog.dismiss();
//				if (MyConstants.isDebug) {
//				MyData.Toast(context, "订单锁定失败");
//				}
//				int errorCode=JSON.parseObject(response, Message.class).getCode();
//				if (errorCode==ServerException.Code363.getKey()) {
//					PayOrder.payBack.PayBackError(LOCK_ORDER_ERROR);
//					 MyHint2PopupWindow.build(context, "该服务时间已被预定", false).show();
//				}else if (errorCode==ServerException.Code366.getKey()) {
//					PayOrder.payBack.PayBackError(LOCK_ORDER_ERROR);
//					 MyHint2PopupWindow.build(context, ServerException.Code366.toString(), false).show();
//				}
//			}
//			
//			@Override
//			public void setResponseHandle(String response) {
//		
//				double price=Double.valueOf(msg[2]) ;
//			if (price==0) {//当价格为0时
//				payOK(context, orderId, payType, msg, payBack);//直接掉服务器支付成功
//			}else {
//				goToPay();
//			}
//			}
//			
//			@Override
//			public void setErrorResponseHandle(VolleyError error) {
//				
//			}
//		});
//	}
	/**
	 * 去支付
	 */
private static void goToPay(){
	//当价格不为0时
	if (payType == AIPAY_PAY) {// 支付宝支付
		 AliPay.pay(context, msg[0], msg[1], msg[2], orderId,
					payBack2);
		} else if (payType == WECHAT_PAY) {// 微信支付
//			if (MyData.checkApkExist(context, "com.tencent.mm")) {//检测是否安装微信
//				MyProgressDialog.Cancel();
//				MyProgressDialog.show(context,  "正在加载");
////				UnifiedOrderByParam( context,orderId,payType);
//			}else {
				PayOrder.payBack.PayBackError(payType);
				Toast.makeText(context, "未安装微信!", Toast.LENGTH_SHORT).show();
				MyProgressDialog.Cancel();
//				}
		} else if (payType == UNIONPAY_PAY) {// 银联支付
			Toast.makeText(context, "还未完成银联支付!", Toast.LENGTH_SHORT).show();
		}

}
//	/**
//	 * //直接掉服务器,支付成功
//	 * @param context
//	 * @param orderId
//	 * @param payType
//	 * @param msg
//	 * @param payBack
//	 */
//	private static void payOK(Context context, String orderId, final int payType,	String msg[],final PayBack payBack) {
//		MyProxy.payOrder(context, orderId, msg[2], new MyStringResponseHandle() {
//			
//			@Override
//			public void setServerErrorResponseHandle(String response)  {
//				payBack.PayBackError(payType);//支付失败
//			}
//			
//			@Override
//			public void setResponseHandle(String response) {
//				//支付成功
//				payBack.PayBackOK(payType);
//			}
//			
//			@Override
//			public void setErrorResponseHandle(VolleyError error) {
//				
//			}
//		} );
//		
//	}
//	/**
//	 * 统一下单用去接口调用
//	 * @param payType 
//	 */
//	private static void UnifiedOrderByParam(final Context context,String orderId, final int payType) {
//		MyProxy.UnifiedOrderByParam(context,orderId,new MyStringResponseHandle() {
//			
//			@Override
//			public void setServerErrorResponseHandle(String response)  {
//					MyProgressDialog.cancel();
//					payBack.PayBackError(payType);//支付失败
//			}
//			
//			@Override
//			public void setResponseHandle(String response) {
//				WxUnifiedorder wxUnifiedorder=JSON.parseObject(response, WxUnifiedorder.class);
//				WxPay.sendPayReq(context, wxUnifiedorder);
//			}
//			
//			@Override
//			public void setErrorResponseHandle(VolleyError error) {
//				MyProgressDialog.cancel();
//			}
//		});
//	}
//	/**
//	 * App收到支付成功后通知平台 
//	 */
//	protected static void PayOrderFromApp() {
//		MyProgressDialog.show(context);
//	MyProxy.PayOrderFromApp(context, orderId, msg[2],payType,new MyStringResponseHandle() {
//			
//			@Override
//			public void setServerErrorResponseHandle(String response)  {
//					MyProgressDialog.cancel();
//			}
//			
//			@Override
//			public void setResponseHandle(String response) {
//				MyProgressDialog.cancel();
//				payBack.PayBackOK(payType);
//			}
//			
//			@Override
//			public void setErrorResponseHandle(VolleyError error) {
//				MyProgressDialog.cancel();
//			}
//		});
//	}

	/**
	 * 支付返回
	 */
	public interface PayBack {
		/**
		 * 支付成功
		 */
		public void PayBackOK(int payType);
		/**
		 * 支付失败
		 */
		public void PayBackError(int payType);

	}
}

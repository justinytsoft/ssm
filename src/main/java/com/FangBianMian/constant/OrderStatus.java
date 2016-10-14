package com.FangBianMian.constant;

/**
 * 订单状态
 * @author Administrator
 *
 */
public class OrderStatus {
	/**
	 * 0，待支付(下单)；
	 */
	public static Integer WAIT_PAY = 0;
	
	/**
	 * 1，待理货(已支付)；
	 */
	public static Integer WAIT_TALLY = 1;
	
	/**
	 * 2，待发货(已理货)；
	 */
	public static Integer WAIT_SEND = 2;
	
	/**
	 * 3，待收货(已发货)；
	 */
	public static Integer WAIT_RECEVIE = 3;
	
	
	/**
	 * 4，待评价(已收货)；
	 */
	public static Integer WAIT_COMMENT = 4;
	
	
	/**
	 * 5，完成(已评价)；
	 */
	public static Integer DONE = 5;
	
	
	/**
	 * 6，取消(过期)；
	 */
	public static Integer CANCEL = 6;
}

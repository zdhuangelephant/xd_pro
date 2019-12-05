package com.xiaodou.oms.constant.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单常量
 * @author Administrator
 *
 */
public interface OrderConstant {
	/** 一般 **/
	public static final Integer VALID = 1;
	public static final Integer INVALID = 0;
	
	
	//0初始化，1支付成功，2已发货，3交易成功，4 退款，5关闭
	/** 订单状态-初始化 */
	public static final Integer STATUS_INITIATE = 0;
	/** 订单状态-支付成功 */
	public static final Integer STATUS_PAYSUCCESS = 1;
	
	/** 订单状态-支付失败 */
	public static final Integer STATUS_PAYFAILURE = -1;
	/** 订单状态-已发货 */
	public static final Integer STATUS_DELIVERED = 2;
	/** 订单状态-交易成功结束 */
	public static final Integer STATUS_TRANSACTIONSUCCESS = 3;
	/** 订单状态-待退款 */
	public static final Integer STATUS_REFUND = 4;
	/** 订单状态-已关闭 */
	public static final Integer STATUS_CLOSED = 5;
	/** 订单状态-备货中 */
	public static final Integer STATUS_PREPARE = 6;
	/** 订单状态-锁库存 */
	public static final Integer STATUS_LOCKED = 9;
	/** 订单状态-退款事变 */
	public static final Integer STATUS_REFUND_ERROR = 42;
	/** 订单状态-退款成功 */
	public static final Integer STATUS_REFUND_SUCCESS = 41;
	/**订单状态-需退款审核*/
	public static final Integer STATUS_DELIVER_FAIL =40;
	
	/** 打款标识 */
	public static final Integer SETTLEUP_FALSE = 0;
	public static final Integer SETTLEUP_TRUE = 1;
	public static final Integer SETTLEUP_DONE =2;
	
	/** 填充字符 */
	public static final Integer INTEGER_NEEDED = 1;
	public static final String STRING_NEEDED = "1";
	public static final Timestamp DATE_NEEDED =  new Timestamp(System.currentTimeMillis());
	public static final Integer[] INTEGERARRAY_NEEDED = new Integer[0];
	public static final BigDecimal BIGDECIMAL_NEEDED = BigDecimal.ONE;
	
	
	
	
	

	
	
}

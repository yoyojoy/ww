package com.cs.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

/**
 * 金融计算对象
 * @author ice
 *
 */
public class Money {
	
	/**
	 * 进行加法运算
	 * @param moneys
	 * @return this
	 */
	public Money add(Number... moneys) {

		this.money = bigDecimalUtils.add(ArrayUtils.add(moneys, 0, money));
		return this;
	}
	
	/**
	 * 进行减法运算
	 * @param moneys
	 * @return this
	 */
	public Money subtract(Number... moneys) {
		
		money = bigDecimalUtils.subtract(ArrayUtils.add(moneys, 0, money));
		return this;
	}
	
	/**
	 * 进行乘法运算
	 * @param moneys
	 * @return this
	 */
	public Money multiply(Number... moneys) {
		
		money = bigDecimalUtils.multiply(ArrayUtils.add(moneys, 0, money));
		return this;
	}
	
	/**
	 * 进行除法运算
	 * @param moneys
	 * @return this
	 */
	public Money divide(Number... moneys) {
		
		money = bigDecimalUtils.divide(ArrayUtils.add(moneys, 0, money));
		return this;
	}
	
	/**
	 * 获取 double 值
	 * 将数值保留两位小数，舍弃部分会四舍五入
	 * @return
	 */
	public double doubleValue() {
		
		return bigDecimalUtils.doubleValue2F(money);
	}
	
	/**
	 * 获取 double 值
	 * @param scale 精度
	 * @return
	 */
	public double doubleValue(int scale) {
		
		return bigDecimalUtils.doubleValue(money, scale);
	}
	
	/**
	 * 获取 long 值
	 * 舍弃部分会四舍五入
	 * @return
	 */
	public long longValue() {
		
		return bigDecimalUtils.longValue(money);
	}
	
	/**
	 * 获取 BigInteger 值
	 * 舍弃部分会四舍五入
	 * @return
	 */
	public BigInteger bigIntegerValue() {
		
		return bigDecimalUtils.bigIntegerValue(money);
	}

	/**
	 * 获取 BigDecimal 值
	 * @param scale 保留小数位
	 * @return
	 */
	public BigDecimal bigDecimalValue(int scale) {
		return bigDecimalUtils.bigDecimalValue(money, scale);
	}
	

	/**
	 * 构造方法，默认值为 0
	 */
	public Money() {
		
		this(0);
	}
	
	/**
	 * 构造方法
	 * @param money 金额
	 */
	public Money(Number money) {
		
		this.money = bigDecimalUtils.getBigDecimal(money);
	}

    /**
     * 根据比较值通过运算符和区间范围值进行比较
     * @param computeValue 比较值
     * @param operaSymbol 操作运算符
     * @param scopeValue 区间范围值
     * @return 比较结果 true：匹配; false:不匹配
     */
    public static boolean computeDouble(double computeValue, String operaSymbol, double scopeValue) {
        // 比较结果值
        int compareValue = Double.compare(computeValue, scopeValue);

        // 大于运行符比较
        if(">".equals(operaSymbol))  return compareValue >  0;
        // 大于等于运行符比较
        if(">=".equals(operaSymbol)) return compareValue >= 0;
        // 等于运行符比较
        if("==".equals(operaSymbol)) return compareValue == 0;
        // 不等于等于运行符比较
        if("!=".equals(operaSymbol)) return compareValue != 0;
        // 小于运行符比较
        if("<".equals(operaSymbol))  return compareValue <  0;
        // 小于等于运行符比较
        if("<=".equals(operaSymbol)) return compareValue <= 0;

        throw new IllegalArgumentException(MessageFormat.format("不支持运算符为[{0}]的比较!", operaSymbol));
    }

	/** 真实数据对象 */
	private BigDecimal money;
	/** 用于处理金额计算的工具 */
	private BigDecimalUtils bigDecimalUtils = BigDecimalUtils.INSTANCE_64;
}

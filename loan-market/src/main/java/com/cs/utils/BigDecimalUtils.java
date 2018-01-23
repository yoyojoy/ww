package com.cs.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 大数工具类
 * @author ice
 */
public enum BigDecimalUtils {
	
	/**
	 * MathContext.UNLIMITED
	 */
	INSTANCE(MathContext.UNLIMITED),
	
	/**
	 * MathContext.DECIMAL32
	 */
	INSTANCE_32(MathContext.DECIMAL32),
	
	/**
	 * MathContext.DECIMAL64
	 */
	INSTANCE_64(MathContext.DECIMAL64),
	
	/**
	 * MathContext.DECIMAL128
	 */
	INSTANCE_128(MathContext.DECIMAL128);
	
	
	/**
	 * 进行加法运算
	 * @param nums
	 * @return
	 */
	public BigDecimal add(Number... nums) {

		return calculate("add", nums);
	}
	
	/**
	 * 进行减法运算
	 * @param nums
	 * @return
	 */
	public BigDecimal subtract(Number... nums) {
		
		return calculate("subtract", nums);
	}
	
	/**
	 * 进行乘法运算
	 * @param nums
	 * @return
	 */
	public BigDecimal multiply(Number... nums) {
		
		return calculate("multiply", nums);
	}
	
	/**
	 * 进行除法运算
	 * @param nums
	 * @return
	 */
	public BigDecimal divide(Number... nums) {
		
		return calculate("divide", nums);
	}
	
	/**
	 * 将数值保留两位小数，舍弃部分会四舍五入
	 * @param num
	 * @return
	 */
	public double doubleValue2F(Number num) {
		
		return doubleValue(num, 2);
		
	}
	
	/**
	 * 将数值按指定进度留小数
	 * @param num
	 * @param scale
	 * @return
	 */
	public double doubleValue(Number num, int scale) {
		
		return getBigDecimal(num).
				setScale(scale, RoundingMode.HALF_UP).doubleValue();
		
	}

	/**
	 * 将数值按指定进度留小数
	 * @param num
	 * @param scale
	 * @return
	 */
	public BigDecimal bigDecimalValue(Number num, int scale) {

		return getBigDecimal(num).
				setScale(scale, RoundingMode.HALF_UP);

	}
	
	/**
	 * 将数值转为整数，舍去部分会四舍五入
	 * @param num
	 * @return
	 */
	public long longValue(Number num) {
		return getBigDecimal(num).
				setScale(0, RoundingMode.HALF_UP).longValue();
	}
	
	/**
	 * 将数值转为整数，舍去部分会四舍五入
	 * @param num
	 * @return
	 */
	public BigInteger bigIntegerValue(Number num) {
		return getBigDecimal(num).
				setScale(0, RoundingMode.HALF_UP).toBigInteger();
	}
	
	/**
	 * 获取一个 getBigDecimal 对象
	 * 如果传入对象本身是一个 BigDecimal 对象，则直接返回
	 * @param num 
	 * @return
	 */
	public BigDecimal getBigDecimal(Number num) {
		if(num instanceof BigDecimal) 
			return (BigDecimal) num;
		
		return new BigDecimal(num.doubleValue(), mathContext);
	}
	
	/**
	 * 检查计算参数是否合规
	 * @param methodName
	 * @param nums
	 */
	private BigDecimal calculate(String methodName, Number... nums) {
		
		if(nums.length < 2) 
			throw new IllegalArgumentException(String.format("进行 %s 计算，至少需要两个参数。", methodName));
		
		Method method = getCalculateMethod(methodName);
		BigDecimal result = getBigDecimal(nums[0]);

		int i = 1;
		do {
			try {
				result = (BigDecimal) method.invoke(result, getBigDecimal(nums[i]), mathContext);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			} 
		} while(++i < nums.length);
		
		return result;
	}
	
	/**
	 * 获取计算方法
	 * @param methodName 方法名
	 * @return
	 */
	private Method getCalculateMethod(String methodName) {

		Method method = null;
		
		try {
			method = BigDecimal.class.getMethod(methodName, BigDecimal.class, MathContext.class);
		} catch (Exception e) { }
		
		return method;
	}

	/**
	 * 私有构造方法
	 * @param mathContext
	 */
    BigDecimalUtils(MathContext mathContext) {

		this.mathContext = mathContext;
	}

	/** 上下文设置进行舍入 */
	private MathContext mathContext;
	
}

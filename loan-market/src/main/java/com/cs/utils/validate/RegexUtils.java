package com.cs.utils.validate;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 * 
 * @author yangqy
 * 
 */
public class RegexUtils {

	/**
	 * 手机正则
	 */
	public static final String MOBILEPHONEREGEX = "^0?(13[0-9]|15[012356789]|18[0-9]|17[0-9]|14[57]|19[89]|166)[0-9]{8}$";

	/**
	 * 是否为数字
	 */
	public static final String NUMBERREGEX = "^\\d\\d*$";

	/**
	 * @param regular 正则表达式
	 * @param input 需要验证的语句
	 * @return :匹配返回true
	 */
	public static Boolean validatePattern(String regular, String input) {
		if (StringUtils.isBlank(input))
			return false;
		Pattern pattern = Pattern.compile(regular);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}

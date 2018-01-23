package com.cs.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cs.redis.RedisUtils;
import com.cs.utils.JsonUtil;
import com.cs.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessInterceptor implements HandlerInterceptor{

	/**
	 * 请求超时状态
	 */
	private static final Integer TIMEOUT_STATUS = 666;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (!isTimeout(request)) {
			return true;
		} else {
			if (!isIgnoreRequest(request)) {
				/*if (request.getHeader("x-requested-with") != null
						&& "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
				}*/
				JSONObject result = new JSONObject();
				result.put("status",TIMEOUT_STATUS);
				result.put("msg","用户信息已失效，请重新登录");
				JsonUtil.responseJson(response,result);
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 是否为被忽略的请求
	 * 
	 * @param request
	 */
	private boolean isIgnoreRequest(HttpServletRequest request) {
		String url = request.getRequestURI().replace(request.getContextPath(), StringUtils.EMPTY);
		if (PropertiesUtil.getSysConfig().contains(url)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 当前用户是否超时
	 *
	 * @param request
	 */
	private boolean isTimeout(HttpServletRequest request) {
		return null == RedisUtils.get("");
	}
}

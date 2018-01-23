package com.cs.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cs.filter.UniParamsUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 将请求参数统一处理
 * (将app过来的请求进行解密)
 */
public class ParamsDecryptInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(ParamsDecryptInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return generateRequestParams(request);
    }

    /**
     * 将请求参数统一处理
     * @param request
     */
    private boolean generateRequestParams(HttpServletRequest request){
        String  param = request.getParameter("sign");
        try {
            if(StringUtils.isNotBlank(param)) {
                JSONObject params = UniParamsUtil.decryUniParams(param);
                if (params != null && params.size() > 0) {
                    for (Map.Entry pa : params.entrySet()) {
                        request.setAttribute(pa.getKey().toString(), pa.getValue());
                    }
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }
}
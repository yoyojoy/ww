package com.cs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 * 
 */
public final class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 此类不需要实例化
     */
    private JsonUtil() {
    }

    /**
     * 把对象转化为json字符串
     * 
     * @param obj
     * @return json
     */
    public static String getJsonForObject(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 从json字符串里面根据key获取value
     * 
     * @param json
     * @param key
     * @return
     */
    public static String getStringForJson(String json, String key) {
        return JSONObject.parseObject(json).getString(key);
    }

    /**
     * json转object
     */
    public static <T> T jsonToObject(String jsonString, Class<T> pojoCalss) {
        return JSONObject.parseObject(jsonString, pojoCalss);
    }

    /**
     * json转 List
     */
    public static <T> List<T> jsonToListObject(JSONArray jsonArray, Class<T> pojoCalss) {
        return JSONArray.parseArray(jsonArray.toString(), pojoCalss);
    }


    /**
     * 函数功能说明 ：返回数据转JSON格式字符串并制定UTF-8编码 <br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * return：void <br/>
     */
    public static void responseJson(HttpServletResponse response, Object obj) {
        String jsonStr = JSON.toJSONString(obj);
        responseJson(response, jsonStr);
    }

    
    public static void responseJson(HttpServletResponse response, Map<String, Object> result) {
		String jsonStr = JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
		responseJson(response, jsonStr);
	}

	private static void responseJson(HttpServletResponse response, String jsonStr) {
		response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter p = null;
        try {
            p = response.getWriter();
            p.write(jsonStr);
        } catch (IOException e) {
        	logger.error("response2json出现异常",e);
        } finally {
            if (p != null)
                p.close();
        }
	}
}

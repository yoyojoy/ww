package com.cs.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs.utils.PropertiesUtil;
import com.cs.utils.rsa.RSAUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joy on 2017/8/8.
 */
public class UniParamsUtil {

    private static final Logger log = LoggerFactory.getLogger(UniParamsUtil.class);

    // 颜值贷内部接口调用私钥
    private static final String YZD_PRIVATE_KEY = PropertiesUtil.getSysConfig().getProperty("yzd.interface.decrypt.privateKey");

    private static final String YZD_PUBLIC_KEY = PropertiesUtil.getSysConfig().getProperty("yzd.interface.encrypt.publicKey");

    public static final String APPVERSION="appVersion";
    public static final String DEVICE="device";
    public static final String TOKEN="token";
    public static final String APPTYPE="appType";
    public static final String IMEI="imei";

    /**
     * 获取参数
     * @param params
     * @param key
     * @return
     */
    public static String getUniValue(String params, String key){
        if(StringUtils.isNotBlank(params)){
            JSONObject jsonObject = decryUniParams(params);
            if(jsonObject!=null && jsonObject.get(key) != null){
                switch (key){
                    case APPVERSION : return jsonObject.get(APPVERSION).toString();
                    case DEVICE : return jsonObject.get(DEVICE).toString();
                    case TOKEN : return jsonObject.get(TOKEN).toString();
                    case APPTYPE : return jsonObject.get(APPTYPE).toString();
                    case IMEI : return jsonObject.get(IMEI).toString();
                }
            }
        }
        return null;
    }

    /**
     * 获取所有的公共参数
     * @param params
     */
    public static Map<String,String> getAllUniValues(String params) {
        if(StringUtils.isNotBlank(params)){
            JSONObject jsonObject = decryUniParams(params);
            if(jsonObject != null) {
                Map<String,String> map = new HashMap<>(jsonObject.keySet().size());
                map.put(APPVERSION,jsonObject.get(APPVERSION).toString());
                map.put(DEVICE,jsonObject.get(DEVICE).toString());
                map.put(APPTYPE,jsonObject.get(APPTYPE).toString());
                map.put(IMEI,jsonObject.get(IMEI).toString());
                map.put(TOKEN,jsonObject.get(TOKEN)!=null ? jsonObject.get(TOKEN).toString() : "");
                return map;
            }
        }
        return null;
    }

    public static JSONObject decryUniParams(String params){
        if(StringUtils.isNotBlank(params)){
            try{
                String res = new String(Base64.decodeBase64(params), "UTF-8");
                String bizData = RSAUtils.decryptByPrivateKey(res, YZD_PRIVATE_KEY);
                return JSONObject.parseObject(bizData);
            }catch (Exception e){
                log.error("解析统一参数"+params+"失败", e);
            }
        }
        return null;
    }

    /**
     * 加密
     * @param jsonMap
     * @return
     */
    public static String encryptUniParams(Map<String, Object> jsonMap){
        try{
            JSONObject json = JSON.parseObject(JSON.toJSONString(jsonMap));
            String RSAParams = RSAUtils.encryptByPublicKey(JSONObject.toJSONString(json), YZD_PUBLIC_KEY);
            return Base64.encodeBase64String(RSAParams.getBytes("UTF-8"));
        }catch (Exception e){
            log.error("统一参数"+jsonMap+"加密失败", e);
        }
        return null;
    }
}

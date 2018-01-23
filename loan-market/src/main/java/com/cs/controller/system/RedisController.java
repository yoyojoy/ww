package com.cs.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.*;

/**
 * Created by joy on 2017/8/12.
 */
@Controller
@RequestMapping("/redis")
public class RedisController extends BaseController {
/*
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

    private static final String SYS_CONFIG_KEY = BizConstants.SYS_CONFIG_KEY;//系统配置表redis key
    private static final String ANTI_FRAUD_KEY = BizConstants.INTERFACE_CONFIG_KEY;//反欺诈redis key
    private static final String BANK_INFO_KEY = BizConstants.BANK_INFO_KEY;//银行卡信息redis key

    @Autowired
    private IPlatformUserService platformUserService;

    @GetMapping("/index")
    public String manager() {
        return "config/RedisList";
    }


    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(String account, String certNo, Integer page, Integer rows) {
        PageInfo pageInfo = new PageInfo(page, rows);
        try {
            ShardedJedis shardedJedis = RedisUtils.getShardedJedisPool().getResource();
            Collection<Jedis> collection = shardedJedis.getAllShards();
            List<PlatformUser> users = platformUserService.get(certNo, account);
            //尽量不查多个用户的缓存信息
            if((users != null && users.size() > 1) || users.isEmpty()){
                pageInfo.setRows(new ArrayList<RedisEntityVO>());
                pageInfo.setTotal(0);
                return pageInfo;
            }
            String userId = users.get(0).getId();
            List<RedisEntityVO> list = new ArrayList<>();
            for (Jedis jedis : collection){
                Set<String> keys = jedis.keys("*");
                List<RedisEntityVO> vos = generate(keys, userId);
                list.addAll(vos);
            }
            sort(list);
            int total = !list.isEmpty() ? list.size() : 0;
            pageInfo.setRows(list);
            pageInfo.setTotal(total);
        } catch (Exception e) {
            LOGGER.warn("===== SesameCreditController => dataGrid: failed to query sesame credit data =====");
            LOGGER.error(e.getMessage());
        }
        return pageInfo;
    }

    private void sort(List<RedisEntityVO> list){
        if(!list.isEmpty()){
            Collections.sort(list, (o1, o2) -> {
                if (o1 == null || o2 == null) {
                    return -1;
                }
                return o1.getKey().compareTo(o2.getKey());
            });
        }
    }

    private List<RedisEntityVO> generate(Set<String> keys, String userId){
        if(keys.isEmpty()) return null;
        List<RedisEntityVO> list = new ArrayList<>();
        for(String key : keys){
            //系统配置表 反欺诈接口配置表 银行卡信息表 不在管理范围内
            if(!(key.contains(SYS_CONFIG_KEY) || key.contains(ANTI_FRAUD_KEY) || key.contains(BANK_INFO_KEY))) {
                RedisEntityVO VO = null;
                if((StringUtils.isNotBlank(userId) && key.contains(userId))){
                    String redisKey = key.substring(key.indexOf(userId));//大多数的key是以userId为开头的
                    if(StringUtils.isNotBlank(redisKey)) {
                        VO = new RedisEntityVO();
                        VO.setKey(redisKey);
                        if (redisKey.equalsIgnoreCase(userId)) {//这种情况的数据格式是: {token:userInfoDto}
                            String token = RedisUtils.get(userId) != null ? RedisUtils.get(userId).toString() : "";
                            UserInfoDto user = (UserInfoDto) RedisUtils.get(token);
                            VO.setValue(JSON.toJSONString(user));
                        } else if(redisKey.contains("getsture")){
                            VO.setValue(RedisUtils.getString(redisKey));
                        }else{
                            VO.setValue(RedisUtils.get(redisKey));
                        }
                        list.add(VO);
                    }
                }
            }
        }
        return list;
    }


    @RequestMapping("/editPage")
    public String editPage(Model model, String id) {
        RedisEntityVO vo = new RedisEntityVO();
        vo.setKey(id);
        if(id.length() == 32){
            String token = (String) RedisUtils.get(id);
            UserInfoDto user =  RedisUtils.get(token) != null ? (UserInfoDto) RedisUtils.get(token) : null;
            vo.setValue(JSON.toJSONString(user));
        }else if(id.contains("getsture")){
            vo.setValue(RedisUtils.getString(id));
        }else{
            vo.setValue(RedisUtils.get(id));
        }
        model.addAttribute("config", vo);
        return "config/redisEdit";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(RedisEntityVO config) {
        if(config.getKey().contains("getsture")){//手势密码
            RedisUtils.delStr(config.getKey());
            RedisUtils.saveString(config.getKey(), config.getValue().toString());
        }else if(config.getKey().length() == 32){
            String token = (String) RedisUtils.get(config.getKey());
            UserInfoDto userInfoDto = JSONObject.parseObject(config.getValue().toString(), UserInfoDto.class);
            RedisUtils.save(token, userInfoDto);
        }else{
            RedisUtils.save(config.getKey(), config.getValue());
        }
        return renderSuccess("编辑成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String key) {
        if(StringUtils.isNotBlank(key)){
            if(key.contains("getsture")){//手势密码是字符串为key添加的  没有序列号的
                RedisUtils.delStr(key);
            }else{
                if(RedisUtils.exists(key)){
                    RedisUtils.del(key);
                }
            }
        }
        return renderSuccess("删除成功！");
    }*/
}

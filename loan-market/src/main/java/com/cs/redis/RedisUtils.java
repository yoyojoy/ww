package com.cs.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @描述: Redis缓存工具类.
 */
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * 默认缓存时间
     */
    private static final int DEFAULT_CACHE_SECONDS = 60 * 60 * 1;// 单位秒 设置成一个钟

    private static ShardedJedisPool shardedJedisPool;

    /**
     * 释放redis资源
     *
     * @param jedis
     */
    private static void releaseResource(ShardedJedis jedis) {
        if (jedis != null)
            jedis.close();

    }

    /**
     * 保存一个对象到Redis中(缓存过期时间:使用此工具类中的默认时间) . <br/>
     *
     * @param key    键 . <br/>
     * @param object 缓存对象 . <br/>
     * @return true or false . <br/>
     * @throws Exception
     */
    public static Boolean save(Object key, Object object) {
        return save(key, object, DEFAULT_CACHE_SECONDS);
    }

    /**
     * 保存一个对象到redis中并指定过期时间
     *
     * @param key     键 . <br/>
     * @param value  缓存对象 . <br/>
     * @param seconds 过期时间（单位为秒）.<br/>
     * @return true or false .
     */
    public static Boolean save(Object key, Object value, int seconds) {
        logger.debug("redis保存成功【" + "key：" + JSON.toJSONString(key) + ", value：" + JSON.toJSONString(value) + ", time："
                + seconds + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(value));
            if (seconds > 0) {
                jedis.expire(SerializeUtils.serialize(key), seconds);
            }
            logger.debug("redis保存成功");
            return true;
        } catch (Exception e) {
            logger.error("Cache保存失败【" + "key：" + JSON.toJSONString(key) + ", value：" + JSON.toJSONString(value) + "】");
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 根据缓存键进行数值++
     *
     * @param key 键
     * @return 值++后的结果
     */
    public static Long incr(Object key) {
        logger.debug("redis进行键值++【 key：" + JSON.toJSONString(key) + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            byte[] serKey = SerializeUtils.serialize(key);
            Long incrValue = jedis.incr(serKey);
            logger.debug("redis进行键值++【 key：" + key + ", value：" + JSON.toJSONString(incrValue) + "】");
            return incrValue;
        } catch (Exception e) {
            logger.error("redis进行键值++失败：" + e);
            logger.error("key：" + JSON.toJSONString(key));
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 根据缓存键获取Redis缓存中的值.<br/>
     *
     * @param key 键.<br/>
     * @return Object .<br/>
     * @throws Exception
     */
    public static Object get(Object key) {
        logger.debug("redis获取【 key：" + JSON.toJSONString(key) + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            byte[] obj = jedis.get(SerializeUtils.serialize(key));
            Object value = obj == null ? null : SerializeUtils.unSerialize(obj);
            logger.debug("redis获取结果【 key：" + key + ", value：" + JSON.toJSONString(value) + "】");
            return value;
        } catch (Exception e) {
            logger.error("Cache获取失败：" + e);
            logger.error("key：" + JSON.toJSONString(key));
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 根据缓存键值自增长
     */
    public static Long IncrString(String key) {
        logger.debug("redis进行键值++【 key：" + JSON.toJSONString(key) + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long incrValue = jedis.incr(key);
            logger.debug("redis进行键值++【 key：" + key + ", value：" + JSON.toJSONString(incrValue) + "】");
            return incrValue;
        } catch (Exception e) {
            logger.error("redis进行键值++失败：" + e);
            logger.error("key：" + JSON.toJSONString(key));
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 保存一个字符串到redis中并指定过期时间
     */
    public static Boolean saveString(String key, String value, int seconds) {
        logger.debug("redis保存成功【" + "key：" + key
                + ", value：" + value
                + ", time：" + seconds + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            logger.debug("redis保存成功【" + "key：" + key + "]");
            return true;
        } catch (Exception e) {
            logger.error("Cache保存失败【" + "key：" + key + ", value：" + value + "】");
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 根据字符串键获取Redis缓存中的字符串值.<br/>
     */
    public static Object getString(String key) {
        logger.debug("redis获取【 key：" + key + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            String value = jedis.get(key);
            logger.debug("redis获取结果【 key：" + key + ", value：" + JSON.toJSONString(value) + "】");
            return value;
        } catch (Exception e) {
            logger.error("Cache获取失败：" + e);
            logger.error("key：" + JSON.toJSONString(key));
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 保存一个字符串到Redis中(缓存过期时间:使用此工具类中的默认时间).<br/>
     */
    public static Boolean saveString(String key, String value) {
        return saveString(key, value, DEFAULT_CACHE_SECONDS);
    }

    /**
     * 根据缓存键清除Redis缓存中的值.<br/>
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static Boolean del(Object key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long del = jedis.del(SerializeUtils.serialize(key));
            return del != null && del > 0;
        } catch (Exception e) {
            logger.error("Cache删除失败：" + e);
            logger.error("key：" + JSON.toJSONString(key));
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 根据缓存键清除Redis缓存中的值.<br/>
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static Boolean delStr(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long del = jedis.del(key);
            return del != null && del > 0;
        } catch (Exception e) {
            logger.error("Cache删除失败：" + e);
            logger.error("key：" + JSON.toJSONString(key));
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 根据缓存键清除Redis缓存中的值.<br/>
     *
     * @param keys
     * @return
     * @throws Exception
     */
    public static Boolean del(Object... keys) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.del(SerializeUtils.serialize(keys));
            return true;
        } catch (Exception e) {
            logger.error("Cache删除失败：" + e);
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * @param key
     * @param seconds 超时时间（单位为秒）
     * @return
     */
    public static Boolean expire(Object key, int seconds) {

        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.expire(SerializeUtils.serialize(key), seconds);
            return true;
        } catch (Exception e) {
            logger.error("Cache设置超时时间失败：" + e);
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * @param key
     * @param seconds 超时时间（单位为秒）
     * @return
     */
    public static Boolean expireStr(String key, int seconds) {

        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.expire(key, seconds);
            return true;
        } catch (Exception e) {
            logger.error("Cache设置超时时间失败：" + e);
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 添加一个内容到指定key的hash中
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Boolean addHash(String key, Object field, Object value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
            return true;
        } catch (Exception e) {
            logger.error("Cache保存失败：" + e);
            return false;
        } finally {
            releaseResource(jedis);
        }
    }


    /**
     * 从指定hash中拿一个对象
     *
     * @param key
     * @return
     */
    public static Map<Object, Object> getHash(Object key) {
        Map<Object, Object> resultMap = null;
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Map<byte[], byte[]> hmap = jedis.hgetAll(SerializeUtils.serialize(key));
            if (hmap != null) {
                resultMap = new HashMap<Object, Object>();
                for (Entry<byte[], byte[]> entity : hmap.entrySet()) {
                    resultMap.put(SerializeUtils.unSerialize(entity.getKey()), SerializeUtils.unSerialize(entity.getValue()));
                }
            }
            return resultMap;
        } catch (Exception e) {
            logger.error("Cache读取失败：" + e);
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 从指定hash中拿一个对象
     *
     * @param key
     * @param field
     * @return
     */
    public static Object getHash(Object key, Object field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            byte[] obj = jedis.hget(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
            return SerializeUtils.unSerialize(obj);
        } catch (Exception e) {
            logger.error("Cache读取失败：" + e);
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 从hash中删除指定filed的值
     *
     * @param key
     * @param field
     * @return
     */
    public static Boolean delHash(Object key, Object field) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            long result = jedis.hdel(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
            return result == 1 ? true : false;
        } catch (Exception e) {
            logger.error("Cache删除失败：" + e);
            return null;
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 判断一个key是否存在
     *
     * @param key
     * @return
     */
    public static Boolean exists(Object key) {
        ShardedJedis jedis = null;
        Boolean result = false;
        try {
            jedis = shardedJedisPool.getResource();
            result = jedis.exists(SerializeUtils.serialize(key));
            return result;
        } catch (Exception e) {
            logger.error("Cache获取失败：" + e);
            return false;
        } finally {
            releaseResource(jedis);
        }
    }

    public static ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        RedisUtils.shardedJedisPool = shardedJedisPool;
    }

    public static Boolean setNX(Object key, Object value) {
        return setNX(key, value, 0);
    }

    public static Boolean setNX(Object key, Object value, int seconds) {
        logger.debug("redis保存成功【" + "key：" + JSON.toJSONString(key) + ", value：" + JSON.toJSONString(value) + ", time："
                + seconds + "】");
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Long result = jedis.setnx(SerializeUtils.serialize(key), SerializeUtils.serialize(value));
            if (result == 0) {
                return false;
            }

            if (seconds > 0) {
                jedis.expire(SerializeUtils.serialize(key), seconds);
            }
            logger.debug("redis保存成功");
            return true;
        } catch (Exception e) {
            logger.error("Cache保存失败【" + "key：" + JSON.toJSONString(key) + ", value：" + JSON.toJSONString(value) + "】");
            return false;
        } finally {
            releaseResource(jedis);
        }
    }
}

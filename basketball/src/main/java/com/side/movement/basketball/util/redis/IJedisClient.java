package com.side.movement.basketball.util.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * redis 接口
 * @author zrm
 *
 */
public interface IJedisClient {
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */

	public String get(String key);

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public Object getObject(String key);

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String set(String key, String value, int cacheSeconds);

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setObject(String key, Object value, int cacheSeconds);

	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public List<String> getList(String key);

	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public List<Object> getObjectList(String key);

	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setList(String key, List<String> value, int cacheSeconds);

	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setObjectList(String key, List<Object> value, int cacheSeconds);

	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long listAdd(String key, String... value);

	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long listObjectAdd(String key, Object... value);

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public Set<String> getSet(String key);

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public Set<Object> getObjectSet(String key);

	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setSet(String key, Set<String> value, int cacheSeconds);

	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setObjectSet(String key, Set<Object> value, int cacheSeconds);

	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long setSetAdd(String key, String... value);

	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long setSetObjectAdd(String key, Object... value);

	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public Map<String, String> getMap(String key);

	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public Map<String, Object> getObjectMap(String key);

	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setMap(String key, Map<String, String> value, int cacheSeconds);

	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setObjectMap(String key, Map<String, Object> value,
                               int cacheSeconds);

	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public String mapPut(String key, Map<String, String> value);

	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public String mapObjectPut(String key, Map<String, Object> value);

	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long mapRemove(String key, String mapKey);

	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long mapObjectRemove(String key, String mapKey);

	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean mapExists(String key, String mapKey);

	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean mapObjectExists(String key, String mapKey);

	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public long del(String key);

	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public long delObject(String key);

	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public boolean exists(String key);

	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public boolean existsObject(String key);
	/**
	 * 尝试获取分布式锁
	 * @param lockKey
	 * @param requestId
	 * @param expireTime
	 * @return
	 */
	 public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime);
	 /**
	  * 释放分布式锁
	  * @param lockKey
	  * @param requestId
	  * @return
	  */
	 public boolean releaseDistributedLock(String lockKey, String requestId);
	 /**
	  * 插入有序集合
	  * @param key
	  * @param score
	  * @param member
	  * @return
	  */
	 public Long zadd(String key, Double score, String member);
	 /**
	  * 右侧插入队列
	  * @param key
	  * @param value
	  * @return
	  */
	 public Long rpush(String key, String value);
	 /**
	  * 阻塞弹栈
	  * @param key
	  * @return
	  */
	 public String blpop(int time, String key);
	 
	public Long incr(String key);
	public Long decr(String key);
	public Long expire(String key, int seconds);

}

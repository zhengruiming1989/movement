package com.side.movement.basketball.util.redis.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.side.movement.basketball.util.redis.IJedisClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisException;

public class JedisClusterClient implements IJedisClient {
	private Logger logger = LoggerFactory.getLogger(JedisClusterClient.class);

	@Autowired
	private JedisCluster jedisCluster;


	@Override
	public String get(String key) {
		String value = null;
		try {
			if (jedisCluster.exists(key)) {
				value = jedisCluster.get(key);
				value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
				logger.debug("get {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("get {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return value;
	}


	@Override
	public Object getObject(String key) {
		Object value = null;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				value = toObject(jedisCluster.get(getBytesKey(key)));
				logger.debug("getObject {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObject {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
			// try {
			// jedisCluster.close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		return value;
	}


	@Override
	public String set(String key, String value, int cacheSeconds) {
		String result = null;

		try {

			result = jedisCluster.set(key, value);
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("set {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("set {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	
	@Override
	public String setObject(String key, Object value, int cacheSeconds) {
		String result = null;

		try {
			result = jedisCluster.set(getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setObject {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObject {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#getList(java.lang.String)
	 */
	@Override
	public List<String> getList(String key) {
		List<String> value = null;
		try {
			if (jedisCluster.exists(key)) {
				value = jedisCluster.lrange(key, 0, -1);
				logger.debug("getList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getList {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#getObjectList(java.lang.String)
	 */
	@Override
	public List<Object> getObjectList(String key) {
		List<Object> value = null;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				List<byte[]> list = jedisCluster.lrange(getBytesKey(key), 0, -1);
				value = Lists.newArrayList();
				for (byte[] bs : list) {
					value.add(toObject(bs));
				}
				logger.debug("getObjectList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObjectList {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setList(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public long setList(String key, List<String> value, int cacheSeconds) {
		long result = 0;
		try {
			if (jedisCluster.exists(key)) {
				jedisCluster.del(key);
			}
			result = jedisCluster.rpush(key, (String[]) value.toArray());
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setList {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setObjectList(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public long setObjectList(String key, List<Object> value, int cacheSeconds) {
		long result = 0;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				jedisCluster.del(key);
			}
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value) {
				list.add(toBytes(o));
			}
			result = jedisCluster.rpush(getBytesKey(key), (byte[][]) list.toArray());
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setObjectList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectList {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#listAdd(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long listAdd(String key, String... value) {
		long result = 0;
		try {
			result = jedisCluster.rpush(key, value);
			logger.debug("listAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("listAdd {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#listObjectAdd(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public long listObjectAdd(String key, Object... value) {
		long result = 0;
		try {
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value) {
				list.add(toBytes(o));
			}
			result = jedisCluster.rpush(getBytesKey(key), (byte[][]) list.toArray());
			logger.debug("listObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("listObjectAdd {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#getSet(java.lang.String)
	 */
	@Override
	public Set<String> getSet(String key) {
		Set<String> value = null;
		try {
			if (jedisCluster.exists(key)) {
				value = jedisCluster.smembers(key);
				logger.debug("getSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getSet {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#getObjectSet(java.lang.String)
	 */
	@Override
	public Set<Object> getObjectSet(String key) {
		Set<Object> value = null;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				value = Sets.newHashSet();
				Set<byte[]> set = jedisCluster.smembers(getBytesKey(key));
				for (byte[] bs : set) {
					value.add(toObject(bs));
				}
				logger.debug("getObjectSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObjectSet {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setSet(java.lang.String,
	 * java.util.Set, int)
	 */
	@Override
	public long setSet(String key, Set<String> value, int cacheSeconds) {
		long result = 0;
		try {
			if (jedisCluster.exists(key)) {
				jedisCluster.del(key);
			}
			result = jedisCluster.sadd(key, (String[]) value.toArray());
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setSet {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSet {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setObjectSet(java.lang.String,
	 * java.util.Set, int)
	 */
	@Override
	public long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		long result = 0;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				jedisCluster.del(key);
			}
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value) {
				set.add(toBytes(o));
			}
			result = jedisCluster.sadd(getBytesKey(key), (byte[][]) set.toArray());
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setObjectSet {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectSet {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setSetAdd(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long setSetAdd(String key, String... value) {
		long result = 0;
		try {
			result = jedisCluster.sadd(key, value);
			logger.debug("setSetAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSetAdd {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setSetObjectAdd(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public long setSetObjectAdd(String key, Object... value) {
		long result = 0;
		try {
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value) {
				set.add(toBytes(o));
			}
			result = jedisCluster.rpush(getBytesKey(key), (byte[][]) set.toArray());
			logger.debug("setSetObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSetObjectAdd {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#getMap(java.lang.String)
	 */
	@Override
	public Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		try {
			if (jedisCluster.exists(key)) {
				value = jedisCluster.hgetAll(key);
				logger.debug("getMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getMap {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#getObjectMap(java.lang.String)
	 */
	@Override
	public Map<String, Object> getObjectMap(String key) {
		Map<String, Object> value = null;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				value = Maps.newHashMap();
				Map<byte[], byte[]> map = jedisCluster.hgetAll(getBytesKey(key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
					value.put(byteArrayToStr(e.getKey()), toObject(e.getValue()));
				}
				logger.debug("getObjectMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObjectMap {} = {}", key, value, e);
		} finally {
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setMap(java.lang.String,
	 * java.util.Map, int)
	 */
	@Override
	public String setMap(String key, Map<String, String> value, int cacheSeconds) {
		String result = null;
		try {
			if (jedisCluster.exists(key)) {
				jedisCluster.del(key);
			}
			result = jedisCluster.hmset(key, value);
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setMap {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#setObjectMap(java.lang.String,
	 * java.util.Map, int)
	 */
	@Override
	public String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		String result = null;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				jedisCluster.del(key);
			}
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()) {
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedisCluster.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
			logger.debug("setObjectMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectMap {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#mapPut(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public String mapPut(String key, Map<String, String> value) {
		String result = null;
		try {
			result = jedisCluster.hmset(key, value);
			logger.debug("mapPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("mapPut {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#mapObjectPut(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public String mapObjectPut(String key, Map<String, Object> value) {
		String result = null;
		try {
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()) {
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedisCluster.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
			logger.debug("mapObjectPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("mapObjectPut {} = {}", key, value, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#mapRemove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long mapRemove(String key, String mapKey) {
		long result = 0;
		try {
			result = jedisCluster.hdel(key, mapKey);
			logger.debug("mapRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapRemove {}  {}", key, mapKey, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#mapObjectRemove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long mapObjectRemove(String key, String mapKey) {
		long result = 0;
		try {
			result = jedisCluster.hdel(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#mapExists(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean mapExists(String key, String mapKey) {
		boolean result = false;
		try {
			result = jedisCluster.hexists(key, mapKey);
			logger.debug("mapExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapExists {}  {}", key, mapKey, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#mapObjectExists(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean mapObjectExists(String key, String mapKey) {
		boolean result = false;
		try {
			result = jedisCluster.hexists(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectExists {}  {}", key, mapKey, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#del(java.lang.String)
	 */
	@Override
	public long del(String key) {
		long result = 0;
		try {
			if (jedisCluster.exists(key)) {
				result = jedisCluster.del(key);
				logger.debug("del {}", key);
			} else {
				logger.debug("del {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn("del {}", key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#delObject(java.lang.String)
	 */
	@Override
	public long delObject(String key) {
		long result = 0;
		try {
			if (jedisCluster.exists(getBytesKey(key))) {
				result = jedisCluster.del(getBytesKey(key));
				logger.debug("delObject {}", key);
			} else {
				logger.debug("delObject {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn("delObject {}", key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key) {
		boolean result = false;
		try {
			result = jedisCluster.exists(key);
			logger.debug("exists {}", key);
		} catch (Exception e) {
			logger.warn("exists {}", key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeeplus.common.redis.IJedisClient#existsObject(java.lang.String)
	 */
	@Override
	public boolean existsObject(String key) {
		boolean result = false;
		try {
			result = jedisCluster.exists(getBytesKey(key));
			logger.debug("existsObject {}", key);
		} catch (Exception e) {
			logger.warn("existsObject {}", key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取byte[]类型Key
	 * @param object
	 * @return
	 */
	public static byte[] getBytesKey(Object object) {
		if (object instanceof String) {
			return strToByteArray((String) object);
		} else {
			return toByteArray(object);
		}
	}

	/**
	 * Object转换byte[]类型
	 * @param object
	 * @return
	 */
	public static byte[] toBytes(Object object) {
		return toByteArray(object);
	}

	/**
	 * byte[]型转换Object
	 * @param bytes
	 * @return
	 */
	private static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 对象转数组
	 * 
	 * @param obj
	 * @return
	 */
	private static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	/**
	 * string转byte数组
	 * 
	 * @param str
	 * @return
	 */
	private static byte[] strToByteArray(String str) {
		if (str == null) {
			return null;
		}
		byte[] byteArray = str.getBytes();
		return byteArray;
	}

	/**
	 * byte数组转String
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String byteArrayToStr(byte[] byteArray) {
		if (byteArray == null) {
			return null;
		}
		String str = new String(byteArray);
		return str;
	}


	@Override
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
		// TODO Auto-generated method stub
		try {
			String result = jedisCluster.set(lockKey, requestId, "NX", "PX", expireTime);

			if ("OK".equals(result)) {
				return true;
			}
			return false;
		} catch (JedisException e) {
			logger.warn("getResource.", e);
		} finally {
			//returnBrokenResource(jedis);
		}
		return false;
	}


	@Override
	public boolean releaseDistributedLock(String lockKey, String requestId) {
		// TODO Auto-generated method stub
		try {
			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
			Object result = jedisCluster.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
			Long start = 1L;
			if (start.equals(result)) {
				return true;
			}
			return false;
		} catch (JedisException e) {
			logger.warn("getResource.", e);
		} finally {
			//returnBrokenResource(jedis);
		}
		return false;
	}


	@Override
	public Long zadd(String key, Double score, String member) {
		// TODO Auto-generated method stub
		Long result = null;
		try {
			result = jedisCluster.zadd(key, score, member);
			logger.debug("zadd {} = {}", key, key);
		} catch (Exception e) {
			logger.warn("zadd {} = {}", key, key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}
	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		Long result = null;
		try {
			result = jedisCluster.incr(key);
			logger.debug("incr key = ",  key);
		} catch (Exception e) {
			logger.warn("incr key = ",  key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}
	@Override
	public Long decr(String key) {
		// TODO Auto-generated method stub
		Long result = null;
		try {
			result = jedisCluster.decr(key);
			logger.debug("decr key = ",  key);
		} catch (Exception e) {
			logger.warn("decr key = ",  key, e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}
	@Override
	public Long expire(String key,int seconds) {
		// TODO Auto-generated method stub
		Long result = null;
		try {
			result = jedisCluster.expire(key,seconds);
			logger.debug("expire key {} , {} ",  key,seconds);
		} catch (Exception e) {
			logger.warn("expire key {} , {} ",  key, seconds,e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}


	@Override
	public Long rpush(String key, String value) {
		// TODO Auto-generated method stub
		Long result = null;
		try {
			result = jedisCluster.rpush(key, value);
			logger.debug("rpush key {} , {} ",  key,value);
		} catch (Exception e) {
			logger.warn("rpush key {} , {} ",  key, value,e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}


	@Override
	public String blpop(int time,String key) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			List<String> list = jedisCluster.blpop(time, key);
			for (String value : list) {
				result = value;
			}
			logger.debug("blpop key {} , {} ", key);
		} catch (Exception e) {
			logger.warn("blpop key {} , {} ", key,e);
		} finally {
			// returnResource(jedis);
		}
		return result;
	}
}

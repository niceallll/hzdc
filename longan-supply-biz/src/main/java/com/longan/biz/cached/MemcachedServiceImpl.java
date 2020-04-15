package com.longan.biz.cached;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemcachedServiceImpl implements MemcachedService {
    private static final Logger logger = LoggerFactory.getLogger(MemcachedServiceImpl.class);

    private static final String MUTEX_KEY_PREFIX = "MUTEX_";
    private ReentrantReadWriteLock addLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock countLock = new ReentrantReadWriteLock();

    @Resource
    private MemcachedClient memcachedClient;

    @Override
    public Object get(String key) {
	Object object = null;
	try {
	    object = memcachedClient.get(key);
	} catch (Exception ex) {
	    logger.error("memcached get error key : " + key, ex);
	}
	return object;
    }

    @Override
    public Integer getIntValue(String key) {
	Integer result = null;
	try {
	    Object object = memcachedClient.get(key);
	    if (object != null && object instanceof Integer) {
		result = (Integer) object;
	    } else {
		logger.error("memcached getIntValue error key : " + key + " value is not int value: " + object);
	    }
	} catch (Exception ex) {
	    logger.error("memcached getIntValue error key : " + key, ex);
	}
	return result;
    }

    @Override
    public Long getLongValue(String key) {
	Long result = null;
	try {
	    Object object = memcachedClient.get(key);
	    if (object != null && object instanceof String) {
		String str = (String) object;
		if (StringUtils.isNumeric(str.trim())) {
		    result = Long.parseLong(str.trim());
		} else {
		    logger.error("memcached getLongValue error key : " + key + " value is not long value: " + str);
		}
	    } else if (object != null && object instanceof Long) {
		result = (Long) object;
	    } else if (object != null && object instanceof Integer) {
		result = ((Integer) object).longValue();
	    } else {
		logger.error("memcached getLongValue error key : " + key + " value is not long value: " + object);
	    }
	} catch (Exception ex) {
	    logger.error("memcached getLongValue error key : " + key, ex);
	}
	return result;
    }

    @Override
    public Boolean getBooleanValue(String key) {
	Boolean result = false;
	try {
	    Object object = memcachedClient.get(key);
	    if (object != null && object instanceof String) {
		String str = (String) object;
		if (StringUtils.equals("true", (str.trim()))) {
		    result = true;
		} else {
		    logger.error("memcached getBooleanValue error key : " + key + " value is not boolean value: " + str);
		}
	    } else if (object != null && object instanceof Boolean) {
		result = (Boolean) object;
	    } else {
		logger.error("memcached getBooleanValue error key : " + key + " value is not boolean value: " + object);
	    }
	} catch (Exception ex) {
	    logger.error("memcached getBooleanValue error key : " + key, ex);
	}
	return result;
    }

    @Override
    public String getStringValue(String key) {
	String result = null;
	try {
	    Object object = memcachedClient.get(key);
	    if (object != null && object instanceof String) {
		result = (String) object;
	    } else {
		logger.error("memcached getStringValue error key : " + key + " value is not string value: " + object);
	    }
	} catch (Exception ex) {
	    logger.error("memcached getStringValue error key : " + key, ex);
	}
	return result;
    }

    @Override
    public void set(String key, int exp, Object object) {
	try {
	    memcachedClient.set(key, exp, object);
	} catch (Exception ex) {
	    logger.error("memcached set error key : " + key, ex);
	}
    }

    @Override
    public boolean add(String key, int exp, Object object) {
	boolean status = false;
	try {
	    addLock.writeLock().lock();
	    if (memcachedClient.add(key, exp, object).getStatus().isSuccess()) {
		status = true;
	    }
	} catch (Exception ex) {
	    status = true;// 写失败、暂作成功处理、避免亏损
	    logger.error("memcached add error key : " + key, ex);
	} finally {
	    addLock.writeLock().unlock();
	}
	return status;
    }

    @Override
    public boolean isMutex(String key, int exp) {
	boolean status = true;
	try {
	    if (memcachedClient.add(MUTEX_KEY_PREFIX + key, exp, "mutex").getStatus().isSuccess()) {
		status = false;
	    }
	} catch (Exception ex) {
	    logger.error("memcached isMutex error key : " + key, ex);
	    status = false;
	}
	return status;
    }

    @Override
    public Long inc(String key, Integer count) {
	try {
	    countLock.writeLock().lock();
	    return memcachedClient.incr(key, count, 1);
	} catch (Exception ex) {
	    logger.error("memcached inc error key : " + key, ex);
	} finally {
	    countLock.writeLock().unlock();
	}
	return null;
    }

    @Override
    public Long inc(String key, Integer count, long def) {
	try {
	    countLock.writeLock().lock();
	    return memcachedClient.incr(key, count, def);
	} catch (Exception ex) {
	    logger.error("memcached inc error key : " + key, ex);
	} finally {
	    countLock.writeLock().unlock();
	}
	return null;
    }

    @Override
    public Long dec(String key, Integer count) {
	try {
	    countLock.writeLock().lock();
	    return memcachedClient.decr(key, count);
	} catch (Exception ex) {
	    logger.error("memcached dec error key : " + key, ex);
	} finally {
	    countLock.writeLock().unlock();
	}
	return null;
    }

    @Override
    public void initCount(String key, int exp, Long count) {
	if (count == null) {
	    return;
	}

	try {
	    // 这里居然要 toString 才可以算计数，费解
	    memcachedClient.set(key, exp, count.toString());
	} catch (Exception ex) {
	    logger.error("memcached set error key : " + key, ex);
	}
    }

    @Override
    public void delete(String key) {
	try {
	    memcachedClient.delete(key);
	} catch (Exception ex) {
	    logger.error("memcached delete error key : " + key, ex);
	}
    }
}

package com.longan.biz.cached;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ThrilleR
 * 
 * @param <K>
 * @param <V>
 */
public abstract class LocalCached<K, V> implements Cached<K, V> {
    private ConcurrentMap<K, V> cached = new ConcurrentHashMap<K, V>();

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(8);

    /** 读写锁， 读的时候不互斥，写的时候互斥，保证在更新数据的时候，都不能读 */
    protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Boolean needClear = true;

    void init(Long initialDelay, Long period) {
	ses.scheduleAtFixedRate(new Runnable() {
	    public void run() {
		reload();
	    }
	}, initialDelay, period, TimeUnit.SECONDS);

	reload();
    }

    public void reload() {
	Long timeStart = System.currentTimeMillis();
	ConcurrentMap<K, V> newCached = createNewCahcedObject();
	ConcurrentMap<K, V> tempCached = cached;

	// reload from db
	reloadFromDb(newCached);

	logger.warn(this.getClass().getName() + " reload from db cost : " + (System.currentTimeMillis() - timeStart) + "ms");

	if (newCached.isEmpty()) {
	    logger.error(this.getClass().getName() + " reload  elements is empty");
	    return;
	}

	try {
	    lock.writeLock().lock();
	    timeStart = System.currentTimeMillis();
	    resetCachedObject(newCached);
	    tempCached.clear();
	    tempCached = null;

	    logger.warn(this.getClass().getName() + " reload cost : " + (System.currentTimeMillis() - timeStart) + "ms");
	} finally {
	    lock.writeLock().unlock();
	}
    }

    private ConcurrentMap<K, V> createNewCahcedObject() {
	return new ConcurrentHashMap<K, V>();
    }

    private void resetCachedObject(ConcurrentMap<K, V> newCached) {
	cached = newCached;
    }

    abstract void reloadFromDb(ConcurrentMap<K, V> cached);

    public V get(K k) {
	V result;
	try {
	    lock.readLock().lock();
	    result = cached.get(k);
	} finally {
	    lock.readLock().unlock();
	}
	return result;
    }

    public Map<K, V> getMap() {
	Map<K, V> map = new HashMap<K, V>();
	try {
	    lock.readLock().lock();
	    for (Map.Entry<K, V> entry : cached.entrySet()) {
		map.put(entry.getKey(), entry.getValue());
	    }
	    return map;
	} finally {
	    lock.readLock().unlock();
	}
    }

    public List<V> getList() {
	List<V> list = new ArrayList<V>();
	try {
	    lock.readLock().lock();
	    for (Map.Entry<K, V> entry : cached.entrySet()) {
		list.add(entry.getValue());
	    }
	    return list;
	} finally {
	    lock.readLock().unlock();
	}
    }

    public void clear() {
	if (needClear) {
	    needClear = false;
	    ses.shutdownNow();
	    logger.warn("LocalCached clear！");
	}
    }
}

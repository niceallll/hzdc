package com.hzdc.biz.test;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.spy.memcached.MemcachedClient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.longan.biz.pojo.PddCacheToken;
import com.longan.biz.utils.DateTool;

public class testcache {
    private static MemcachedClient client;
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws Exception {
	// cache();
	System.out.println(DateTool.beforeSeconds(new Date(), 60));
    }

    private static void cache() {
	try {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("test-cache.xml");
	    client = (MemcachedClient) ctx.getBean("memcachedClient");
	    PddCacheToken itoken = new PddCacheToken();
	    itoken.setAccessToken("token");
	    itoken.setRefreshToken("refresh");
	    itoken.setDeadline(5 * 60);
	    client.set("pdd_token", 30, itoken);
	    TimeUnit.MINUTES.sleep(1);
	    PddCacheToken otoken = (PddCacheToken) client.get("pdd_token");
	    System.out.println(otoken.getAccessToken());
	    System.out.println(otoken.getRefreshToken());
	    System.out.println(otoken.getDeadline());
	    // client.delete("test");
	    // System.out.println(client.add("test", 600, true).getStatus().isSuccess());
	    // System.out.println(client.add("test", 600, true).getStatus().isSuccess());
	    // System.out.println(client.add("test", 600, true).getStatus().isSuccess());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private static void testthread() {
	ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(4);
	initCount("test", 1000, 0l);
	ses.execute(new Runnable() {
	    @Override
	    public void run() {
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
	    }
	});
	ses.execute(new Runnable() {
	    @Override
	    public void run() {
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
		System.out.println("inc:" + inc("test", 1));
	    }
	});
	ses.execute(new Runnable() {
	    @Override
	    public void run() {
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
	    }
	});
	ses.execute(new Runnable() {
	    @Override
	    public void run() {
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
		System.out.println(dec("test", 1));
	    }
	});

	try {
	    Thread.sleep(5000);
	} catch (Exception ex) {
	}
	System.out.println("finish:" + client.get("test"));
    }

    private static Long inc(String key, Integer count) {
	try {
	    lock.writeLock().lock();
	    return client.incr(key, count, 1);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    lock.writeLock().unlock();
	}
	return null;
    }

    private static Long dec(String key, Integer count) {
	try {
	    lock.writeLock().lock();
	    return client.decr(key, count);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    lock.writeLock().unlock();
	}
	return null;
    }

    private static void initCount(String key, int exp, Long count) {
	try {
	    client.set(key, exp, count.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}

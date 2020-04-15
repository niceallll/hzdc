package com.longan.biz.cached;

public interface MemcachedService {
    public Object get(String key);

    public Integer getIntValue(String key);

    public Long getLongValue(String key);

    public Boolean getBooleanValue(String key);

    public String getStringValue(String key);

    public void set(String key, int exp, Object object);

    public boolean add(String key, int exp, Object object);

    // 是否冲突，如果不冲突返回false ,并且按时间占用锁 , 冲突则返回true，说明被其他进程占用。
    public boolean isMutex(String key, int exp);

    // 计数 ++ 如果没有值，则默认值是1
    public Long inc(String key, Integer count);

    // 计数 ++ 如果没有值，按参数指定默认值
    public Long inc(String key, Integer count, long def);

    // 计数 --
    public Long dec(String key, Integer count);

    // 设置计数
    public void initCount(String key, int exp, Long count);

    public void delete(String key);
}

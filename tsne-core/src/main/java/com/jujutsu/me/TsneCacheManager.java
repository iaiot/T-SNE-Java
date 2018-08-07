package com.me;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存降维的中间计算结果和最终计算结果
 *
 * @author hjl
 */
public class TsneCacheManager {

    /**
     * 临时缓存
     */
    private static Map<String, CacheData> CACHE_DATA = new ConcurrentHashMap<>();

    /**
     * 最终计算结果缓存
     */
    private static Map<String, CacheData> FINAL_CACHE_DATA = new ConcurrentHashMap<>();

    /**
     * 判断是否存在key
     *
     * @param key key
     * @return boolean
     */
    public static boolean containsFinalKey(String key) {
        return FINAL_CACHE_DATA.containsKey(key);
    }

    /**
     * 获取缓存数据
     *
     * @param key key
     * @param <T> 值类型
     * @return 值
     */
    public static <T> T getFinalData(String key) {
        CacheData<T> data = FINAL_CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= 0 || data.getSaveTime() >= new Date().getTime())) {
            return data.getData();
        }
        return null;
    }

    /**
     * setData
     *
     * @param key    key
     * @param data   值
     * @param expire 缓存存活时间
     * @param <T>    <T>
     */
    public static <T> void setFinalData(String key, T data, int expire) {
        FINAL_CACHE_DATA.put(key, new CacheData(data, expire));
    }

    /**
     * 获取缓存数据
     *
     * @param key    key
     * @param load   load
     * @param expire 缓存存活时间
     * @param <T>    值类型
     * @return 值
     */
    public static <T> T getData(String key, Load<T> load, int expire) {
        T data = getData(key);
        if (data == null && load != null) {
            data = load.load();
            if (data != null) {
                setData(key, data, expire);
            }
        }
        return data;
    }

    /**
     * 获取缓存数据
     *
     * @param key key
     * @param <T> 值类型
     * @return 值
     */
    public static <T> T getData(String key) {
        CacheData<T> data = CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= 0 || data.getSaveTime() >= new Date().getTime())) {
            return data.getData();
        }
        return null;
    }

    /**
     * setData
     *
     * @param key    key
     * @param data   值
     * @param expire 缓存存活时间
     * @param <T>    <T>
     */
    public static <T> void setData(String key, T data, int expire) {
        CACHE_DATA.put(key, new CacheData(data, expire));
    }

    /**
     * 判断是否存在key
     *
     * @param key key
     * @return boolean
     */
    public static boolean containsKey(String key) {
        return CACHE_DATA.containsKey(key);
    }

    /**
     * 根据key移除缓存
     *
     * @param key key
     */
    public static void clear(String key) {
        CACHE_DATA.remove(key);
    }

    /**
     * 清除全部缓存
     */
    public static void clearAll() {
        CACHE_DATA.clear();
    }

    /**
     * Load<T>
     *
     * @param <T> <T>
     */
    public interface Load<T> {
        /**
         * load
         *
         * @return T
         */
        T load();
    }

    private static class CacheData<T> {
        CacheData(T t, int expire) {
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.saveTime = new Date().getTime() + this.expire;
        }

        /**
         * data
         */
        private T data;

        /**
         * 存活时间
         */
        private long saveTime;

        /**
         * 过期时间 小于等于0标识永久存活
         */
        private long expire;

        public T getData() {
            return data;
        }

        public long getExpire() {
            return expire;
        }

        public long getSaveTime() {
            return saveTime;
        }
    }

}

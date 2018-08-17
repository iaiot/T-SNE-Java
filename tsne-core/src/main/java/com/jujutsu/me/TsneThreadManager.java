package com.jujutsu.me;

import com.jujutsu.tsne.TSneConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TsneThreadManager
 *
 * @author hjl
 */
public class TsneThreadManager {

    /**
     * 多线程开关 | 缓存开关
     */
    public static final boolean ENABLE_THREAD = true;

    /**
     * 第一次计算的超时时间 ms
     */
    private static final long TIME_OUT = 20000;

    /**
     * 单次计算等待时间 ms
     */
    private static final long SINGLE_WAIT_TIME = 500;

    /**
     * 线程集合
     */
    private static Map<String, Thread> TSNE_THRED_MAP = new ConcurrentHashMap<>();

    /**
     * 创建一个线程执行降维计算，并返回中间计算的结果
     *
     * @param threadKey 线程key
     * @param config    TSneConfiguration
     * @return double[][]
     */
    public static double[][] start(String threadKey, TSneConfiguration config) {
        TsneThred therd = new TsneThred(config);
        Thread thread = new Thread(therd);
        thread.start();
        TSNE_THRED_MAP.put(threadKey, thread);
        for (int i = 0; i < TIME_OUT / SINGLE_WAIT_TIME; i++) {
            if (TsneCacheManager.containsKey(threadKey)) {
                return TsneCacheManager.getData(threadKey);
            }
            if (TsneCacheManager.containsFinalKey(threadKey)) {
                return TsneCacheManager.getFinalData(threadKey);
            }
            try {
                Thread.sleep(SINGLE_WAIT_TIME);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据 threadKey 标记某个线程为中断
     *
     * @param threadKey 线程key
     */
    public static void interrupt(String threadKey) {
        TSNE_THRED_MAP.get(threadKey).interrupt();
    }

    /**
     * 判断线程是否存在
     *
     * @param threadKey key
     * @return boolean
     */
    public static boolean containsKey(String threadKey) {
        return TSNE_THRED_MAP.containsKey(threadKey);
    }
    
    /**
     * 根据 threadKey 获取指定线程
     *
     * @param threadKey 线程key
     * @return Thread
     */
    public static Thread getThread(String threadKey) {
        return TSNE_THRED_MAP.get(threadKey);
    }

    /**
     * 从线程集合中删除key为 threadKey 的线程
     *
     * @param threadKey threadKey
     */
    public static void clear(String threadKey) {
        TSNE_THRED_MAP.remove(threadKey);
    }

}

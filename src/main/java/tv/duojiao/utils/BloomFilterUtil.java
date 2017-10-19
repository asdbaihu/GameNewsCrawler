package tv.duojiao.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * Description: 布隆过滤器，主要用于去重
 * User: Yodes
 * Date: 2017/9/14
 */
public class BloomFilterUtil {
    private static int expectedInsertions = 4000;
    private static double fpp = 0.01;
    private static int size = 0;
    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions, fpp);

    public static BloomFilter getInstance() {
        if (bloomFilter != null) {
            return bloomFilter;
        } else {
            return BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions, fpp);
        }
    }

    public static int putSize(int i) {
        size += i;
        return size;
    }

    public static int getSize() {
        return size;
    }

    public static boolean reset() {
        synchronized (bloomFilter) {
            bloomFilter = null;
            size = 0;
            System.gc();
            if (bloomFilter == null) {
                return true;
            } else {
                return false;
            }
        }
    }
}

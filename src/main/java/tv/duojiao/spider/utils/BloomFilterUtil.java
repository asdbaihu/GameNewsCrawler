package tv.duojiao.spider.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * Description: 布隆过滤器，主要用于去重
 * User: Yodes
 * Date: 2017/9/14
 */
public class BloomFilterUtil {
    private static int expectedInsertions = 2500;
    private static double fpp = 0.3;
    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions, fpp);

    public static BloomFilter getInstance() {
        if (bloomFilter != null) {
            return bloomFilter;
        } else {
            return BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions, fpp);
        }
    }

    public static boolean reset() {
        bloomFilter = null;
        System.gc();
        if (bloomFilter == null) {
            return true;
        } else {
            return false;
        }
    }
}

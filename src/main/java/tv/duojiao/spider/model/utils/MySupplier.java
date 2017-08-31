package tv.duojiao.spider.model.utils;

/**
 * MySupplier
 *
 * @author Yodes
 * @version
 */
@FunctionalInterface
public interface MySupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}

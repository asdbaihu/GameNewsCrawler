package tv.duojiao.utils;

public class LangUtil {
    /**
     * 有任何一个元素为空则返回true
     * @param data
     * @return true | false
     */
    public static boolean isAnyOneBlank(String... data) {
        for (int i = 0; i < data.length; i++)
            if (data[i] == null || "".equals(data[i].trim()) || "undefined".equals(data[i].trim())) {
                return true;
            }
        return false;
    }
}

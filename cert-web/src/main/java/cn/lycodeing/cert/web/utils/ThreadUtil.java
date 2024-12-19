package cn.lycodeing.cert.web.utils;

/**
 * 线程工具类
 *
 * @author lycodeing
 */
public class ThreadUtil {

    /**
     * 线程休眠
     *
     * @param millis 毫秒
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

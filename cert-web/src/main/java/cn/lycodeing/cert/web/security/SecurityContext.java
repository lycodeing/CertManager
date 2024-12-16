package cn.lycodeing.cert.web.security;

/**
 *  安全认证上下文
 * @author lycodeing
 */
public class SecurityContext {
    private static final ThreadLocal<Integer> USER_ID = new ThreadLocal<>();

    public static Integer getUserId() {
        return USER_ID.get();
    }

    public static void setUserId(Integer userId) {
        USER_ID.set(userId);
    }

    public static void remove() {
        USER_ID.remove();
    }
}

package util;

public class Utils {
    public static void log(String format, Object... args) {
        System.out.printf((format) + "%n", args);
    }

    public static void ensure(boolean condition, String message) {
        if (!condition) {
            log("%s", message);
        } else {
            log("测试成功");
        }
    }
}

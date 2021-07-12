package myspring.ioc.helper;

import myspring.ioc.ConfigConstant;
import myspring.ioc.util.PropsUtil;

import java.util.Properties;

/**
 * 助手类
 * 借用PropsUtil工具类来实现ConfigHelper助手类，
 * 框架通过ConfigHelper类就可以加载用户自定义的配置文件，
 * 从代码中可以看到，部分配置项拥有默认值，
 * 当用户没有自定义是将使用默认值
 *
 * @author czy
 * @date 2021/7/12
 */
public final class ConfigHelper {
    /**
     * 加载配置文件的属性
     */
    private static final Properties CONFIG_PROPS =
            PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取JDBC驱动配置
     *
     * @return
     */
    public static String getJDBCDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取JDBC的url配置
     *
     * @return
     */
    public static String getJDBCUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取jdbc 账号
     *
     * @return
     */
    public static String getJDBCUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc密码
     *
     * @return
     */
    public static String getJDBCPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名称
     *
     * @return
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用 JSP 路径
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取应用静态资源路径
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    /**
     * 根据属性名称获取String类的书属性值
     * @param key
     * @return
     */
    public static String getString(String key) {
        return PropsUtil.getString(CONFIG_PROPS, key);
    }

    /**
     * 根据属性名称获取int类型的属性值
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return PropsUtil.getInt(CONFIG_PROPS, key);
    }

    /**
     * 根据属性名获取 boolean 类型的属性值
     */
    public static boolean getBoolean(String key) {
        return PropsUtil.getBoolean(CONFIG_PROPS, key);
    }

}

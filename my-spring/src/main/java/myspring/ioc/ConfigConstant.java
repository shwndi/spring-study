package myspring.ioc;

import myspring.ioc.helper.ConfigHelper;

/**
 * 常量接口
 * 维护配置文件中的相关配置项名称
 * 在该类中规定配置文件变量对应名称
 * 通过配置文件获取到集体的值，
 * 再将配置的值赋给具体变量
 * 通过{@link ConfigHelper}的方法实现
 *
 * @author czy
 * @date 2021/7/12
 */
public interface ConfigConstant {
    //配置文件的名称
    String CONFIG_FILE="handwritten.properties";

    //数据源
    String JDBC_DRIVER  = "handwritten.framework.jdbc.driver";
    String JDBC_URL = "handwritten.framework.jdbc.url";
    String JDBC_USERNAME = "handwritten.framework.jdbc.username";
    String JDBC_PASSWORD = "handwritten.framework.jdbc.password";

    //java源码地址
    String APP_BASE_PACKAGE = "handwritten.framework.app.base_package";
    //jsp页面路径
    String APP_JSP_PATH = "handwritten.framework.app.jsp_path";
    //静态资源路径
    String APP_ASSET_PATH = "handwritten.framework.app.asset_path";
}

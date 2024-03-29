package myspring.ioc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 工具类
 * 用于属性文件读取
 * @author czy
 * @date 2021/7/12
 */
public final class PropsUtil {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(PropsUtil.class);
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;
        try {
            is = ClassUtil.getClassLoader().getResourceAsStream(fileName);
            if (is==null){
                throw new FileNotFoundException(fileName+"file is not found");
            }
            props = new Properties();
            props.load(is);
        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure",e);
                }
            }
        }
        return props;
    }
    /**
     * 获取String类型的属性值（默认值为空字符串）
     */
    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }

    /**
     * 获取String类型的属性值（可指定默认值）
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties props,String key,String defaultValue){
        String value =defaultValue;
        if (props.containsKey(key)){
            value= props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取int类型的属性值(默认值为0)
     *
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }

    /**
     * 获取 int 类型的属性值（可指定默认值）
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    private static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.contains(key)){
            value = Integer.parseInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取 boolean 类型属性（默认值为 false）
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取 boolean 类型属性（可指定默认值）
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = Boolean.parseBoolean(props.getProperty(key));
        }
        return value;
    }


}

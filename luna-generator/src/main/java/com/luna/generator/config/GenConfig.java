package com.luna.generator.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 *
 * @author luna
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = {"classpath:generator.yml"})
public class GenConfig {
    /**
     * 作者
     */
    public static String  author;

    /**
     * 生成包路径
     */
    public static String  packageName;

    /**
     * 自动去除表前缀，默认是false
     */
    public static boolean autoRemovePre;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String  tablePrefix;

    public static String  javaPath;

    public static String  vuePath;

    public static String getVuePath() {
        return vuePath;
    }

    @Value("${vuePath}")
    public void setVuePath(String vuePath) {
        GenConfig.vuePath = vuePath;
    }

    public static String getJavaPath() {
        return javaPath;
    }

    @Value("${javaPath}")
    public void setJavaPath(String javaPath) {
        GenConfig.javaPath = javaPath;
    }

    public static String getAuthor()
    {
        return author;
    }

    @Value("${author}")
    public void setAuthor(String author) {
        GenConfig.author = author;
    }

    public static String getPackageName() {
        return packageName;
    }

    @Value("${packageName}")
    public void setPackageName(String packageName) {
        GenConfig.packageName = packageName;
    }

    public static boolean getAutoRemovePre() {
        return autoRemovePre;
    }

    @Value("${autoRemovePre}")
    public void setAutoRemovePre(boolean autoRemovePre) {
        GenConfig.autoRemovePre = autoRemovePre;
    }

    public static String getTablePrefix() {
        return tablePrefix;
    }

    @Value("${tablePrefix}")
    public void setTablePrefix(String tablePrefix) {
        GenConfig.tablePrefix = tablePrefix;
    }
}

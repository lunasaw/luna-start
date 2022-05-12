package com.luna.generator.enums;

import com.luna.common.utils.ExtendUtils;

import java.util.Arrays;

/**
 * @author luna
 * 2021/11/1
 */
public enum ColumnSettingEnum {

    SORT_ABEL("sortAble", false, Boolean.class, "生成模版:%s"),

    ;

    public static String getText(String name, String value) {
        ColumnSettingEnum type = getType(name);
        return type.getText(value);
    }

    public static ColumnSettingEnum getType(String name) {
        return Arrays.stream(values()).filter(v -> v.getName().equals(name)).findFirst().orElse(null);
    }

    String name;
    Object defaultValue;
    Class  clazz;
    String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getText(String value) {
        return String.format(text, value);
    }

    public void setText(String text) {
        this.text = text;
    }

    ColumnSettingEnum(String name, Object defaultValue, Class clazz, String text) {

        this.name = name;
        this.defaultValue = defaultValue;
        this.clazz = clazz;
        this.text = text;
    }

    public static Object getValue(String extend, ColumnSettingEnum columnSettingEnum) {
        String featuresValue = ExtendUtils.getFeaturesValue(extend, columnSettingEnum.getName());
        if (Integer.class.equals(columnSettingEnum.getClazz())) {
            return ExtendUtils.getInteger(featuresValue, (Integer)columnSettingEnum.getDefaultValue());
        } else if (Boolean.class.equals(columnSettingEnum.getClazz())) {
            return ExtendUtils.getBoolean(featuresValue, (Boolean)columnSettingEnum.getDefaultValue());
        } else {
            return ExtendUtils.getString(featuresValue, (String)columnSettingEnum.getDefaultValue());
        }
    }

    public static Boolean getSortAble(String extend) {
        return (Boolean)getValue(extend, ColumnSettingEnum.SORT_ABEL);
    }

    public static String setSortAble(String extend, Boolean value) {
        if (null != value) {
            return ExtendUtils.setFeaturesValue(extend, ColumnSettingEnum.SORT_ABEL.getName(), value);
        }
        return extend;
    }
}

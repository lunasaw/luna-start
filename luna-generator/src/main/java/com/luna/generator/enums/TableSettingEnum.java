package com.luna.generator.enums;

import java.util.Arrays;
import java.util.Optional;

import com.luna.common.utils.ExtendUtils;
import sun.misc.VM;

/**
 * @author luna
 * 2021/11/1
 */
public enum TableSettingEnum {

    GENERATE_TEMPLATE("template", VmTypeEnum.MYBATIS_PLUS_SWAGGER, VmTypeEnum.class, "字段是否可排序:%s"),

    ;

    public static String getText(String name, String value) {
        TableSettingEnum type = getType(name);
        return type.getText(value);
    }

    public static TableSettingEnum getType(String name) {
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

    TableSettingEnum(String name, Object defaultValue, Class clazz, String text) {

        this.name = name;
        this.defaultValue = defaultValue;
        this.clazz = clazz;
        this.text = text;
    }

    public static Object getValue(String extend, TableSettingEnum columnSettingEnum) {
        String featuresValue = ExtendUtils.getFeaturesValue(extend, columnSettingEnum.getName());
        if (Integer.class.equals(columnSettingEnum.getClazz())) {
            return ExtendUtils.getInteger(featuresValue, (Integer)columnSettingEnum.getDefaultValue());
        } else if (Boolean.class.equals(columnSettingEnum.getClazz())) {
            return ExtendUtils.getBoolean(featuresValue, (Boolean)columnSettingEnum.getDefaultValue());
        } else if (VmTypeEnum.class.equals(columnSettingEnum.getClazz())) {
            return Optional.ofNullable(featuresValue).map(e -> VmTypeEnum.getById(Integer.valueOf(e)))
                .orElse((VmTypeEnum)columnSettingEnum.getDefaultValue());
        } else {
            return ExtendUtils.getString(featuresValue, (String)columnSettingEnum.getDefaultValue());
        }
    }

    public static VmTypeEnum getTemplate(String extend) {
        return (VmTypeEnum)getValue(extend, TableSettingEnum.GENERATE_TEMPLATE);
    }

    public static String setTemplate(String extend, Integer typeId) {
        if (null != typeId) {
            return ExtendUtils.setFeaturesValue(extend, TableSettingEnum.GENERATE_TEMPLATE.getName(), typeId);
        }
        return extend;
    }
}

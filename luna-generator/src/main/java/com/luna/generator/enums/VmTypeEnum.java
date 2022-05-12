package com.luna.generator.enums;

public enum VmTypeEnum {

    MYBATIS_PLUS(1, "mybatis-plus", "mybatis-plus/vm"),
    MYBATIS_PLUS_SWAGGER(2, "mybatis-plus-swagger", "mybatis-plus-swagger/vm")

    ;

    private Integer type;
    private String desc;

    /**
     * 路径变量
     */
    private String path;

    VmTypeEnum(Integer type, String desc, String path) {
        this.type = type;
        this.desc = desc;
        this.path = path;
    }

    VmTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getPath() {
        return path;
    }

    public static boolean isValid(Integer type) {
        for (VmTypeEnum typeEnum : values()) {
            if (typeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static VmTypeEnum getById(Integer type) {
        for (VmTypeEnum typeEnum : values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return MYBATIS_PLUS;
    }
}

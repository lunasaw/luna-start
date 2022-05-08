package com.luna.generator.domain;

import lombok.Data;

/**
 * @author luna
 * 2022/4/30
 */
@Data
public class VmTypeVO {

    /**
     * Id
     */
    private Integer id;

    /**
     * vm名称
     */
    private String type;

    public VmTypeVO(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
}

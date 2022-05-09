package com.luna.generator.domain;

import com.luna.generator.config.GenConfig;

/**
 * @author luna
 * 2022/5/9
 */
public enum GenPathEnum {

    /**
     * VUE 部署路径
     */
    VUE {
        @Override
        public String getGenPath() {
            return GenConfig.getVuePath();
        }
    },

    /**
     * JAVA 部署路径
     */
    JAVA {

        @Override
        public String getGenPath() {
            return GenConfig.getJavaPath();
        }
    };

    public abstract String getGenPath();
}

package com.luna.web.domain.req;

import lombok.Data;

/**
 * @author luna
 * 2022/5/22
 */
@Data
public class OssSignReq {

    private String fileName;

    private Long   expireTime;
}

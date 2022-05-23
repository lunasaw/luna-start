package com.luna;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.PutObjectResult;
import com.luna.ali.config.AliOssConfigProperties;
import com.luna.ali.oss.AliOssUploadApi;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luna
 * 2022/5/20
 */
public class OssAliTest extends AppTest {

    @Autowired
    private AliOssUploadApi        aliOssUploadApi;

    @Autowired
    private AliOssConfigProperties aliOssConfigProperties;

    @Test
    public void atest() {

        PutObjectResult putObjectResult = aliOssUploadApi.uploadFile("/Users/luna/Pictures/cat_2.jpg", aliOssConfigProperties.getBucketName(), "");
        System.out.println(JSON.toJSONString(putObjectResult));
    }
}

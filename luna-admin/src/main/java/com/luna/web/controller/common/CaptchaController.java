package com.luna.web.controller.common;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.common.constant.Constants;
import com.luna.common.core.domain.AjaxResult;
import com.luna.common.core.redis.RedisCache;
import com.luna.common.utils.uuid.IdUtils;

/**
 * 验证码操作处理
 *
 * @author luna
 */
@RestController
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;

    // 验证码类型
    @Value("${luna.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode() throws IOException {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String code = null;

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        // 生成验证码
        if ("math".equals(captchaType)) {
            ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36, 2);
            code = captcha.text();
            ajax.put("img", captcha.toBase64());
        } else if ("char".equals(captchaType)) {
            SpecCaptcha captcha = new SpecCaptcha(111, 36, 4);
            code = captcha.text();
            ajax.put("img", captcha.toBase64());
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        return ajax;
    }
}

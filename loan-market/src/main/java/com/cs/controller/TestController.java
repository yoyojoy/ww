package com.cs.controller;

import com.cs.common.GlobalCode;
import com.cs.common.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * test
 */
@RestController
@RequestMapping("test")
public class TestController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    /**
     * 设置交易密码
     *
     * @param request tradePwd 交易密码
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Object cfgTradePwd(HttpServletRequest request) {
        JSONResult result = new JSONResult(GlobalCode.SUCCESS);
        return result.setData("这是个测试");
    }
}

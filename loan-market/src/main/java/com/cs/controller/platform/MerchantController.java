package com.cs.controller.platform;

import com.cs.common.JSONResult;
import com.cs.controller.BaseController;
import com.cs.model.platform.Merchant;
import com.cs.service.platform.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by joy on 2017/11/24.
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

    @Autowired
    private IMerchantService merchantService;

    /**
     * 新增
     * @param merchant
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Object add(Merchant merchant) {
        JSONResult jsonResult = new JSONResult();
        boolean f = merchantService.insert(merchant);
        return jsonResult.setMsg("添加成功！");
    }

    /**
     * 查所有
     * @return
     */
    @PostMapping("/queryAll")
    @ResponseBody
    public Object queryAll() {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.setData(merchantService.queryAll());
    }
}

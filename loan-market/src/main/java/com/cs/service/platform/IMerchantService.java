package com.cs.service.platform;

import com.baomidou.mybatisplus.service.IService;
import com.cs.model.platform.Merchant;

import java.util.List;

/**
 * 商户
 */
public interface IMerchantService extends IService<Merchant> {

    public List<Merchant> queryAll();

}

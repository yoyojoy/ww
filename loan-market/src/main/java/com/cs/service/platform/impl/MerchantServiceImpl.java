package com.cs.service.platform.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cs.mapper.platform.MerchantMapper;
import com.cs.model.platform.Merchant;
import com.cs.service.platform.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by joy on 2017/11/24.
 */
@Service("merchantService")
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public List<Merchant> queryAll() {
        return merchantMapper.selectAll();
    }
}

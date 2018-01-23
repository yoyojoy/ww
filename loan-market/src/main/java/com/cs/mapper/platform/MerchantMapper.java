package com.cs.mapper.platform;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cs.model.platform.Merchant;

import java.util.List;

public interface MerchantMapper extends BaseMapper<Merchant> {

    List<Merchant> selectAll();
}

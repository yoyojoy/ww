package com.cs.model.platform;

import com.baomidou.mybatisplus.annotations.TableName;
import com.cs.model.BaseEntity;

/**
 * Created by joy on 2017/11/23.
 */
@TableName("cs_merchant")
public class Merchant extends BaseEntity {

    private String phone;
    private String name;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

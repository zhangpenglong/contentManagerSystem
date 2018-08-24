package com.yxb.cms.modules.system.domain.vo;

import java.util.Date;

/**
 * Created by dongao on 2018/5/18.
 */
public class Balance {

    private Long id;
    private Double balance;
    private Date creat_date;

    public Date getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(Date creat_date) {
        this.creat_date = creat_date;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

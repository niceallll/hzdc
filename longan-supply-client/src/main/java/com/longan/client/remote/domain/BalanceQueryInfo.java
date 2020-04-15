package com.longan.client.remote.domain;

import java.io.Serializable;

public class BalanceQueryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String balance;
    private String msg;

    public String getBalance() {
	return balance;
    }

    public void setBalance(String balance) {
	this.balance = balance;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public String toString() {
	if (balance != null) {
	    return "账户余额： " + balance;
	} else {
	    return msg;
	}
    }
}

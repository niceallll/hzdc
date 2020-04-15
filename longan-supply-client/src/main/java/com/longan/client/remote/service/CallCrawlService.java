package com.longan.client.remote.service;

import java.util.List;

import com.longan.biz.domain.Result;
import com.longan.client.remote.domain.CrawlLogin;

public interface CallCrawlService {
    public Result<CrawlLogin> getRand(Integer area, Integer pos);

    public Result<Boolean> getMessage(Integer area, CrawlLogin verify);

    public Result<String> getBalance(Integer area, Integer pos);

    public Result<Boolean> login(Integer area, CrawlLogin login);

    public Result<Boolean> logout(Integer area);

    public Result<List<String>> getStatus(Integer area);
}

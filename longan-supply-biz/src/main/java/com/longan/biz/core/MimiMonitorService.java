package com.longan.biz.core;

import java.util.List;

import com.longan.biz.domain.MiniInfo;

public interface MimiMonitorService {
    public void heartbeat(String pcId, String posId);

    public void countByPosId(String posId, Long amount);

    public boolean checkLimit(String posId);

    public List<MiniInfo> queryAllMini();

    public List<MiniInfo> queryAliveMini();
}

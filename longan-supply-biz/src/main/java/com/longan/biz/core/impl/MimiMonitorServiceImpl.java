package com.longan.biz.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

//import com.google.common.cache.CacheBuilder;
//import com.google.common.collect.MapMaker;
import com.longan.biz.core.MimiMonitorService;
import com.longan.biz.domain.MiniInfo;

public class MimiMonitorServiceImpl implements MimiMonitorService {
    // public final static ConcurrentMap<String, MiniInfo> map = new MapMaker().expiration(1, TimeUnit.DAYS).concurrencyLevel(16)
    // .initialCapacity(100).makeMap();

    @Override
    public void heartbeat(String pcId, String posId) {
	// MiniInfo miniInfo = map.get(posId);
	// if (miniInfo == null) {
	// miniInfo = new MiniInfo();
	// miniInfo.setPcId(pcId);
	// miniInfo.setPosId(posId);
	// miniInfo.updateLastHeartBeat();
	// map.put(posId, miniInfo);
	// return;
	// }
	// miniInfo.setPcId(pcId);
	// miniInfo.setPosId(posId);
	// miniInfo.updateLastHeartBeat();
    }

    @Override
    public void countByPosId(String posId, Long amount) {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean checkLimit(String posId) {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public List<MiniInfo> queryAllMini() {
	List<MiniInfo> result = new ArrayList<MiniInfo>();
	// for (Entry<String, MiniInfo> entry : map.entrySet()) {
	// result.add(entry.getValue());
	// }
	// sort(result);
	return result;
    }

    @Override
    public List<MiniInfo> queryAliveMini() {
	List<MiniInfo> result = new ArrayList<MiniInfo>();
	// for (Entry<String, MiniInfo> entry : map.entrySet()) {
	// if (entry.getValue().isAlive()) {
	// result.add(entry.getValue());
	// }
	// }
	// sort(result);
	return result;
    }

    private final Comparator<MiniInfo> aliveComparator = new Comparator<MiniInfo>() {
	@Override
	public int compare(MiniInfo o1, MiniInfo o2) {
	    int compareName = o1.getPcId().compareTo(o2.getPcId());
	    if (o1.isAlive() && !o2.isAlive()) {
		return -1;
	    } else if (!o1.isAlive() && o2.isAlive()) {
		return 1;
	    }
	    return compareName;
	}
    };

    private void sort(List<MiniInfo> list) {
	Collections.sort(list, aliveComparator);
    }

}

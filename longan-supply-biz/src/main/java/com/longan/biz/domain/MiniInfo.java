package com.longan.biz.domain;

public class MiniInfo {
	private String pcId;
	private String posId;
	private Long lastHeartBeat;
	private boolean isAlive;

	private static final long TIMEOUT = 60 * 2 * 1000;

	public Long getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(Long lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public String getPcId() {
		return pcId;
	}

	public void setPcId(String pcId) {
		this.pcId = pcId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public void updateLastHeartBeat() {
		setLastHeartBeat(System.currentTimeMillis());
		setAlive(true);
	}

	public void checkAlive() {
		if (System.currentTimeMillis() - lastHeartBeat > TIMEOUT) {
			setAlive(false);
		}
	}
}

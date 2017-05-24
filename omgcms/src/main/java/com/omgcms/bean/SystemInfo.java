package com.omgcms.bean;

public class SystemInfo {

	private String osName;
	private String runtimeEnv;
	private String javaVm;
	private double totalMemory;
	private double maxMemory;
	private double freeMemory;
	private String serverInfo;

	public SystemInfo() {
		super();
	}
	
	public SystemInfo(String osName, String runtimeEnv, String javaVm, double totalMemory, double maxMemory, double freeMemory,
			String serverInfo) {
		super();
		this.osName = osName;
		this.runtimeEnv = runtimeEnv;
		this.javaVm = javaVm;
		this.totalMemory = totalMemory;
		this.maxMemory = maxMemory;
		this.freeMemory = freeMemory;
		this.serverInfo = serverInfo;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getRuntimeEnv() {
		return runtimeEnv;
	}

	public void setRuntimeEnv(String runtimeEnv) {
		this.runtimeEnv = runtimeEnv;
	}

	public String getJavaVm() {
		return javaVm;
	}

	public void setJavaVm(String javaVm) {
		this.javaVm = javaVm;
	}

	public double getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(double totalMemory) {
		this.totalMemory = totalMemory;
	}

	public double getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(double maxMemory) {
		this.maxMemory = maxMemory;
	}

	public double getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(double freeMemory) {
		this.freeMemory = freeMemory;
	}

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	@Override
	public String toString() {
		return "SystemInfo [osName=" + osName + ", runtimeEnv=" + runtimeEnv + ", javaVm=" + javaVm + ", totalMemory=" + totalMemory
				+ ", maxMemory=" + maxMemory + ", freeMemory=" + freeMemory + ", serverInfo=" + serverInfo + "]";
	}

}

package com.example.dkdk6.toktokplay.Activity;

public class TCPConfig {
	private String ip;
	private int port;
	private String dbDirectory;
	
	//Force to assign essential values to TCPConfig Class.
	//Do not make basic constructor.
	public TCPConfig(String ip, int port, String dbDirectory){
		this.setIp(ip);
		this.setPort(port);
		this.setDbDirectory(dbDirectory);
	}

	//Basic Getter and Setter.
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getDbDirectory() {
		return dbDirectory;
	}
	public void setDbDirectory(String dbDirectory) {
		this.dbDirectory = dbDirectory;
	}
}

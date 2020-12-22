package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PingServer {

	@SerializedName("ok")
	@Expose
	private Boolean ok;
	@SerializedName("ping")
	@Expose
	private String ping;
	@SerializedName("elapsed")
	@Expose
	private String elapsed;

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getPing() {
		return ping;
	}

	public void setPing(String ping) {
		this.ping = ping;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

}
package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetPositionForArea {

	@SerializedName("ok")
	@Expose
	private Boolean ok;
	@SerializedName("elapsed")
	@Expose
	private String elapsed;
	@SerializedName("err")
	@Expose
	private String err;

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

}
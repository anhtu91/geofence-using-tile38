package org.ict.geofencing.generated;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetKey {

	@SerializedName("ok")
	@Expose
	private Boolean ok;
	@SerializedName("keys")
	@Expose
	private List<String> keys = null;
	@SerializedName("elapsed")
	@Expose
	private String elapsed;

	
	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

}
package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenameKey {
	
	@SerializedName("ok")
	@Expose
	private Boolean ok;
	@SerializedName("err")
	@Expose
	private String err;
	@SerializedName("elapsed")
	@Expose
	private String elapsed;
	
	
	public Boolean getOk() {
		return ok;
	}
	public String getErr() {
		return err;
	}
	public String getElapsed() {
		return elapsed;
	}
	
}

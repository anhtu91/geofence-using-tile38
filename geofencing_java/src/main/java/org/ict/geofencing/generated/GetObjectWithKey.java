package org.ict.geofencing.generated;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetObjectWithKey {

	@SerializedName("ok")
	@Expose
	private Boolean ok;
	@SerializedName("objects")
	@Expose
	private List<GeoObjects> objects = null;
	@SerializedName("count")
	@Expose
	private Integer count;
	@SerializedName("cursor")
	@Expose
	private Integer cursor;
	@SerializedName("elapsed")
	@Expose
	private String elapsed;

	
	@Override
	public String toString() {
		return "GetObjectWithKey [ok=" + ok + ", objects=" + objects + ", count=" + count + ", cursor=" + cursor
				+ ", elapsed=" + elapsed + "]";
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public List<GeoObjects> getObjects() {
		return objects;
	}

	public void setObjects(List<GeoObjects> objects) {
		this.objects = objects;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCursor() {
		return cursor;
	}

	public void setCursor(Integer cursor) {
		this.cursor = cursor;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

}
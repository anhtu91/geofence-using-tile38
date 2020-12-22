package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoObjects {

	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("object")
	@Expose
	private GeoObject object;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GeoObject getObject() {
		if(object == null) {
			return null;
		}
		return object;
	}

	public void setObject(GeoObject object) {
		this.object = object;
	}
}
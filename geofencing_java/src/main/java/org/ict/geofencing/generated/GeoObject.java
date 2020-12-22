package org.ict.geofencing.generated;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GeoObject {

	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("coordinates")
	@Expose
	private JsonElement coordinates = null;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JsonElement getCoordinates() {
		return coordinates;
	}

}

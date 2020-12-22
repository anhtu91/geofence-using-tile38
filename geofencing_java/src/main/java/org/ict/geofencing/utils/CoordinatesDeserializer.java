package org.ict.geofencing.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CoordinatesDeserializer  implements JsonDeserializer<JsonElement> {

	  @Override
	  public JsonElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
	      throws JsonParseException {
	    JsonObject obj = new JsonObject();
	    obj.add("coordinates", json);
	    System.out.println(obj);
	    return obj;
	  }
}

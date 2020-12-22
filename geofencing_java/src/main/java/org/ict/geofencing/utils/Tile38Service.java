package org.ict.geofencing.utils;

import org.ict.geofencing.generated.Area;
import org.ict.geofencing.generated.DelOneObject;
import org.ict.geofencing.generated.DropAllObject;
import org.ict.geofencing.generated.GetKey;
import org.ict.geofencing.generated.PingServer;
//import org.ict.geofencing.generated.Point;
import org.ict.geofencing.generated.RenameKey;
import org.ict.geofencing.generated.Server;
import org.ict.geofencing.generated.SetPositionForArea;
import org.ict.geofencing.generated.SetPositionForPoint;
import org.ict.geofencing.generated.SetPositionForRectangle;
import org.ict.geofencing.generated.SetReadMode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Tile38Service {

	/** Case 1: Set position for a polygon **/
	@POST("SET {keyID} {fieldName} OBJECT {\"type\":\"Polygon\",\"coordinates\":[[{longitudeLatitude}]]}")
	Call<SetPositionForArea> defineNewArea(@Path("keyID") String keyID, @Path("fieldName") String name,
			@Path("longitudeLatitude") String longitudeLatitude);

	/** Case 2: Set position for rectangle **/
	@POST("SET {keyID} {fieldName} BOUNDS {southwestLatitude} {southwestLongitude} {northeastLatitude} {northeastLongitude}")
	Call<SetPositionForRectangle> defineNewRectangle(@Path("keyID") String keyID, @Path("fieldName") String name,
			@Path("southwestLatitude") double southwestLatitude, @Path("southwestLongitude") double southwestLongitude,
			@Path("northeastLatitude") double northeastLatitude, @Path("northeastLongitude") double northeastLongitude);

	/** Case 3: Set position for a point **/
	@POST("SET {keyID} {fieldName} POINT {latitude} {longitude}")
	Call<SetPositionForPoint> defineNewPoint(@Path("keyID") String keyID, @Path("fieldName") String name,
			@Path("latitude") double latitude, @Path("longitude") double longitude);

	/** Case 4: Get all defined objects **/
	@GET("KEYS *")
	Call<GetKey> getAllKey();

	/** Case 5: Check if object exists in area by passing parameter **/
	@GET("TEST POINT {latitude} {longitude} WITHIN GET {keyID} {fieldName}")
	Call<Area> objectExistInAreaByPassingParameter(@Path("latitude") double latitude,
			@Path("longitude") double longitude, @Path("keyID") String keyID, @Path("fieldName") String fieldName);

	/** Case 6: Remove all objects using same keyID **/
	@POST("DROP {keyID}")
	Call<DropAllObject> removeAllObjectFromKeyID(@Path("keyID") String keyID);

	/** Case 7: Remove a specified object **/
	@POST("DEL {keyID} {fieldName}")
	Call<DelOneObject> delOneObjectFromKeyidFieldName(@Path("keyID") String keyID, @Path("fieldName") String fieldName);

	/** Case 8: Rename keyID **/
	@POST("RENAME {oldKey} {newKey}")
	Call<RenameKey> renameKey(@Path("oldKey") String oldKey, @Path("newKey") String newKey);

	/** Case 9: Set read mode **/
	@POST("READONLY {read_mode}")
	Call<SetReadMode> setReadMode(@Path("read_mode") String read_mode);

	/** Case 10: Ping to server **/
	@GET("PING")
	Call<PingServer> pingToServer();

	/** Case 11: Check server status **/
	@GET("SERVER")
	Call<Server> checkServerStatus();

	public static Retrofit createRetrofit(String baseUri) {

		return new Retrofit.Builder().baseUrl(baseUri)
				.client(new OkHttpClient.Builder()
						.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
						.build())
				.addConverterFactory(GsonConverterFactory.create()).build();
	}

}

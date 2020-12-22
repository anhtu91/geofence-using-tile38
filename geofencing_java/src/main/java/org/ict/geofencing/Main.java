package org.ict.geofencing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ict.geofencing.generated.Area;
import org.ict.geofencing.generated.DelOneObject;
import org.ict.geofencing.generated.DropAllObject;
import org.ict.geofencing.generated.GeoObjects;
import org.ict.geofencing.generated.GetKey;
import org.ict.geofencing.generated.GetObjectWithKey;
import org.ict.geofencing.generated.PingServer;
//import org.ict.geofencing.generated.Point;
import org.ict.geofencing.generated.RenameKey;
import org.ict.geofencing.generated.Server;
import org.ict.geofencing.generated.SetPositionForArea;
import org.ict.geofencing.generated.SetPositionForPoint;
import org.ict.geofencing.generated.SetPositionForRectangle;
import org.ict.geofencing.generated.SetReadMode;
import org.ict.geofencing.utils.Tile38Service;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Response;

/**
 ** 
 * @Author Anh Tu Nguyen/ IuK FH Dortmund
 * @Version 12-11-2019
 * @Projektthema: Aufbau eines Geofence Systems für das Cyberphysical system
 * @UseCase: Parkplatzbelegung
 * @UseCase: Zutrittskontrolle (QR-Code erzeugen, Tür öffnen)
 * @UseCase: E-Auto laden (optional)
 * @GeofencingServer (location-based applications): {@link https://tile38.com/}
 */

public class Main {

	private static final Logger logger = LogManager.getFormatterLogger(Main.class);
	private static Scanner sc = new Scanner(System.in);

	private static Tile38Service service = null;

	private static final String URL = "http://localhost:9851"; // "http://test-server.ikt.fh-dortmund.de:9851"
	private static final String SCAN = "/SCAN%20";

	private static Multimap<String, String> allDefinedObjectsPointsMap = ArrayListMultimap.create();

	/** Use for case 1: Set position for polygon **/
	private static Call<SetPositionForArea> callSetPositionForAreaByPassingParameter = null;
	private static Response<SetPositionForArea> responseSetPositionForAreaByPassingParameter = null;

	/** Use for case 2: Set position for rectangle **/
	private static Call<SetPositionForRectangle> callSetPositionForRectangleByPassingParameter = null;
	private static Response<SetPositionForRectangle> responseSetPositionForRectangleByPassingParameter = null;

	/** Use for case 3: Set position for point **/
	private static Call<SetPositionForPoint> callSetPositionForPointByPassingParameter = null;
	private static Response<SetPositionForPoint> responseSetPositionForPointByPassingParameter = null;

	/** Use for case 4: Get all defined objects **/
	private static Call<GetKey> callGetAllKey = null;
	private static Response<GetKey> responseGetAllKey = null;

	/** Use for case 5: Find out if object in area **/
	private static Call<Area> callObjectExistInAreaByPassingParameter = null;
	private static Response<Area> responseObjectExistInAreaPassingParameter = null;

	/** Use for case 6: Remove all objects using same keyID **/
	private static Call<DropAllObject> callRemoveAllObjectFromKeyID = null;
	private static Response<DropAllObject> responseRemoveObjectsFromKeyID = null;

	/** Use for case 7: Remove a specified object **/
	private static Call<DelOneObject> callDelOneObjectFromKeyidFieldName = null;
	private static Response<DelOneObject> responseDelOneObjectFromKeyidFieldName = null;

	/** Use for case 8: Rename a keyID **/
	private static Call<RenameKey> callRenameKeyid = null;
	private static Response<RenameKey> responseRenameKey = null;

	/** Use for case 9: Set server to read only mode **/
	private static Call<SetReadMode> callSetReadMode = null;
	private static Response<SetReadMode> responseReadMode = null;

	/** Use for case 10: Ping to server **/
	private static Call<PingServer> callPingToServer = null;
	private static Response<PingServer> responsePingServer = null;

	/** Use for case 11: Get server information **/
	private static Call<Server> callServerStatus = null;
	private static Response<Server> responseServerStatus = null;

	private static void displayMenu() {
		logger.info("Tile38 Menu: ");
		logger.info("1. Set position for polygon ");
		logger.info("2. Set position for minimum bounding rectangle");
		logger.info("3. Set position for point ");
		logger.info("4. Get all defined objects ");
		logger.info("5. Find out if object in area ");
		logger.info("6. Remove all objects using same keyID ");
		logger.info("7. Remove a specified object ");
		logger.info("8. Rename a keyID ");
		logger.info("9. Set server to read only mode ");
		logger.info("10. Ping to server ");
		logger.info("11. Get server information ");
	}

	/*
	 * Check if object already existed
	 * 
	 * @param key: KeyID of object
	 * 
	 * @param fieldName: FieldName of object
	 * 
	 * @return true if key, id exist in cache
	 * 
	 */
	private static boolean checkKeyidExistInDatabase(String key, String fieldName) {
		for (Map.Entry<String, String> entry : allDefinedObjectsPointsMap.entries()) {
			if (entry.getKey().equals(key)) {
				if (entry.getValue().equals(fieldName))
					return true;
			}
		}
		return false;
	}

	/*
	 * For case 1: Display response for case 1
	 * 
	 * @param keyID: KeyID of object
	 * 
	 * @param fielName: FieldName of object
	 * 
	 * @return no return
	 */

	private static void setPositionForAreaDisplayResponse(String keyID, String fieldName,
			Call<SetPositionForArea> callSetPositionForAreaByPassingParameter) {
		try {
			responseSetPositionForAreaByPassingParameter = callSetPositionForAreaByPassingParameter.execute();
		} catch (IOException e) {
			logger.info("Error while get response " + e.getMessage());
		}

		if (responseSetPositionForAreaByPassingParameter.body().getOk()) {
			logger.info("Successful set position for area %s %s", keyID, fieldName);
		} else {
			logger.info("Failed to set position for area %s %s. Error: %s", keyID, fieldName,
					responseSetPositionForAreaByPassingParameter.body().getErr());
		}
	}

	/*
	 * For case 1: Enter Longitude Latitude for set position for area
	 * 
	 * @param keyID: KeyID of object
	 * 
	 * @param fieldName: FieldName of object
	 * 
	 * @param num_corner: Number corner of object
	 * 
	 * @return no return
	 *
	 */
	private static void setPositionForAreaEnterLongitudeLatitude(String keyID, String fieldName, int numCorner) {
		StringBuilder strBuilderLongitudeLatitude = new StringBuilder();
		double longitude = 0;
		double latitude = 0;
		double firstLongitude = 0;
		double firstLatitude = 0;
		String strLongitudeLatitude = null;

		// Get longitude, latitude of corners
		for (int i = 0; i < numCorner; i++) {
			logger.info("Enter latitude of %d corner: ", i + 1);
			latitude = Double.parseDouble(sc.nextLine());
			logger.info("Enter longitude of %d corner: ", i + 1);
			longitude = Double.parseDouble(sc.nextLine());
			if (i != numCorner - 1) {
				strLongitudeLatitude = "[" + Double.toString(longitude) + "," + Double.toString(latitude) + "],";
				if (i == 0) {
					firstLatitude = latitude;
					firstLongitude = longitude;
				}
			}
			strBuilderLongitudeLatitude.append(strLongitudeLatitude);
		}

		// first and last longitude/latitude must be same!
		strLongitudeLatitude = "[" + Double.toString(firstLongitude) + "," + Double.toString(firstLatitude) + "]";
		strBuilderLongitudeLatitude.append(strLongitudeLatitude);

		// Set position for area
		callSetPositionForAreaByPassingParameter = service.defineNewArea(keyID, fieldName,
				strBuilderLongitudeLatitude.toString());
		// Get response
		setPositionForAreaDisplayResponse(keyID, fieldName, callSetPositionForAreaByPassingParameter);
	}

	/*
	 * For case 1: Count number of corner from input string
	 * 
	 * @param: string coordinates
	 * 
	 * @return: number of corners
	 * 
	 */
	private static int countCorners(String strCoordinates) {
		int counterComma = 0;
		
		for (int i = 0; i < strCoordinates.length(); i++) {
			if (strCoordinates.charAt(i) == ',') {
				counterComma++;
			}
		}
		return (int) (counterComma / 2);
	}

	/*
	 * For case 1: Enter number corner for set position for area
	 * 
	 * @param keyID: KeyID of object
	 * 
	 * @param fieldName: FieldName of object
	 * 
	 * @param IsOverwrite: true if overwrite object, false for not
	 * 
	 * @return no return
	 *
	 */
	private static void setPositionForAreaGetNumberCorner(String keyID, String fieldName, boolean IsOverwritte) {
		int numCorner = 0;

		if (!IsOverwritte) {
			while (true) {
				logger.info("How many corner in area: ");
				numCorner = Integer.parseInt(sc.nextLine());

				if (numCorner >= 3)
					break;
				else
					logger.info("Number corner muss >= 3");
			}
			setPositionForAreaEnterLongitudeLatitude(keyID, fieldName, numCorner);
		} else {
			try {
				// Get JSON Object from URL
				JSONObject json = readJsonFromUrl(URL + SCAN + keyID);
				String strJson = json.toString();
				Gson gson = new GsonBuilder()
						.registerTypeAdapter(JsonElement.class, new org.ict.geofencing.utils.CoordinatesDeserializer())
						.create();
				GetObjectWithKey geoObj = gson.fromJson(strJson, GetObjectWithKey.class);

				// List of all geoobject with keyID
				List<GeoObjects> listGeoObjects = geoObj.getObjects();
				boolean isObjectPolygon = true;

				for (int i = 0; i < listGeoObjects.size(); i++) {
					if (geoObj.getObjects().get(i).getId().equals(fieldName)) {

						// Check if object is point or area?
						if (geoObj.getObjects().get(i).getObject().getType().equals("Point")) {
							isObjectPolygon = false;
						} else if (geoObj.getObjects().get(i).getObject().getType().equals("Polygon")) {
							// Check how many corner has area?
							String strCoordinates = geoObj.getObjects().get(i).getObject().getCoordinates().toString();
							numCorner = countCorners(strCoordinates);// strCoordinates.length() -
																		// strCoordinates.replace("[", "").length() - 2;
							logger.info("Defined object has " + numCorner + " corner.");
							setPositionForAreaEnterLongitudeLatitude(keyID, fieldName, numCorner);
						}
					}
				}

				if (!isObjectPolygon)
					logger.info("Cannot overwrite a point for a area.");
			} catch (Exception e) {
				logger.info("Error " + e.getMessage());
			}
		}
	}

	/*
	 * For case 1: Set position for polygon (>= 3 corners)
	 * 
	 * @param
	 * 
	 * @return no return
	 *
	 */
	private static void setPositionForPolygon() {
		String keyID = null;
		String fieldName = null;
		String overwritten = null;

		try {
			logger.info("Set position for a area...");
			logger.info("Enter keyID: ");
			keyID = sc.nextLine();

			if (keyID.isBlank()) {
				logger.info("Input keyID was empty");
			} else {
				logger.info("Enter field name: ");
				fieldName = sc.nextLine();
				if (fieldName.isBlank()) {
					logger.info("Input fieldName was empty");
				} else {
					// Check if keyID exist in database
					if (checkKeyidExistInDatabase(keyID, fieldName)) {
						logger.info("Key: " + keyID + " and field name: " + fieldName
								+ " already exist in database. Want to overwrite? T/t for true, others is false");
						overwritten = sc.nextLine();

						if (overwritten.toLowerCase().equals("t"))
							setPositionForAreaGetNumberCorner(keyID, fieldName, true);

					} else
						setPositionForAreaGetNumberCorner(keyID, fieldName, false);
				}
			}

		} catch (Throwable T) {
			if (T instanceof NumberFormatException) {
				logger.info("Latitude ,Longitude muss be double. Number of corner muss be integer.");
			} else {
				logger.info("Wrong input " + T.getMessage());
			}
		}
	}

	/*
	 * For case 2: Enter latitude, longitude
	 * 
	 * @param keyID: KeyID of object
	 * 
	 * @param fieldName: FieldName of object
	 * 
	 * @return no return
	 *
	 */
	private static void setPositionForRectangleInputLatitudeLongitude(String keyID, String fieldName) {
		double southwestLatitude = 0;
		double southwestLongitude = 0;
		double northeastLatitude = 0;
		double northeastLongitude = 0;

		try {
			logger.info("Enter southwest latitude: ");
			southwestLatitude = Double.parseDouble(sc.nextLine());
			logger.info("Enter southwest longitude: ");
			southwestLongitude = Double.parseDouble(sc.nextLine());
			logger.info("Enter northeast latitude: ");
			northeastLatitude = Double.parseDouble(sc.nextLine());
			logger.info("Enter northeast longitude: ");
			northeastLongitude = Double.parseDouble(sc.nextLine());

			// Set position
			callSetPositionForRectangleByPassingParameter = service.defineNewRectangle(keyID, fieldName,
					southwestLatitude, southwestLongitude, northeastLatitude, northeastLongitude);
			// Get response
			responseSetPositionForRectangleByPassingParameter = callSetPositionForRectangleByPassingParameter.execute();

			if (responseSetPositionForRectangleByPassingParameter.body().getOk()) {
				logger.info("Successful set position for Rectangle %s %s", keyID, fieldName);
			} else {
				logger.info("Failed to set position for Rectangle %s %s. Error: %s", keyID, fieldName,
						responseSetPositionForRectangleByPassingParameter.body().getErr());
			}
		} catch (Exception e) {
			logger.info("Error " + e.getMessage());
		}
	}

	/*
	 * For case 2: Set position for rectangle
	 * 
	 * @param
	 * 
	 * @return no return
	 */
	private static void setPositionForMinimumBoundingRectangle() {
		String keyID = null;
		String fieldName = null;
		String overwritten = null;

		try {
			logger.info("Set position for a area...");
			logger.info("Enter keyID: ");
			keyID = sc.nextLine();

			if (keyID.isBlank()) {
				logger.info("Input keyID was empty");
			} else {
				logger.info("Enter field name: ");
				fieldName = sc.nextLine();
				if (fieldName.isBlank()) {
					logger.info("Input fieldName was empty");
				} else {
					// Check if keyID exist in database
					if (checkKeyidExistInDatabase(keyID, fieldName)) {
						logger.info("Key: " + keyID + " and field name: " + fieldName
								+ " already exist in database. Want to overwrite? T/t for true, others is false");
						overwritten = sc.nextLine();

						if (overwritten.toLowerCase().equals("t")) {
							setPositionForRectangleInputLatitudeLongitude(keyID, fieldName);
						}

					} else {
						setPositionForRectangleInputLatitudeLongitude(keyID, fieldName);
					}
				}
			}

		} catch (Throwable T) {
			if (T instanceof NumberFormatException) {
				logger.info("Latitude ,Longitude muss be double. Number of corner muss be integer.");
			} else {
				logger.info("Wrong input " + T.getMessage());
			}
		}
	}

	/*
	 * For case 3: Enter Longitutde Latitude for set position for point
	 * 
	 * @param keyID: KeyID of object
	 * 
	 * @param fieldName: FieldName of object
	 * 
	 * @return no return
	 * 
	 */
	private static void setPositionForPointEnterLongitudeLatitude(String keyID, String fieldName) {
		double latitude = 0;
		double longitude = 0;

		try {
			logger.info("Enter latitude: ");
			latitude = Double.parseDouble(sc.nextLine());
			logger.info("Enter longitude: ");
			longitude = Double.parseDouble(sc.nextLine());

			// Set position
			callSetPositionForPointByPassingParameter = service.defineNewPoint(keyID, fieldName, latitude, longitude);
			// Get response
			responseSetPositionForPointByPassingParameter = callSetPositionForPointByPassingParameter.execute();

			if (responseSetPositionForPointByPassingParameter.body().getOk()) {
				logger.info("Successful set position for point %s %s", keyID, fieldName);
			} else {
				logger.info("Failed to set position for point %s %s. Error: %s", keyID, fieldName,
						responseSetPositionForPointByPassingParameter.body().getErr());
			}
		} catch (Exception e) {
			logger.info("Error " + e.getMessage());
		}
	}

	/*
	 * For case 3: Set position for point
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void setPositionForPoint() {
		String keyID = null;
		String fieldName = null;
		String overwritten = null;

		try {
			logger.info("Set position for a point...");
			logger.info("Enter keyID: ");
			keyID = sc.nextLine();

			if (keyID.isBlank()) {
				logger.info("Input keyID was empty");
			} else {
				logger.info("Enter field name: ");
				fieldName = sc.nextLine();
				if (fieldName.isBlank()) {
					logger.info("Input fieldName was empty");
				} else {
					if (checkKeyidExistInDatabase(keyID, fieldName)) {
						logger.info("Key: " + keyID + " and field name: " + fieldName
								+ " already exist in database. Want to overwrite? Y/y for yes, others for no");
						overwritten = sc.nextLine();

						if (overwritten.toLowerCase().equals("t"))
							setPositionForPointEnterLongitudeLatitude(keyID, fieldName);
					} else
						setPositionForPointEnterLongitudeLatitude(keyID, fieldName);
				}
			}

		} catch (Throwable T) {
			if (T instanceof NumberFormatException) {
				logger.info("Latitude ,Longitude muss be double.");
			} else {
				logger.info("Wrong input " + T.getMessage());
			}
		}
	}

	/*
	 * For case 4: Return string from response of URL
	 * 
	 * @param rd: Reader from input stream
	 * 
	 * @return String of JSON data
	 * 
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/*
	 * For case 4: Return JSON Data from URL
	 * 
	 * @param url: URL path which returns JSON data
	 * 
	 * @return JSONObject from input URL
	 * 
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		JSONObject json = null;
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONObject(jsonText);
		} catch (Exception e) {
			logger.info("Error when get JSON data from URL " + e.getMessage());
		} finally {
			is.close();
		}
		return json;
	}

	/*
	 * For case 4: Count number of comma to classify GeoObject
	 * 
	 * @param inputJson: coordinates of GeoObject
	 * 
	 * @param charNeedCheck: should be ',' in this case
	 * 
	 * @return number of comma in GeoObject => Classify point (1 comma), triangle(7
	 * commas), quadrangle(9 commas), pentagon(11 commas) and polygon(>11 commas)
	 * 
	 */
	private static int counterCharacter(JsonElement inputJson, char charNeedCheck) {
		int counter = 0;

		if (inputJson != null) {
			String strInput = inputJson.toString();

			for (int i = 0; i < strInput.length(); i++) {
				if (strInput.charAt(i) == charNeedCheck) {
					counter++;
				}
			}
		}

		return counter;
	}

	@SuppressWarnings("finally")
	private static GetObjectWithKey geoJsonObject(String key) {
		GetObjectWithKey geoObj = null;
		try {
			JSONObject json = readJsonFromUrl(URL + SCAN + key);
			String strJson = json.toString();
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(JsonElement.class, new org.ict.geofencing.utils.CoordinatesDeserializer())
					.create();
			geoObj = gson.fromJson(strJson, GetObjectWithKey.class);
		} catch (Exception e) {
			logger.info("Error " + e.getMessage());
		} finally {
			return geoObj;
		}
	}

	/*
	 * For case 4: Get all polygons and points in database
	 * 
	 * @param displayList: if true => print out all defined objects if false => save
	 * to map to check if object already exists
	 * 
	 * @return no return
	 * 
	 */
	private static void getAllObjectsPoints(boolean displayList) {
		List<String> listAllKey = null;

		if (displayList)
			logger.info("Get all defined objects...");

		callGetAllKey = service.getAllKey();
		try {
			responseGetAllKey = callGetAllKey.execute();
		} catch (Exception e) {
			logger.info("Error " + e.getMessage());
		}

		if (responseGetAllKey.body().getOk()) {
			listAllKey = responseGetAllKey.body().getKeys();
			int counterPoint = 0;
			int counterTriangle = 0;
			int counterQuadrangle = 0;
			int counterPentagon = 0;
			int counterPolygon = 0;
			int counterGeoObject = 0;

			for (String key : listAllKey) {
				GetObjectWithKey geoObj = geoJsonObject(key);
				if (geoObj != null) {
					List<GeoObjects> listGeoObjects = geoObj.getObjects();

					for (int i = 0; i < listGeoObjects.size(); i++) {

						if (displayList) {
							logger.info("Key: " + key);
							logger.info("ID: " + geoObj.getObjects().get(i).getId());

							if (counterCharacter(geoObj.getObjects().get(i).getObject().getCoordinates(), ',') == 1) {
								logger.info("Type: Point");
								counterPoint++;
							} else if (counterCharacter(geoObj.getObjects().get(i).getObject().getCoordinates(),
									',') == 7) {
								logger.info("Type: Trianle");
								counterTriangle++;
							} else if (counterCharacter(geoObj.getObjects().get(i).getObject().getCoordinates(),
									',') == 9) {
								logger.info("Type: Quadrangle");
								counterQuadrangle++;
							} else if (counterCharacter(geoObj.getObjects().get(i).getObject().getCoordinates(),
									',') == 11) {
								logger.info("Type: Pentagon");
								counterPentagon++;
							} else {
								logger.info("Type: Polygon with corners >= 5");
								counterPolygon++;
							}
							counterGeoObject++;

							logger.info("Coordinates (longitude/latitude): "
									+ geoObj.getObjects().get(i).getObject().getCoordinates());

							logger.info("--------------------------------");
						} else {
							allDefinedObjectsPointsMap.put(key, geoObj.getObjects().get(i).getId());
						}
					}
				} else {
					logger.info("GeoObject of key " + key + "is null");
				}
			}

			if (displayList) {
				logger.info(
						"Found " + (counterGeoObject) + " objects with " + listAllKey.size() + " keys in database.");
				logger.info(counterPoint + " are points.");
				logger.info(counterTriangle + " are triangles.");
				logger.info(counterQuadrangle + " are quadrangles.");
				logger.info(counterPentagon + " are pentagons.");
				logger.info(counterPolygon + " are polygons more than >= 5 cornes.");
			}

		} else {
			logger.info("Get all key unsuccessful.\n");
		}
	}

	/*
	 * For case 5: Save area where object found in map
	 * 
	 * @param: keyID of defined object in server
	 * 
	 * @param: fieldName of defined object in server
	 * 
	 * @param: responseObjectExistInAreaPassingParameter response from server
	 * 
	 * @return: hashmap with key = keyID, value = fieldName of object
	 */
	private static HashMap<String, String> areaWhereObjectFound(String keyID, String fieldName,
			Response<Area> responseObjectExistInAreaPassingParameter) {
		HashMap<String, String> keyIdFieldname = new HashMap<String, String>();
		if (responseObjectExistInAreaPassingParameter.body().getErro() == null) {
			// If area is defined in tile38
			if (responseObjectExistInAreaPassingParameter.body().getOk()
					&& responseObjectExistInAreaPassingParameter.body().getResult()) {
				keyIdFieldname.put(keyID, fieldName);
			}
		}
		return keyIdFieldname;
	}

	/*
	 * For case 5: find all area, where object belongs to
	 * 
	 * @param: input latitude of object
	 * 
	 * @param: input longitude of object
	 * 
	 * @return none
	 */
	private static void findAreaOfObject(double latitude, double longitude) {
		ArrayList<HashMap<String, String>> allAreaWhereObjectFound = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> keyIdFieldnameWhereObjectFound = new HashMap<String, String>();

		try {
			// Loop through multimap
			for (Map.Entry<String, String> entry : allDefinedObjectsPointsMap.entries()) {
				callObjectExistInAreaByPassingParameter = service.objectExistInAreaByPassingParameter(latitude,
						longitude, entry.getKey(), entry.getValue());
				responseObjectExistInAreaPassingParameter = callObjectExistInAreaByPassingParameter.execute();
				keyIdFieldnameWhereObjectFound = areaWhereObjectFound(entry.getKey(), entry.getValue(),
						responseObjectExistInAreaPassingParameter);
				if (!keyIdFieldnameWhereObjectFound.isEmpty())
					allAreaWhereObjectFound.add(keyIdFieldnameWhereObjectFound);
			}

			if (allAreaWhereObjectFound.size() > 0) {
				logger.info("Object was found in " + allAreaWhereObjectFound.size()
						+ " following defined area [keyID, fieldName].");
				for (HashMap<String, String> map : allAreaWhereObjectFound) {
					for (String key : map.keySet()) {
						logger.info("[" + key + "," + map.get(key) + "]");
					}
				}
			} else {
				logger.info("Object was not found in any defined area.");
			}
		} catch (Throwable T) {
			if (T instanceof NumberFormatException) {
				logger.info("Longitude, latitude muss be double. Number of area muss be integer.");
			} else {
				logger.info("Wrong input " + T.getMessage());
			}
		}
	}

	/*
	 * For case 5: Find out if object in area
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void checkObjectExistInArea() {
		double longitude = 0;
		double latitude = 0;

		try {
			logger.info("Check if object exists in area...");
			logger.info("Enter latitude: ");
			latitude = Double.parseDouble(sc.nextLine());
			logger.info("Enter longitude: ");
			longitude = Double.parseDouble(sc.nextLine());

			findAreaOfObject(latitude, longitude);

		} catch (Throwable T) {
			if (T instanceof NumberFormatException) {
				logger.info("Longitude, latitude muss be double. Number of area muss be integer.");
			} else {
				logger.info("Wrong input " + T.getMessage());
			}
		}
	}

	/*
	 * For case 6: Remove all objects using same keyID
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void removeAllObjectsWithKey() {
		String keyID = null;

		try {
			logger.info("Remove all objects from keyID...");
			logger.info("Enter keyID: ");
			keyID = sc.nextLine();

			if (keyID.isBlank()) {
				logger.info("Input keyID was empty");
			} else {
				if (isKeyIdExist(keyID)) {
					callRemoveAllObjectFromKeyID = service.removeAllObjectFromKeyID(keyID);
					responseRemoveObjectsFromKeyID = callRemoveAllObjectFromKeyID.execute();

					if (responseRemoveObjectsFromKeyID.body().getOk()) {
						logger.info("Remove all objects with key ID: %s successful.", keyID);
					} else {
						logger.info("Remove all objects with key ID: %s NOT successful.", keyID);
					}
				} else {
					logger.info("Not found keyID: %s in database.", keyID);
				}
			}

		} catch (Throwable T) {
			logger.info("Error when removing objects from keyID..." + T.getMessage());
		}
	}

	/*
	 * For case 7: Remove a specific object
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void removeSpecifiedObject() {
		String keyID = null;
		String fieldName = null;

		try {
			logger.info("Remove a specified object...");
			logger.info("Enter keyID: ");
			keyID = sc.nextLine();

			if (keyID.isBlank()) {
				logger.info("Input keyID was empty");
			} else {
				if (isKeyIdExist(keyID)) {
					logger.info("Enter fieldName: ");
					fieldName = sc.nextLine();

					if (fieldName.isBlank()) {
						logger.info("Input fieldName was empty");
					} else {
						if (checkKeyidExistInDatabase(keyID, fieldName)) {
							callDelOneObjectFromKeyidFieldName = service.delOneObjectFromKeyidFieldName(keyID,
									fieldName);
							responseDelOneObjectFromKeyidFieldName = callDelOneObjectFromKeyidFieldName.execute();

							if (responseDelOneObjectFromKeyidFieldName.body().getOk()) {
								logger.info("Remove successful object with keyID: %s, fieldName: %s", keyID, fieldName);
							} else {
								logger.info("Remove unsuccessful object with keyID: %s, fieldName: %s", keyID,
										fieldName);
							}
						} else {
							logger.info("Key: " + keyID + " and field name: " + fieldName
									+ " don't exist in database.");
						}
					}

				} else {
					logger.info("Not found keyID: %s in database.", keyID);
				}
			}
		} catch (Throwable T) {
			logger.info("Error when removing a object from keyID and fieldName..." + T.getMessage());
		}
	}

	/*
	 * For case 8: Return true if found keyID in database
	 * 
	 * @param oldkeyID: keyID of object
	 * 
	 * @return true if found object/ false if not
	 * 
	 */
	private static boolean isKeyIdExist(String oldkeyID) {
		// Get JSON Object from URL
		JSONObject json;
		List<GeoObjects> listGeoObjects = null;

		try {
			json = readJsonFromUrl(URL + SCAN + oldkeyID);
			String strJson = json.toString();
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(JsonElement.class, new org.ict.geofencing.utils.CoordinatesDeserializer())
					.create();
			GetObjectWithKey geoObj = gson.fromJson(strJson, GetObjectWithKey.class);
			listGeoObjects = geoObj.getObjects();
		} catch (JSONException e) {
			logger.info("Error with JSON " + e.getMessage());
		} catch (IOException e) {
			logger.info("Error with IOException " + e.getMessage());
		}

		if (listGeoObjects == null) // if nothings entered
			return false;
		else
			return listGeoObjects.size() != 0 ? true : false;
	}

	/*
	 * For case 8: Rename a keyID
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void renameKeyID() {
		try {
			String oldKeyID = "";
			String newKeyID = "";
			logger.info("Rename key...");
			logger.info("Enter old keyID: ");
			oldKeyID = sc.nextLine();

			if (oldKeyID.isBlank()) {
				logger.info("Input oldKeyID was empty");
			} else {
				/* Check if found key in database */
				if (isKeyIdExist(oldKeyID)) {
					logger.info("Enter new keyID: ");
					newKeyID = sc.nextLine();

					if (newKeyID.isBlank()) {
						logger.info("Input newKeyID was empty");
					} else {
						callRenameKeyid = service.renameKey(oldKeyID, newKeyID);
						responseRenameKey = callRenameKeyid.execute();

						if (responseRenameKey.body().getOk())
							logger.info("Rename key " + oldKeyID + " to new key name " + newKeyID + " successful.");
						else
							logger.info("Rename keyID " + oldKeyID + "not successful.");
					}
				} else
					logger.info("Not found old keyID in database.");
			}
		} catch (Exception e) {
			logger.info("Error " + e.toString() + " when rename keyID.");
		}
	}

	/*
	 * For case 9: Set read only mode for server
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void setReadMode() {
		String readOnlyMode = null;

		try {
			logger.info("Set server read only mode...");

			callServerStatus = service.checkServerStatus();
			responseServerStatus = callServerStatus.execute();

			if (responseServerStatus.body().getStats().getReadOnly()) {
				logger.info("Read only is on.");
				logger.info("Set read mode? Enter yes for on, no for off: ");
				readOnlyMode = sc.nextLine();

				// Set off to read only mode
				if (readOnlyMode.toLowerCase().equals("no")) {

					callSetReadMode = service.setReadMode(readOnlyMode);
					responseReadMode = callSetReadMode.execute();

					if (responseReadMode.body().getOk()) {
						logger.info("Set successful read only mode off.");
					}
				} else if (readOnlyMode.toLowerCase().equals("yes")) {
					logger.info("Don't need to change read only mode.");
				} else {
					logger.info("Wrong input.");
				}
			} else {
				logger.info("Read only is off.");
				logger.info("Set read mode? Enter yes for on, no for off: ");
				readOnlyMode = sc.nextLine();

				// Set on to read only mode
				if (readOnlyMode.toLowerCase().equals("yes")) {
					callSetReadMode = service.setReadMode(readOnlyMode);
					responseReadMode = callSetReadMode.execute();

					if (responseReadMode.body().getOk()) {
						logger.info("Set successful read only mode on.");
					}
				} else if (readOnlyMode.toLowerCase().equals("no")) {
					logger.info("Don't need to change read only mode.");
				} else {
					logger.info("Wrong input.");
				}
			}
		} catch (Throwable T) {
			logger.info("Wrong input " + T.getMessage());
		}
	}

	/*
	 * For case 10: Ping to server
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void pingToServer() {
		try {
			logger.info("Ping to server...");
			callPingToServer = service.pingToServer();
			responsePingServer = callPingToServer.execute();

			if (responsePingServer.body().getOk()) {
				logger.info("Server is reachable.");
				logger.info("Return from server: %s", responsePingServer.body().getPing());
				logger.info("Time to return packet: %s", responsePingServer.body().getElapsed());
			} else {
				logger.info("Server is unreachable.");
			}
		} catch (Throwable T) {
			logger.info("Error " + T.getMessage());
		}
	}

	/*
	 * For case 11: Get server information
	 * 
	 * @param
	 * 
	 * @return no return
	 * 
	 */
	private static void checkServerStatus() {
		try {
			logger.info("Check server status...");
			callServerStatus = service.checkServerStatus();
			responseServerStatus = callServerStatus.execute();

			if (responseServerStatus.body().getOk()) {
				logger.info("Tile38 is on.");
				logger.info("Number of objects in server: %s", responseServerStatus.body().getStats().getNumObjects());
				logger.info("Number of keys in server: %s", responseServerStatus.body().getStats().getNumCollections());
				if (responseServerStatus.body().getStats().getReadOnly()) {
					logger.info("Read only is on.");
				} else {
					logger.info("Read only is off.");
				}
				logger.info("Version of Tile38: %s", responseServerStatus.body().getStats().getVersion());
			} else {
				logger.info("Tile38 is off.");
			}
		} catch (Throwable T) {
			logger.info("Error " + T.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {

		String strExit = null;
		service = Tile38Service.createRetrofit(URL).create(Tile38Service.class);

		getAllObjectsPoints(false);

		while (true) {
			int choose = 0;

			displayMenu();

			try {
				logger.info("Enter your choice: ");
				choose = Integer.parseInt(sc.nextLine());
			} catch (Throwable T) {
				logger.info("Choose only number...");
			}

			// allDefinedObjectsPointsMap.clear();
			// getAllObjectsPoints(false);

			switch (choose) {
			case 1:
				/** Case 1: Set position for polygon **/
				setPositionForPolygon();
				allDefinedObjectsPointsMap.clear();
				getAllObjectsPoints(false);
				break;
			case 2:
				/** Case 2: Set position for rectangle **/
				setPositionForMinimumBoundingRectangle();
				allDefinedObjectsPointsMap.clear();
				getAllObjectsPoints(false);
				break;

			case 3:
				/** Case 3: Set position for a point **/
				setPositionForPoint();
				allDefinedObjectsPointsMap.clear();
				getAllObjectsPoints(false);
				break;

			case 4:
				/** Case 4: Get all defined objects **/
				getAllObjectsPoints(true);
				break;

			case 5:
				/** Case 5: Check if object exists in area by passing parameter **/
				checkObjectExistInArea();
				break;

			case 6:
				/** Case 6: Remove key from database **/
				removeAllObjectsWithKey();
				break;

			case 7:
				/** Case 7: Remove a specified object **/
				removeSpecifiedObject();
				break;

			case 8:
				/** Case 8: Rename keyID **/
				renameKeyID();
				break;

			case 9:
				/** Case 9: Set read mode **/
				setReadMode();
				break;
			case 10:
				/** Case 10: Ping to server **/
				pingToServer();
				break;

			case 11:
				/** Case 11: Check server status **/
				checkServerStatus();
				break;

			default:
				logger.info("Only number from 1 to 10 is acceptable...");
				break;
			}

			logger.info("Do it again? Enter N/n to quit. Other to do again: ");
			strExit = sc.nextLine();

			if (strExit.toLowerCase().equals("n")) {
				logger.info("====> Bye <====");
				break;
			}
		}

		sc.close();
	}
}
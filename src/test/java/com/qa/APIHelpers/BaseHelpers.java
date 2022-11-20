package com.qa.APIHelpers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public  class BaseHelpers {

    public static final String ORIGIN = "originId";
    public static final String DESTINATION = "destinationId";
    public static final String DEPARTURE_FROM = "departureFrom";
    public static final String DEPARTURE_TO = "departureTo";
    public static final String ADULT = "adult";
    public static final String CHILD = "child";
    public static final String INFANT = "infant";
    public static final String LEG = "leg";
    public static final String CABIN = "cabin";
    public static final String PAX = "pax";
    public static final String STOP = "stops";
    public static final String AIRLINE = "airline";
    public static final String TIME = "timeSlots";
    public static final String AIRPORTS = "airports";
    public final String CONTENT_TYPE = "application/json";


    public  JSONObject buildSearchRequest(
            String origin,
            String destination,
            String departureFromDate,
            String departureToDate,
            String cabin,
            int child,
            int adult,
            int infant) throws JSONException {

        JSONObject requestBody = new JSONObject();

        JSONArray legArray = new JSONArray();
        JSONObject legObject = new JSONObject();
        legObject.put(ORIGIN, origin);
        legObject.put(DESTINATION, destination);
        legObject.put(DEPARTURE_FROM,departureFromDate);
        legObject.put(DEPARTURE_TO,departureToDate);
        legArray.put(legObject);

        JSONObject paxPerson = new JSONObject();
        paxPerson.put(ADULT,adult);
        paxPerson.put(CHILD,child);
        paxPerson.put(INFANT,infant);

        JSONArray stopArray = new JSONArray();
        JSONArray airlineArray = new JSONArray();
        JSONObject timeSlots = new JSONObject();
        JSONObject airports = new JSONObject();


        requestBody.put(LEG, legArray);
        requestBody.put(CABIN,cabin);
        requestBody.put(PAX,paxPerson);
        requestBody.put(STOP, stopArray);
        requestBody.put(AIRLINE, airlineArray);
        requestBody.put(TIME, timeSlots);
        requestBody.put(AIRPORTS, airports);

        return requestBody;
    }

    /**
     * @param requestBody
     * @param url
     * @return
     * @throws JSONException
     */
    public Response sendPostRequest(JSONObject requestBody,  String url ) throws JSONException {


        // Create RequestSpecification
        RequestSpecification requestSpecification;

        if (requestBody == null) {
            requestSpecification = given();
        } else {
            requestSpecification = given().body(requestBody.toString()).with().contentType(CONTENT_TYPE);
        }


        // Send Post Request
        Response response = requestSpecification.when().log().all().post(url);

        System.out.println("Response: " + response.asString());
        return response;
    }


    /**
     * @param
     * @param url
     * @return
     * @throws JSONException
     */
    public Response sendGetRequest(String url ) throws JSONException {

        RequestSpecification requestSpecification;

        // Send Get Request
        requestSpecification = given().with().contentType(CONTENT_TYPE);
        Response response = requestSpecification.get(url);

        System.out.println("Response: " + response.asString());
        return response;
    }

    /**
     * @param response
     * @return
     * @throws JSONException
     */
    public boolean validateResponse(Response response){
        try {
            return response.statusCode() == 200 && response.contentType().equals("application/json");
        } catch (RuntimeException e){
            return false;
        }
    }

}
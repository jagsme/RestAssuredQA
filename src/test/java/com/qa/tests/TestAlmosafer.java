package com.qa.tests;

import com.qa.APIHelpers.BaseHelpers;
import com.qa.Utils.Date;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class TestAlmosafer {

    BaseHelpers APIcommon = new BaseHelpers();



    @BeforeAll
    static void setup() {

        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
        RestAssured.baseURI = "https://ae.almosafer.com/api/";

    }

    @Test()
    public void testGetResourceCodeDubai() throws JSONException {


        Response response = APIcommon.sendGetRequest(baseURI+"flight/resource/codes/DXB");
        assert APIcommon.validateResponse(response) : String.format("Response %s or %s is incorrect ", response.statusCode(), response.contentType());

    }


    @Test()
    public void testFlightFareFromDubaiToJordan() throws JSONException {
        String departDate = Date.getFormattedDateFromToday(37, Date.DATE_FORMAT_PATTERN_YYYY_MM_DD);
        String arrivalDate = Date.getFormattedDateFromToday(40, Date.DATE_FORMAT_PATTERN_YYYY_MM_DD);
        String destination = "AMM";
        String origin = "DXB";
        String cabin = "Economy";
        int child = 1;
        int adult = 1;
        int infant = 0;

        JSONObject requestBody = APIcommon.buildSearchRequest(origin,
                destination,
                departDate,
                arrivalDate,
                cabin,
                child,
                adult,
                infant);

        Response response = APIcommon.sendPostRequest(requestBody,baseURI+"v3/flights/flight/get-fares-calender");

        assert APIcommon.validateResponse(response) : String.format("Response %s or %s is incorrect ", response.statusCode(), response.contentType());

    }
}

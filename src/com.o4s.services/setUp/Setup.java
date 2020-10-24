package setUp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import constants.ApiUrlConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ReadPropertyFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Setup {

    Response response;

    public Response getApi(String url) {
        // TODO: Remove the url argument and use RestAssured.baseURI
        response = given().when().log().all().get(url).then().extract().response();
        return response;
    }
    public Response getApi(String url, String token) {
        response = RestAssured.given().header("Content-Type","application/json").auth().oauth2(token).when().log().all().get(url).then().extract().response();
        return response;
    }
    public Response getApi(String url, String pathParam, String token) {
        response = RestAssured.given().header("Content-Type","application/json").pathParam("id",pathParam).auth().oauth2(token).when().log().all().get(url).then().extract().response();
        return response;
    }
    public Response getApi(String url, String queryParamVar, String queryParam, String token) {
        response = RestAssured.given().header("Api-Secret-Key","ycq55IbIjkLb").header("Api-Token","c84d563b77441d784dce71323f69eb42")
                .header("Content-Type","application/json").queryParam(queryParamVar,queryParam).header("Authorization","bearer "+token)
                .when().log().all().get(url).then().extract().response();
        return response;
    }
    public Response getApi(String url, JsonObject data) {
        Map<String, Object> queryParam = new Gson().fromJson(data, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        response = given().when().log().all().get(url).then().extract().response();
        return response;
    }

    public Response postApi(String url, String requestBody, String contentType, String token) {
        // TODO: Remove the url argument and use RestAssured.baseURI
        response = given().header("Content-Type", contentType).header("Api-Secret-Key","ycq55IbIjkLb")
                .header("Api-Token","c84d563b77441d784dce71323f69eb42").header("Authorization","bearer "+token)
                .body(requestBody).when().log().all().post(url).then().extract().response();

        return response;
    }
    public Response deleteApi(String url, String token) {
        // TODO: Remove the url argument and use RestAssured.baseURI
        response= given().header("Content-Type","application/json").auth().oauth2(token).when().log().all().delete(url).then().extract().response();
        return response;
    }
    public Response postApi(String url, String bodyRequest, String token) {
        response = RestAssured.given().header("Content-Type","application/json").body(bodyRequest).auth().oauth2(token).when().log().all().post(url).then().extract().response();
        return response;
    }

}

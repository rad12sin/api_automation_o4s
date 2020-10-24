package api;

import com.google.gson.JsonObject;
import com.google.protobuf.Api;
import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;

public class HerosGetApi {
    public Response response;
    Setup setup=new Setup();
    String baseUrl= ApiUrlConstants.baseUrl;
    String apiUri=baseUrl+ApiUrlConstants.getHerosUrl;
    String token= ApiUrlConstants.bearerToken;

    public Response herosGetApi(){

        response=setup.getApi(apiUri,token);
        return response;
    }
}

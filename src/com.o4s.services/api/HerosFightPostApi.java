package api;

import com.google.gson.JsonObject;
import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;

public class HerosFightPostApi {
    public Response response;
    Setup setup=new Setup();
    String baseUrl= ApiUrlConstants.baseUrl;
    String apiUri=baseUrl+ApiUrlConstants.HerosFight;
    String token= ApiUrlConstants.bearerToken;

    public Response herosFightPostApi(){
        response=setup.postApi(apiUri,token);
        return response;
    }
}

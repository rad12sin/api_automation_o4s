package api;

import com.google.gson.JsonObject;
import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;

public class AddHeroForFightPostApi {
    public Response response;
    Setup setup=new Setup();
    String baseUrl= ApiUrlConstants.baseUrl;
    String apiUri=baseUrl+ApiUrlConstants.addHeroForFight;
    String token= ApiUrlConstants.bearerToken;

    public Response addHeorForFightPostApi(String heroId){
        JsonObject body=new JsonObject();
        body.addProperty("heroId", heroId);
        String bodyRequest=body.toString();
        response=setup.postApi(apiUri,bodyRequest,token);
        return response;
    }
}

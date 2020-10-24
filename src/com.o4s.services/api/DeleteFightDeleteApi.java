package api;

import com.google.gson.JsonObject;
import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;

public class DeleteFightDeleteApi {
    public Response response;
    Setup setup=new Setup();
    String baseUrl= ApiUrlConstants.baseUrl;
    String apiUri=baseUrl+ApiUrlConstants.deleteFight;
    String token= ApiUrlConstants.bearerToken;

    public Response deleteFightDeleteApi(){
        response=setup.deleteApi(apiUri,token);
        return response;
    }
}

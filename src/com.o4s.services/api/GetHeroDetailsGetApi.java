package api;

import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;

public class GetHeroDetailsGetApi {
    public Response response;
    Setup setup=new Setup();
    String baseUrl= ApiUrlConstants.baseUrl;
    String apiUrl=baseUrl+ApiUrlConstants.heroDetails;
    String token= ApiUrlConstants.bearerToken;

    public Response herosGetApi(String pathParam){
        String apiUri=apiUrl+pathParam;
        response=setup.getApi(apiUri,token);
        return response;
    }
}

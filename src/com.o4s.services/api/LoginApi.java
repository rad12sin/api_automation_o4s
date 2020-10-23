package api;

import com.google.gson.JsonObject;
import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;
import sun.security.pkcs11.wrapper.Constants;

public class LoginApi {
    public Response response;
    Setup setup=new Setup();

    String baseUrl= ApiUrlConstants.baseUrl;
    //String apiUri=baseUrl+ ApiUrlConstants.LOG_IN_API_URI;

    public Response loginApi(String uName, String pass, String role){
     /*   JsonObject body=new JsonObject();
        body.addProperty("username",uName);
        body.addProperty("password",pass);
        body.addProperty("role",role);
        String bodyRequest=body.toString();
        response=setup.getApi(apiUri,bodyRequest);
      */
        return response;
    }
}

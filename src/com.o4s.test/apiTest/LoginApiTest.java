package apiTest;


import api.LoginApi;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class LoginApiTest {
    LoginApi loginApi=new LoginApi();
    private Response response;
    private JsonObject json;

    @BeforeMethod
    private void getAuthToken() {
        //Read json file and store the json element in json variable
        json = utils.ReadJsonFile.getApiJson(System.getProperty("user.dir")+"\\com.wakefit.tests\\testData\\loginApi.json");
    }

    @Test
    private void loginApiTestCase1(){
        System.out.println("Test case 1: Check with valid user name, password and role");
        String userName=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("user_name").getAsString();
        String password=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("password").getAsString();
        String loginRole=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("loginRole").getAsString();
        response=loginApi.loginApi(userName,password,loginRole);
        int statusCode=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("status_code").getAsInt();
        Assert.assertEquals(response.getStatusCode(),statusCode);

        Assert.assertTrue((!response.toString().isEmpty()));
        String token=response.getBody().jsonPath().getString("token");
        Assert.assertTrue((!token.isEmpty()));
    }
}

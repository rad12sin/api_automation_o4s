package apiTest;

import api.AddHeroForFightPostApi;
import api.DeleteFightDeleteApi;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddHeroForFightPostApiTest {
    private AddHeroForFightPostApi addHeroForFightPostApi=new AddHeroForFightPostApi();
    private DeleteFightDeleteApi deleteFightDeleteApi=new DeleteFightDeleteApi();
    private Response response;
    private JsonObject json;
    @BeforeClass
    private void getAuthToken() throws InterruptedException {
        //Read json file and store the json element in json variable
        json = utils.ReadJsonFile.getApiJson(System.getProperty("user.dir")+"\\src\\com.o4s.test\\testData\\addHeroForFightPostApi.json");
        deleteFightDeleteApi.deleteFightDeleteApi();
    }
    @Test
    private void setAddHeroForFightPostApiTest1(){
        System.out.println("Test case 1: Add the Hero for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        int statusCode=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("statusCode").getAsInt();
        Assert.assertEquals(response.getStatusCode(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("message").toString());
        System.out.println("response="+response.asString());
    }
    @Test
    private void setAddHeroForFightPostApiTest2(){
        System.out.println("Test case 2: Add the 2nd Hero for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        int statusCode=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("statusCode").getAsInt();
        Assert.assertEquals(response.getStatusCode(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("message").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("message").toString());
    }
    @Test
    private void setAddHeroForFightPostApiTest3(){
        System.out.println("Test case 3: Add the 3rd Hero for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        int statusCode=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("statusCode").getAsInt();
        Assert.assertEquals(response.getStatusCode(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("message").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("message").toString());
    }
    @Test
    private void setAddHeroForFightPostApiTest4(){
        System.out.println("Test case 4: Add the same Hero who has been already added for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        String statusCode=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(response.jsonPath().get("errorCode").toString(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("error").toString());
    }
    @Test
    private void setAddHeroForFightPostApiTest5(){
        System.out.println("Test case 5: Add empty hero for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        String statusCode=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(response.jsonPath().get("errorCode").toString(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("error").toString());
    }
    @Test
    private void setAddHeroForFightPostApiTest6(){
        System.out.println("Test case 6: Add incorrect Hero Id for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        String statusCode=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(response.jsonPath().get("errorCode").toString(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("error").toString());
    }
    @Test
    private void setAddHeroForFightPostApiTest7(){
        System.out.println("Test case 6: Add null in Hero Id for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(6).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        String statusCode=json.get("test_cases").getAsJsonArray().get(6).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(response.jsonPath().get("errorCode").toString(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(6).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("error").toString());
    }
    @Test
    private void setAddHeroForFightPostApiTest8(){
        System.out.println("Test case 8: Add 4th Hero for fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(7).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        String statusCode=json.get("test_cases").getAsJsonArray().get(7).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(response.jsonPath().get("errorCode").toString(),statusCode);
        String message=json.get("test_cases").getAsJsonArray().get(7).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(message,response.jsonPath().get("error").toString());
    }
}

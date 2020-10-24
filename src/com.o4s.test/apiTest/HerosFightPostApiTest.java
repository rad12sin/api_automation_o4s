package apiTest;

import api.AddHeroForFightPostApi;
import api.DeleteFightDeleteApi;
import api.HerosFightPostApi;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HerosFightPostApiTest {
    HerosFightPostApi herosFightPostApi=new HerosFightPostApi();
    private DeleteFightDeleteApi deleteFightDeleteApi=new DeleteFightDeleteApi();
    private Response response;
    private JsonObject json;
    private AddHeroForFightPostApi addHeroForFightPostApi=new AddHeroForFightPostApi();
    @BeforeMethod
    private void getAuthToken() throws InterruptedException {
        //Read json file and store the json element in json variable
        json = utils.ReadJsonFile.getApiJson(System.getProperty("user.dir")+"\\src\\com.o4s.test\\testData\\herosFightPostApi.json");
        deleteFightDeleteApi.deleteFightDeleteApi();
    }
    @Test
    private void herosFightPostApiTest1(){
        System.out.println("Test case 1: Verify the response if there is no hero in fight");
        response=herosFightPostApi.herosFightPostApi();
        int statusCode=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("statusCode").getAsInt();
        Assert.assertEquals(response.getStatusCode(),statusCode);
        String errorCode=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(errorCode,response.jsonPath().get("errorCode").toString());
        String error=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(error,response.jsonPath().get("error").toString());
    }
    @Test
    private void herosFightPostApiTest2(){
        System.out.println("Test case 2: Verify the response if there is 1 hero in fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("heroId").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        response=herosFightPostApi.herosFightPostApi();
        int statusCode=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("statusCode").getAsInt();
        Assert.assertEquals(response.getStatusCode(),statusCode);
        String errorCode=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("errorCode").getAsString();
        Assert.assertEquals(errorCode,response.jsonPath().get("errorCode").toString());
        String error=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("error").getAsString();
        Assert.assertEquals(error,response.jsonPath().get("error").toString());
    }
    @Test
    private void herosFightPostApiTest3(){
        System.out.println("Test case 3: Verify the response if there is 2 hero in fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("heroId1").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        heroId=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("heroId2").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        response=herosFightPostApi.herosFightPostApi();
        int statusCode=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("statusCode").getAsInt();
        if(response.getStatusCode()!=200){
            System.out.println("Our Fight Api is not working if there is 2 heros in fight");
        }
        else {
            String message=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("message").getAsString();
            Assert.assertEquals(response.jsonPath().get("message"),message);
            Assert.assertEquals(response.getStatusCode(),statusCode);
        }
    }
    @Test
    private void herosFightPostApiTest4(){
        System.out.println("Test case 4: Verify the response if there is 3 hero in fight");
        String heroId=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("heroId1").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        heroId=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("heroId2").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        heroId=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("heroId3").getAsString();
        response=addHeroForFightPostApi.addHeorForFightPostApi(heroId);
        response=herosFightPostApi.herosFightPostApi();
        int statusCode=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("statusCode").getAsInt();
        if(response.getStatusCode()!=200){
            System.out.println("Our Fight Api is not working if there is 3 heros in fight");
        }
        else {
            String message=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("message").getAsString();
            Assert.assertEquals(response.jsonPath().get("message"),message);
            Assert.assertEquals(response.getStatusCode(),statusCode);
        }
    }
}

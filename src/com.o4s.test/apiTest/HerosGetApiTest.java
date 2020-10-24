package apiTest;


import api.HerosGetApi;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HerosGetApiTest {
    HerosGetApi herosGetApi=new HerosGetApi();
    private Response response;
    private JsonObject json;
    @BeforeClass
    private void getAuthToken() throws InterruptedException {
        //Read json file and store the json element in json variable
        json = utils.ReadJsonFile.getApiJson(System.getProperty("user.dir")+"\\src\\com.o4s.test\\testData\\herosGetApi.json");
    }
    @Test
    private void HerosGetApiTest1(){
        System.out.println("Test case 1: Verify the status code of api");
        response=herosGetApi.herosGetApi();
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(response.asString());
    }
    @Test
    private void HerosGetApiTest2(){
        System.out.println("Test case 2: Verify the id, name and power level of 1st fighter");
        response=herosGetApi.herosGetApi();
        String id=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
        Assert.assertEquals(id,response.jsonPath().getJsonObject("[0].id").toString());
        String name=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().getJsonObject("[0].name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().getJsonObject("[0].powerlevel").toString());
    }
    @Test
    private void HerosGetApiTest3(){
        System.out.println("Test case 3: Verify the id, name and power level of 2nd fighter");
        response=herosGetApi.herosGetApi();
        String id=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("id").getAsString();
        Assert.assertEquals(id,response.jsonPath().getJsonObject("[1].id").toString());
        String name=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().getJsonObject("[1].name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().getJsonObject("[1].powerlevel").toString());
    }
    @Test
    private void HerosGetApiTest4(){
        System.out.println("Test case 4: Verify the id, name and power level of 3rd fighter");
        response=herosGetApi.herosGetApi();
        String id=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("id").getAsString();
        Assert.assertEquals(id,response.jsonPath().getJsonObject("[2].id").toString());
        String name=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().getJsonObject("[2].name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().getJsonObject("[2].powerlevel").toString());
    }
    @Test
    private void HerosGetApiTest5(){
        System.out.println("Test case 5: Verify the id, name and power level of 4th fighter");
        response=herosGetApi.herosGetApi();
        String id=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("id").getAsString();
        Assert.assertEquals(id,response.jsonPath().getJsonObject("[4].id").toString());
        String name=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().getJsonObject("[4].name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().getJsonObject("[4].powerlevel").toString());
    }
    @Test
    private void HerosGetApiTest6(){
        System.out.println("Test case 6: Verify the id, name and power level of 5th fighter");
        response=herosGetApi.herosGetApi();
        String id=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("id").getAsString();
        Assert.assertEquals(id,response.jsonPath().getJsonObject("[5].id").toString());
        String name=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().getJsonObject("[5].name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().getJsonObject("[5].powerlevel").toString());
    }
    @Test
    private void HerosGetApiTest7(){
        System.out.println("Test case 7: Verify the id, name and power level of 6th fighter");
        response=herosGetApi.herosGetApi();
        String id=json.get("test_cases").getAsJsonArray().get(6).getAsJsonObject().get("id").getAsString();
        Assert.assertEquals(id,response.jsonPath().getJsonObject("[6].id").toString());
        String name=json.get("test_cases").getAsJsonArray().get(6).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().getJsonObject("[6].name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(6).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().getJsonObject("[6].powerlevel").toString());
    }
}

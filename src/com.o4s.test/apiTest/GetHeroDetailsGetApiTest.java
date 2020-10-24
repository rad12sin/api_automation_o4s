package apiTest;

import api.GetHeroDetailsGetApi;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetHeroDetailsGetApiTest {
    private GetHeroDetailsGetApi getHeroDetailsGetApi=new GetHeroDetailsGetApi();
    private Response response;
    private JsonObject json;
    @BeforeClass
    private void getAuthToken() throws InterruptedException {
        //Read json file and store the json element in json variable
        json = utils.ReadJsonFile.getApiJson(System.getProperty("user.dir")+"\\src\\com.o4s.test\\testData\\getHeroDetailsGetApi.json");
    }
    @Test
    private void GetHerosDetailsGetApiTest1(){
        System.out.println("Test case 1: Verify the Details of 1st Hero");
        String pathParam=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
        response=getHeroDetailsGetApi.herosGetApi(pathParam);
        Assert.assertEquals(response.getStatusCode(),200);
        String name=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().get("name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(0).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().get("powerlevel").toString());
        System.out.println(response.asString());
    }
    @Test
    private void GetHerosDetailsGetApiTest2(){
        System.out.println("Test case 2: Verify the Details of 2nd Hero");
        String pathParam=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("id").getAsString();
        response=getHeroDetailsGetApi.herosGetApi(pathParam);
        Assert.assertEquals(response.getStatusCode(),200);
        String name=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().get("name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(1).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().get("powerlevel").toString());
        System.out.println(response.asString());
    }
    @Test
    private void GetHerosDetailsGetApiTest3(){
        System.out.println("Test case 3: Verify the Details of 3rd Hero");
        String pathParam=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("id").getAsString();
        response=getHeroDetailsGetApi.herosGetApi(pathParam);
        Assert.assertEquals(response.getStatusCode(),200);
        String name=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().get("name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(2).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().get("powerlevel").toString());
        System.out.println(response.asString());
    }
    @Test
    private void GetHerosDetailsGetApiTest4(){
        System.out.println("Test case 1: Verify the Details of 1st Hero");
        String pathParam=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("id").getAsString();
        response=getHeroDetailsGetApi.herosGetApi(pathParam);
        Assert.assertEquals(response.getStatusCode(),200);
        String name=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().get("name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(3).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().get("powerlevel").toString());
        System.out.println(response.asString());
    }
    @Test
    private void GetHerosDetailsGetApiTest5(){
        System.out.println("Test case 5: Verify the Details of 5th Hero");
        String pathParam=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("id").getAsString();
        response=getHeroDetailsGetApi.herosGetApi(pathParam);
        Assert.assertEquals(response.getStatusCode(),200);
        String name=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().get("name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(4).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().get("powerlevel").toString());
        System.out.println(response.asString());
    }
    @Test
    private void GetHerosDetailsGetApiTest6(){
        System.out.println("Test case 6: Verify the Details of 1st Hero");
        String pathParam=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("id").getAsString();
        response=getHeroDetailsGetApi.herosGetApi(pathParam);
        Assert.assertEquals(response.getStatusCode(),200);
        String name=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(name,response.jsonPath().get("name").toString());
        String powerLevel=json.get("test_cases").getAsJsonArray().get(5).getAsJsonObject().get("powerlevel").getAsString();
        Assert.assertEquals(powerLevel,response.jsonPath().get("powerlevel").toString());
        System.out.println(response.asString());
    }
}

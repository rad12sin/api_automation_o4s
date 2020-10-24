package apiTest;

import api.DeleteFightDeleteApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteFightDeleteApiTest {
    private DeleteFightDeleteApi deleteFightDeleteApi=new DeleteFightDeleteApi();
    private Response response;
    @Test
    private void deleteFightDeletApiTest1() {
        System.out.println("Test case 1: Verify the status code");
        response = deleteFightDeleteApi.deleteFightDeleteApi();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    private void deleteFightDeletApiTest2() {
        System.out.println("Test case 2: Verify the message");
        response = deleteFightDeleteApi.deleteFightDeleteApi();
        String message="Fight has been deleted and now all heroes went back to Helicarrier Ship.";
        Assert.assertEquals(response.jsonPath().get("message").toString(),message);
    }
}

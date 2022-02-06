package Tests;

import Utilities.APIServiceUtils;
import Utilities.ReadProps;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Utilities.TestListeners.class)
public class GetUserTest
{

    @BeforeClass
    public void login() throws Exception {
        APIServiceUtils.setBaseURL();
    }
    @Test(priority = 1)
    public void test_single_user_infromation_using_get_api() throws Exception {
        Response response1 = APIServiceUtils.createRandomUserAPI();
        String id1 = response1.getBody().jsonPath().getJsonObject("id");
        String name1 =  response1.getBody().jsonPath().getJsonObject("name").toString();
        String email1 =  response1.getBody().jsonPath().getJsonObject("email").toString();
        String gender1 =  response1.getBody().jsonPath().getJsonObject("gender").toString();
        String status1 =  response1.getBody().jsonPath().getJsonObject("status").toString();

        Response response = APIServiceUtils.getUserAPI(id1);
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");

        String id2 =  response.getBody().jsonPath().getJsonObject("id").toString();
        String name2 =  response.getBody().jsonPath().getJsonObject("name").toString();
        String email2 =  response.getBody().jsonPath().getJsonObject("email").toString();
        String gender2 =  response.getBody().jsonPath().getJsonObject("gender").toString();
        String status2 =  response.getBody().jsonPath().getJsonObject("status").toString();

        Assert.assertEquals(name1,name2,"name mismatch");
        Assert.assertEquals(email1, email2,"email mismatch");
        Assert.assertEquals(gender1, gender2,"gender mismatch");
        Assert.assertEquals(status1, status2, "status mismatch");

    }

    @Test(priority = 2)
    public void test_all_user_infromation_using_get_api() throws Exception {
        Response response = APIServiceUtils.getAllUserAPI();
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertNotNull(response.getBody().jsonPath().getJsonObject("name").toString());
    }

}
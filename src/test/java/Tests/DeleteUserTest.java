package Tests;

import Utilities.APIServiceUtils;
import Utilities.ReadProps;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Utilities.TestListeners.class)
public class DeleteUserTest
{

    @BeforeClass
    public void login() throws Exception {
        APIServiceUtils.setBaseURL();
    }
    @Test(priority = 1)
    public void test_single_user_deletion_using_delete_api() throws Exception {
        Response response1 = APIServiceUtils.createRandomUserAPI();
        String id = response1.getBody().jsonPath().getJsonObject("id").toString();
        Response response = APIServiceUtils.DeleteUserAPI(id);
        Assert.assertEquals(response.getStatusCode(),204,"status code mismatch");

    }

}
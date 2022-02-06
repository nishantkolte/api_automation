package Tests;

import Utilities.APIServiceUtils;
import Utilities.ReadProps;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.restassured.response.Response;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


@Listeners(Utilities.TestListeners.class)
public class CreateUserTest

{
    @BeforeClass
    public void startUp() throws Exception {
        ReadProps.UpdatePropertyFile();
        APIServiceUtils.setBaseURL();
    }
    @Test (enabled = false)
    public void test_user_created_successfully_using_post_api_method1_body_from_json_file() throws Exception {
        Response response = APIServiceUtils.createUserAPIFromJson("CreateUserJsonBody.json");
        Assert.assertEquals(response.getStatusCode(),201,"status code mismatch");

        String id =  response.getBody().jsonPath().getJsonObject("id").toString();
        String name =  response.getBody().jsonPath().getJsonObject("name").toString();
        String email =  response.getBody().jsonPath().getJsonObject("email").toString();
        String gender =  response.getBody().jsonPath().getJsonObject("gender").toString();
        String status =  response.getBody().jsonPath().getJsonObject("status").toString();

        String body = Files.readString(Path.of(System.getProperty("user.dir") + "\\src\\test\\java\\Data\\CreateUserJsonBody.json"), StandardCharsets.US_ASCII);
        JsonMapper mapper = new JsonMapper();
        JsonNode node = mapper.readValue(body, JsonNode.class);

        // method - 2
        Assert.assertEquals(name, node.get("name").asText(),"name mismatch");
        Assert.assertEquals(email, node.get("email").asText(),"email mismatch");
        Assert.assertEquals(gender, node.get("gender").asText(),"gender mismatch");
        Assert.assertEquals(status, node.get("status").asText(), "status mismatch");
    }

    @Test (enabled = true)
    public void test_user_created_successfully_using_post_api_method2_body_from_properties_file() throws Exception {
        Response response = APIServiceUtils.createUserAPIFromProp();
        Assert.assertEquals(response.getStatusCode(),201,"status code mismatch");

        String id =  response.getBody().jsonPath().getJsonObject("id").toString();
        String name =  response.getBody().jsonPath().getJsonObject("name").toString();
        String email =  response.getBody().jsonPath().getJsonObject("email").toString();
        String gender =  response.getBody().jsonPath().getJsonObject("gender").toString();
        String status =  response.getBody().jsonPath().getJsonObject("status").toString();


        // method -1
        Assert.assertEquals(name, ReadProps.getValue("name"),"name mismatch");
        Assert.assertEquals(email, ReadProps.getValue("email"),"email mismatch");
        Assert.assertEquals(gender, ReadProps.getValue("gender"),"gender mismatch");
        Assert.assertEquals(status, ReadProps.getValue("status"), "status mismatch");

    }

    @Test (enabled = false)
    public void test_user_created_successfully_using_post_api_method3_using_dynamic_data() throws Exception {
        Response response = APIServiceUtils.createRandomUserAPI();
        Assert.assertEquals(response.getStatusCode(),201,"status code mismatch");

        String id =  response.getBody().jsonPath().getJsonObject("id").toString();
        String name =  response.getBody().jsonPath().getJsonObject("name").toString();
        String email =  response.getBody().jsonPath().getJsonObject("email").toString();
        String gender =  response.getBody().jsonPath().getJsonObject("gender").toString();
        String status =  response.getBody().jsonPath().getJsonObject("status").toString();


        // method -1
        Assert.assertNotNull(name,"name not found");
        Assert.assertNotNull(email,"email not found");
        Assert.assertNotNull(gender,"gender not found");
        Assert.assertNotNull(status,"status not found");

    }
}
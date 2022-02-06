package Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestListeners implements ITestListener {
    public static ExtentTest test;
    static ExtentReports report;

    @Override
    public void onFinish(ITestContext result) {
        report.endTest(test);
        report.flush();

    }

    @Override
    public void onStart(ITestContext result) {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
        report = new ExtentReports(System.getProperty("user.dir")+"\\target\\HTMLReport\\"+result.getSuite().getName()+"_Report_"+timestamp+".html");
        report.loadConfig(new File (System.getProperty("user.dir")+"\\target\\HTMLReport\\config.xml"));
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.log(
                LogStatus.FAIL,
                "<b> TEST CLASS :" + result.getInstanceName() +
                        "<br /> TEST METHOD : "+result.getName() +
                        "<br /> STATUS : <font color=red> FAILED" +
                        "<br /> ERROR : </b>" +  result.getThrowable().toString()
        );

        test.log(LogStatus.INFO, "<b><u> REQUEST: </b></u> <br />" + APIServiceUtils.getRequestDetails(APIServiceUtils.request));
        test.log(LogStatus.INFO, "<font color=red>  <b><u> RESPONSE: </b></u><br />" +
                "<b>RESPONSE STATUS CODE </b>: " + APIServiceUtils.response.getStatusCode() + "<br />" +
                "<b>RESPONSE BODY </b>: " + APIServiceUtils.response.asPrettyString());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(LogStatus.SKIP,result.getInstanceName()+" : "+result.getName().replace("_"," ")+" -- SKIPPED");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = report.startTest(result.getName().replace("_"," "));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(
                LogStatus.PASS,
                "<b> TEST CLASS :" + result.getInstanceName() +
                        "<br /> TEST METHOD : "+result.getName() +
                        "<br /> STATUS : <font color=green> PASSED"
        );


        test.log(LogStatus.INFO, "<font color=green> <b><u> REQUEST: </b></u> <br />" + APIServiceUtils.getRequestDetails(APIServiceUtils.request));
        test.log(LogStatus.INFO, "<font color=green>  <b><u> RESPONSE: </b></u><br />" +
                "<b>RESPONSE STATUS CODE </b>: " + APIServiceUtils.response.getStatusCode() + "<br />" +
                "<b>RESPONSE BODY </b>: " + APIServiceUtils.response.asPrettyString());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
    }

}

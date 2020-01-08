package extendReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class ExtendReportTest {
    WebDriver driver;

   public ExtentHtmlReporter extentHtmlReporter;
   public ExtentReports extentReports;
   public ExtentTest extentTest;

    @BeforeMethod
    public void setup(){
       System.setProperty("webdriver.chrome.driver","C:\\Users\\sekar_p\\Framework\\ExtenReportFramework\\src\\test\\java\\chromeDriver\\chromedriverNew.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeTest
    public void setupExtendReprot(){
        extentHtmlReporter = new ExtentHtmlReporter("C:\\Users\\sekar_p\\Framework\\ExtenReportFramework\\src\\test\\java\\testReports\\extendReport"+System.currentTimeMillis()+".html");
        extentHtmlReporter.config().setDocumentTitle("Selenium UI Automation Reports");
        extentHtmlReporter.config().setReportName("Home Page Title Validations");
        extentHtmlReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentHtmlReporter);
        extentReports.setSystemInfo("Browser","Chrome");
        extentReports.setSystemInfo("TesterName","PhotonTester");
    }

    @Test
    public void openAmazonHomePage(){
        extentTest = extentReports.createTest("openAmazonHomePage");
        driver.get("https://www.amazon.com");
        String titleName = driver.getTitle();
        Assert.assertEquals(titleName,"Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more1");
    }

    @Test
    public void openYahooHomePage(){
        extentTest = extentReports.createTest("openYahooHomePage");
        driver.get("https://www.yahoo.com");
        String titleName = driver.getTitle();
        Assert.assertEquals(titleName,"Yahoo India | News, Finance, Cricket, Lifestyle and Entertainment");
    }

    @Test
    public void openRediffMailHomePage(){
        extentTest = extentReports.createTest("openRediffMailHomePage");
        driver.get("https://www.rediff.com");
        String titleName = driver.getTitle();
        Assert.assertEquals(titleName,"Rediff.com: News | Rediffmail | Stock Quotes | Shopping");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if(result.getStatus() == ITestResult.FAILURE){
            extentTest.log(Status.FAIL,"TestCase "+result.getName()+" Failed due to"+ result.getThrowable());
            String pathInfo = takeScreenShot(result.getName());
            extentTest.addScreenCaptureFromPath(pathInfo);
        }
        else if (result.getStatus() == ITestResult.SUCCESS){
            extentTest.log(Status.PASS,"TestCase "+result.getName()+" Passed successfully");
        }
        driver.quit();
    }

    @AfterTest
    public void flushReport(){
        extentReports.flush();
    }

    private String takeScreenShot(String methodName) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotSource = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String path = "C:\\Users\\sekar_p\\Framework\\ExtenReportFramework\\src\\test\\java\\testEvidance\\FailedScreenshot"+System.currentTimeMillis()+".jpg";
        FileUtil.copyFile(screenShotSource,new File(path));
        return path;
    }

}

package Netflix;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LogInFormTests {

    private WebDriver driver;

    private String URL ="https://www.netflix.com/ua-en/";
    private String INVALID_PASSWORD_MASSAGE = "Sorry, we can't find an account with this email address";
    private String PASSWORD = "password";
    private String INVALID_EMAIL = "email.com";
    private String EMAIL = "email@.com";


    @BeforeTest
    public void profileSetUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @Test
    public void checkLogInWithValidCredentials(){
        driver.findElement(By.xpath("//a[@class='authLinks redButton']")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//input[@name='userLoginId']")).sendKeys(EMAIL);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[@class='btn login-button btn-submit btn-small']")).click();
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='authLinks redButton']")));
        assertEquals(driver.getCurrentUrl(),URL);
    }

    @Test
    public void checkPasswordIsMasked(){
        driver.findElement(By.xpath("//a[@class='authLinks redButton']")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(PASSWORD);
        assertEquals(driver.findElement(By.xpath("//input[@name='password']")).getAttribute("type"),"password");
    }

    @Test
    public void checkLogInWithInvalidPassword(){
        driver.findElement(By.xpath("//a[@class='authLinks redButton']")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//input[@name='userLoginId']")).sendKeys(INVALID_EMAIL);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[@class='btn login-button btn-submit btn-small']")).click();
        assertTrue(driver.findElement(By.xpath("//div[@class='ui-message-contents']")).getText().contains(INVALID_PASSWORD_MASSAGE));
    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}



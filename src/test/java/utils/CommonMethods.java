package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import javafx.fxml.Initializable;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonMethods {


    //public static WebDriver driver;
    public static ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();

    public static void setUp(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        switch (ConfigReader.getPropertyValue("browser")){
            case "chrome":
                //System.setProperty("webdriver.chrome.driver","C:\\Users\\imark\\IdeaProjects\\JavaBatch9TestNG\\drivers\\chromedriver.exe");
                WebDriverManager.chromedriver().setup();
                if(ConfigReader.getPropertyValue("headless").equals("true")){
                    ChromeOptions chromeOptions=new ChromeOptions();
                    chromeOptions.setHeadless(true);
                    webdriver.set(new ChromeDriver(chromeOptions));
                }else {
                    webdriver.set(new ChromeDriver());
                }
                break;
            case "firefox":
                //System.setProperty("webdriver.gecko,driver","C:\\Users\\imark\\IdeaProjects\\JavaBatch9TestNG\\drivers\\geckodriver.exe");
                WebDriverManager.firefoxdriver().setup();
                webdriver.set(new FirefoxDriver());
                break;
            default:
                throw new RuntimeException("Invalid name of browser");
        }
        webdriver.get().get(ConfigReader.getPropertyValue("url"));
        webdriver.get().manage().window().maximize();
        webdriver.get().manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public static void sendText(WebElement element,String textToSend){
        element.clear();
        element.sendKeys(textToSend);
    }

    public static   WebDriverWait getWait(){
        WebDriverWait wait=new WebDriverWait(webdriver.get(),Constants.EXPLICIT_WAIT);
        return wait;
    }

    public static   void waitForClickability(WebElement element){
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(WebElement element){
        waitForClickability(element);
        element.click();
    }

    public static void waitForVisibility(WebElement element){
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static JavascriptExecutor getJSexecutor(){
        JavascriptExecutor js=(JavascriptExecutor) webdriver.get();
        return js;
    }

    public static void jsClick(WebElement element){
        getJSexecutor().executeScript("arguments[0].click()",element);
    }

    public static byte[] takeScreenshot(String fileName){
        TakesScreenshot ts=(TakesScreenshot) webdriver.get();

        byte[] picBytes=ts.getScreenshotAs(OutputType.BYTES);

        File sourceFile=ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(sourceFile, new File(Constants.SCREENSHOT_FILEPATH + fileName+" " + getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picBytes;
    }


    public static String getTimeStamp(String pattern){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Actions action(){
        Actions action=new Actions(webdriver.get());
        return action;
    }

    public static void moveToElement(WebElement element){
        action().moveToElement(element).perform();
    }



    public static void tearDown(){
        if(webdriver.get()!=null){
            webdriver.get().quit();
        }
    }
}

package com.example.implementations;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.example.interfaces.ISpotifySupport;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.Context;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@GraphWalker(value = "random(edge_coverage(100))", start = "Start", groups = { "default" })
public class SpotifySupport extends ExecutionContext implements ISpotifySupport {

    private WebDriver driver = null;
    private WebDriverWait waiter = null;

    public final static Path MODEL_PATH = Paths.get("src/main/java/com/example/graph/SpotifySupport.json");

    @BeforeExecution
    public void setup() {   
        System.setProperty("webdriver.chrome.driver", "C:/Users/kahraman.bayraktar/source/Repos/BOUNSWE/SWE550/demo/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.get("chrome://settings/clearBrowserData");
        driver.manage().window().maximize();     
        waiter = new WebDriverWait(driver, 30);
    }

    @AfterExecution
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void Start() {
    }

    @Override
    public void e_StartBrowser() {
    }

    @Override
    public void v_BrowserStarted() {
        Assertions.assertNotNull(driver);
    }

    @Override
    public void e_OpenSpotify() {
        driver.get("https://spotify.com");
    }

    @Override
    public void v_SpotifyHomePageIsOpen() {
        Sleep();
        
        By by = By.cssSelector("a[data-ga-action='log-in']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));

        WebElement loginButton = driver.findElement(by);
        Assertions.assertNotNull(loginButton);
    }

    @Override
    public void e_OpenSupportPage() {
        Sleep();

        By by = By.cssSelector("a[data-ga-action='help']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement supportButton = driver.findElement(by);
        supportButton.click();
    }

    @Override
    public void v_SupportPageIsOpen() {
        Sleep();

        By by = By.cssSelector("input[type='text'][aria-label='Arama']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement searchDiv = driver.findElement(by);  
        Assertions.assertNotNull(searchDiv);
    }

    @Override
    public void e_DoSupportSearchWithResults() {
        Sleep();

        By by = By.cssSelector("input[type='text'][aria-label='Arama']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchDiv = driver.findElement(by);
        searchDiv.sendKeys("playlist");
    }

    @Override
    public void v_SearchResultsReturned() {
        Sleep();

        By by = By.cssSelector("div[data-testid='aria-message']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement resultDiv = driver.findElement(by);
        Assertions.assertNotNull(resultDiv);
    }

    @Override
    public void e_DoSupportSearchWithoutResults() {
        Sleep();

        By by = By.cssSelector("input[type='text'][aria-label='Arama']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchDiv = driver.findElement(by);
        searchDiv.clear();
        searchDiv.sendKeys("dgdfhfgjfj");
    }

    @Override
    public void v_SearchResultsNotReturned() {
        Sleep();

        By by = By.cssSelector("span[data-testid='no-results-msg']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement resultDiv = driver.findElement(by);
        Assertions.assertNotNull(resultDiv);
    }

    @Override
    public void e_ClearSearch() {
        Sleep();
        
        By by = By.cssSelector("input[type='text'][aria-label='Arama']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchDiv = driver.findElement(by);
        searchDiv.clear();
    }

    @Test
    public void runSmokeTest() throws IOException {
        Context context = new SpotifySupport();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new AStarPath(new ReachedVertex("v_BrowserStarted")));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    public void runFunctionalTest() throws IOException {
        Context context = new SpotifySupport();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new RandomPath(new EdgeCoverage(100)));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    public void runStabilityTest() throws IOException {
        Context context = new SpotifySupport();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }
    
    private void Sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

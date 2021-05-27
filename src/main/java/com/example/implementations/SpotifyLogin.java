package com.example.implementations;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.example.interfaces.ISpotifyLogin;

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
public class SpotifyLogin extends ExecutionContext implements ISpotifyLogin {

    private WebDriver driver = null;
    private WebDriverWait waiter = null;

    public final static Path MODEL_PATH = Paths.get("src/main/java/com/example/graph/SpotifyLogin.json");

    public final static String username = "xxx@gmail.com";
    public final static String password = "xxx";

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
        Sleep();
        driver.get("https://open.spotify.com");
    }

    @Override
    public void v_SpotifyWebPlayerHomePageIsOpen() {
        Sleep();

        By by = By.cssSelector("button[data-testid='login-button']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement loginButton = driver.findElement(by);
        Assertions.assertNotNull(loginButton);
    }

    @Override
    public void e_OpenLoginPage() {
        By by = By.cssSelector("button[data-testid='login-button']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement loginButton = driver.findElement(by);
        loginButton.click();
    }

    @Override
    public void v_LoginPageIsOpen() {
        Sleep();

        By by = By.name("username");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement firstResult = driver.findElement(by);
        Assertions.assertNotNull(firstResult);
    }

    @Override
    public void e_LoginViaValidCredentials() {
        Sleep();

        By by = By.cssSelector("input[type='text'][name='username']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement userNameInput = driver.findElement(by);
        userNameInput.clear();
        userNameInput.sendKeys(username);

        by = By.cssSelector("input[type='password'][name='password']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement passwordInput = driver.findElement(by);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        by = By.id("login-button");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement loginButton = driver.findElement(by);
        loginButton.click();
    }   

    @Override
    public void v_UserLoggedIn() {
        Sleep();

        waiter.until(ExpectedConditions.urlContains("open.spotify.com"));

        By by = By.cssSelector("button[data-testid='user-widget-link']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement userWidgetLink = driver.findElement(by);
        Assertions.assertNotNull(userWidgetLink);
    }

    @Override
    public void e_LoginAttemptViaInvalidCredentials() {
        Sleep();

        By by = By.cssSelector("input[type='text'][name='username']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement userNameInput = driver.findElement(by);
        userNameInput.clear();
        userNameInput.sendKeys(username);

        by = By.cssSelector("input[type='password'][name='password']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement passwordInput = driver.findElement(by);
        passwordInput.clear();
        passwordInput.sendKeys(password + "_");

        by = By.id("login-button");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement loginButton = driver.findElement(by);
        loginButton.click();
    }
    
    @Override
    public void v_InvalidCredentialsMessageReturned() {
        Sleep();

        By by = By.cssSelector("span[ng-if='response.error']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement userWidgetLink = driver.findElement(by);
        Assertions.assertNotNull(userWidgetLink);
        Assertions.assertTrue(userWidgetLink.getText().equals("Incorrect username or password.") ||
            userWidgetLink.getText().contains("Oops! Something went wrong"));
    }

    // @Override
    // public void e_ClearInput() {
    //     Sleep();

    //     By by = By.cssSelector("input[type='text'][name='username']");
    //     waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
    //     WebElement userNameInput = driver.findElement(by);
    //     userNameInput.clear();

    //     by = By.cssSelector("input[type='password'][name='password']");
    //     // waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
    //     WebElement passwordInput = driver.findElement(by);
    //     passwordInput.clear();
    // }

    @Override
    public void e_LogOut() {
        Sleep();

        By by = By.cssSelector("button[data-testid='user-widget-link']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement userWidgetLink = driver.findElement(by);
        userWidgetLink.click();

        Sleep();

        by = By.cssSelector("button[data-testid='user-widget-dropdown-logout']");
        // waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement logoutButton = driver.findElement(by);
        logoutButton.click();
    }

    @Test
    public void runSmokeTest() throws IOException {
        Context context = new SpotifyLogin();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new AStarPath(new ReachedVertex("v_BrowserStarted")));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    public void runFunctionalTest() throws IOException {
        Context context = new SpotifyLogin();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new RandomPath(new EdgeCoverage(100)));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    public void runStabilityTest() throws IOException {
        Context context = new SpotifyLogin();
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

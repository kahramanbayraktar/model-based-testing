package com.example.implementations;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.example.interfaces.ISpotifySearch;

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
public class SpotifySearch extends ExecutionContext implements ISpotifySearch {

    private WebDriver driver = null;
    private WebDriverWait waiter = null;

    public final static Path MODEL_PATH = Paths.get("src/main/java/com/example/graph/SpotifySearch.json");

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
        driver.get("https://open.spotify.com");
    }

    @Override
    public void v_SpotifyWebPlayerHomePageIsOpen() {
        Sleep();
        
        waiter.until(ExpectedConditions.urlContains("https://open.spotify.com"));

        By by = By.cssSelector("button[data-testid='login-button']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement loginButton = driver.findElement(by);
        Assertions.assertNotNull(loginButton);
    }

    @Override
    public void e_OpenLoginPage() {
        Sleep();

        By by = By.cssSelector("button[data-testid='login-button']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement loginButton = driver.findElement(by);
        loginButton.click();
    }

    @Override
    public void v_LoginPageIsOpen() {
        Sleep();

        waiter.until(ExpectedConditions.urlContains("accounts.spotify.com"));

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
    public void e_OpenSearchPage() {
        Sleep();

        By by = By.cssSelector("a[href='/search']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchButton = driver.findElement(by);
        searchButton.click();
    }

    @Override
    public void v_SearchPageIsOpen() {
        Sleep();

        waiter.until(ExpectedConditions.urlContains("/search"));

        By by = By.cssSelector("form[role='search']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement searchForm = driver.findElement(by);
        Assertions.assertNotNull(searchForm);

        by = By.cssSelector("input[data-testid='search-input']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchInput = driver.findElement(by);
        Assertions.assertNotNull(searchInput);
    }

    @Override
    public void e_DoSearchWithResults() {
        Sleep();
        
        By by = By.cssSelector("input[data-testid='search-input']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement searchInput = driver.findElement(by);
        searchInput.clear();
        searchInput.sendKeys("Sade");
    }

    @Override
    public void v_SearchResultsReturned() {
        Sleep();

        By by = By.cssSelector("section[data-testid='component-shelf']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement topResultDiv = driver.findElement(by);
        Assertions.assertNotNull(topResultDiv);

        by = By.id("searchPage");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchDiv = driver.findElement(by);
        
        by = By.cssSelector("a[title='Sade']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement resultLink = searchDiv.findElement(by);
        Assertions.assertNotNull(resultLink);
    }

    @Override
    public void e_DoSearchWithoutResults() {
        Sleep();

        By by = By.cssSelector("input[data-testid='search-input']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchInput = driver.findElement(by);
        searchInput.clear();
        searchInput.sendKeys("dgdfhfgjfj");
    }

    @Override
    public void v_SearchResultsNotReturned() {
        Sleep();

        By by = By.cssSelector("div[data-testid='grid-container']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));        
        WebElement gridContainerDiv = driver.findElement(by);
        Assertions.assertNotNull(gridContainerDiv);

        by = By.cssSelector("div");
        WebElement politeDiv = gridContainerDiv.findElement(by);
        Assertions.assertNotNull(politeDiv);

        by = By.cssSelector("h1");
        WebElement noResultH1 = politeDiv.findElement(by);
        Assertions.assertNotNull(noResultH1);
        Assertions.assertNotNull(noResultH1.getText().startsWith("No results found for"));
    }

    @Override
    public void e_ClearSearch() {
        Sleep();
        
        By by = By.cssSelector("input[data-testid='search-input']");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement searchInput = driver.findElement(by);
        searchInput.clear();
    }

    @Test
    public void runSmokeTest() throws IOException {
        Context context = new SpotifySearch();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new AStarPath(new ReachedVertex("v_BrowserStarted")));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    public void runFunctionalTest() throws IOException {
        Context context = new SpotifySearch();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new RandomPath(new EdgeCoverage(100)));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    public void runStabilityTest() throws IOException {
        Context context = new SpotifySearch();
        TestBuilder builder = new TestBuilder().addContext(context, MODEL_PATH, new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)));
        context.setNextElement(context.getModel().findElements("Start").get(0));
        Result result = builder.execute();
        Assertions.assertFalse(result.hasErrors());
    }
    
    private void Sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package org.example.busmanager;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(SeleniumJupiter.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {

    static final Logger log = getLogger(SeleniumTests.class);
    @LocalServerPort
    int port;
    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
        driver.get("http://localhost:" + port);
    }

    @Test
    void selectingDepartureAndArrival() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> webDriver.findElement(By.cssSelector("#input-vaadin-combo-box-22")));
        WebElement departure = driver.findElement(By.cssSelector("#input-vaadin-combo-box-22"));
        departure.click();
        departure.sendKeys("Aveiro");
        departure.sendKeys(Keys.ENTER);
        WebElement arrival = driver.findElement(By.cssSelector("#input-vaadin-combo-box-24"));
        arrival.click();
        arrival.sendKeys("Porto");
        arrival.sendKeys(Keys.ENTER);
        Integer childCount = driver.findElements(By.tagName("vaadin-grid-cell-content")).size();
        driver.findElement(By.cssSelector(".search")).click();
        Integer newChildCount = driver.findElements(By.tagName("vaadin-grid-cell-content")).size();
        assertThat(newChildCount, is(greaterThan(childCount)));
    }

    @Test
    void currencyChanges() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> webDriver.findElement(By.cssSelector("#input-vaadin-combo-box-22")));
        WebElement departure = driver.findElement(By.cssSelector("#input-vaadin-combo-box-22"));
        departure.click();
        departure.sendKeys("Aveiro");
        departure.sendKeys(Keys.ENTER);
        WebElement arrival = driver.findElement(By.cssSelector("#input-vaadin-combo-box-24"));
        arrival.click();
        arrival.sendKeys("Porto");
        arrival.sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector(".search")).click();
        WebElement priceElement = driver.findElement(By.cssSelector(".content > vaadin-grid:nth-child(1) > vaadin-grid-cell-content:nth-child(22)"));
        Double oldPrice = Double.parseDouble(priceElement.getText().replaceAll(",", "."));
        WebElement currencyBox = driver.findElement(By.cssSelector("#input-vaadin-combo-box-26"));
        Actions actions = new Actions(driver)
                .click(currencyBox)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys("EUR")
                .pause(Duration.ofSeconds(1))
                .sendKeys(Keys.ENTER);
        actions.perform();
        TimeUnit.SECONDS.sleep(1);
        Double newPrice = Double.parseDouble(priceElement.getText().replaceAll(",", "."));
        assertThat(newPrice, is(not(equalTo(oldPrice))));
    }

    @Test
    void registration_flow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> webDriver.findElement(By.cssSelector("#input-vaadin-combo-box-22")));
        WebElement departure = driver.findElement(By.cssSelector("#input-vaadin-combo-box-22"));
        departure.click();
        departure.sendKeys("Aveiro");
        departure.sendKeys(Keys.ENTER);
        WebElement arrival = driver.findElement(By.cssSelector("#input-vaadin-combo-box-24"));
        arrival.click();
        arrival.sendKeys("Porto");
        arrival.sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector(".search")).click();
        driver.findElement(By.cssSelector(".content > vaadin-grid:nth-child(1) > vaadin-grid-cell-content:nth-child(22)")).click();
        driver.findElement(By.cssSelector("#input-vaadin-text-field-27"))
                .sendKeys("John Doe");
        driver.findElement(By.cssSelector("#input-vaadin-email-field-28"))
                .sendKeys("asd@example.com");
        driver.findElement(By.cssSelector("#input-vaadin-text-field-29"))
                .sendKeys("123456789");
        driver.findElement(By.cssSelector("#input-vaadin-text-field-30"))
                .sendKeys("1,5");
        driver.findElement(By.cssSelector(".reservation-form > vaadin-horizontal-layout:nth-child(5) > vaadin-button:nth-child(1)")).click();
        WebElement cell = driver.findElement(By.cssSelector("#ROOT-2521314 > vaadin-vertical-layout:nth-child(1) > vaadin-grid:nth-child(1) > vaadin-grid-cell-content:nth-child(29)"));
        try {
            wait.until(webDriver -> cell);
        } catch (Exception e) {
            log.error("Element not found");
        }
    }

}
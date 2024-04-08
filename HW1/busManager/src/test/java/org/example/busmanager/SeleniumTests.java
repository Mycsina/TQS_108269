package org.example.busmanager;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@Disabled
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
        Select departure = new Select(driver.findElement(By.cssSelector("#input-vaadin-combo-box-22")));
        departure.selectByVisibleText("Aveiro");
        Select arrival = new Select(driver.findElement(By.cssSelector("#input-vaadin-combo-box-24")));
        arrival.selectByVisibleText("Porto");
        Integer childCount = driver.findElements(By.tagName("vaadin-grid-cell-content")).size();
        driver.findElement(By.cssSelector(".vaadin-button-container")).click();
        Integer newChildCount = driver.findElements(By.tagName("vaadin-grid-cell-content")).size();
        assertThat(newChildCount).isGreaterThan(childCount);
    }

}
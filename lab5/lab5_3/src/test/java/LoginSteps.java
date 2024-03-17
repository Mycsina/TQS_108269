import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.NoSuchElementException;

public class LoginSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @And("I select {string} as departure and {string} as destination")
    public void iSelect(String departure, String destination) {
        Select dropdown = new Select(driver.findElement(By.name("fromPort")));
        dropdown.selectByVisibleText(departure);
        dropdown = new Select(driver.findElement(By.name("toPort")));
        dropdown.selectByVisibleText(destination);

    }

    @And("I click Find Flights")
    public void iPressEnter() {
        driver.findElement(By.cssSelector("input.btn")).click();
    }

    @And("I select the first flight")
    public void iSelectFirstFlight() {
        driver.findElement(By.cssSelector(".table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2) > input:nth-child(1)")).click();
    }

    @And("I type in my information")
    public void iTypeInMyInformation() {
        driver.findElement(By.name("inputName")).sendKeys("John Doe");
        driver.findElement(By.name("address")).sendKeys("123 Main St");
        driver.findElement(By.name("city")).sendKeys("Anytown");
        driver.findElement(By.name("state")).sendKeys("AnyState");
        driver.findElement(By.name("zipCode")).sendKeys("12345");
        driver.findElement(By.name("creditCardNumber")).sendKeys("1234567890");
        driver.findElement(By.name("nameOnCard")).sendKeys("John Doe");
    }

    @And("I click Purchase Flight")
    public void iClickPurchaseFlight() {
        driver.findElement(By.cssSelector("input.btn")).click();
    }

    @Then("I should be see the page title {string}")
    public void iShouldSee(String result) {
        try {
            boolean res = driver.getTitle().equals(result);
            if (!res) {
                throw new AssertionError(
                        "Expected: " + result + " but got: " + driver.findElement(By.cssSelector("head > title:nth-child(2)")).getText());
            }
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
        } finally {
            driver.quit();
        }
    }

}
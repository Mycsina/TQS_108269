import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FactoryLoginPage extends ExtendedBasePage {

    @FindBy(id = "username")
    @CacheLookup
    WebElement usernameInput;

    @FindBy(id = "password")
    @CacheLookup
    WebElement passwordInput;

    @FindBy(css = "button")
    @CacheLookup
    WebElement submitButton;

    @FindBy(id = "success")
    @CacheLookup
    WebElement successBox;

    public FactoryLoginPage(String browser, int timeoutSec) {
        this(browser);
        setTimeoutSec(timeoutSec);
    }

    public FactoryLoginPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }


    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    public String getSuccessMessage() {
        return successBox.getText();
    }

    public void close() {
        driver.quit();
    }

}
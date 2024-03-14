import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.platform.commons.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.String.format;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.platform.commons.logging.LoggerFactory.getLogger;

public class ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    WebDriverWait wait;
    int timeoutSec = 5; // wait timeout (5 seconds by default)

    public ExtendedBasePage(String browser) {
        driver = WebDriverManager.getInstance(browser).create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Rest of common methods: quit(), visit(URL url), find(By element), etc.


    public void visit(String url) {
        log.info(() -> format("Visiting URL %s", url));
        driver.get(url);
    }

    public WebElement find(By element) {
        log.info(() -> format("Finding element %s", element));
        return driver.findElement(element);
    }


}
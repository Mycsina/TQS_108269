import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.function.Supplier;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.logging.LoggerFactory.getLogger;

@ExtendWith(SeleniumJupiter.class)
public class HelloWorldFirefoxSelJupTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(FirefoxDriver driver) {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        String print = "The title of " + sutUrl + " is " + title;
        Supplier<String> supplier = () -> print;
        log.debug(supplier);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

}
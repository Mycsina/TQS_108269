import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;

public class TestsUnit {

    String url = "https://jsonplaceholder.typicode.com/todos";

    @Test
    public void endpointIsAvailable() {
            get(url).then().statusCode(200);
    }

    @Test
    public void correctDataIsReturnedOn4th() {
            get(url).then().body("title[3]", Matchers.equalTo("et porro tempora"));
    }

    @Test
    public void id198And199AreAvailable() {
            get(url).then().body("id", Matchers.hasItems(198, 199));
    }

    @Test
    public void under2Seconds() {
            long start = System.currentTimeMillis();
            get(url);
            long end = System.currentTimeMillis();
            long duration = end - start;
            assert duration < 2000;
    }
}

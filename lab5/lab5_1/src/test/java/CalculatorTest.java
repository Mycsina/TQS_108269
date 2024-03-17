import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

import java.util.List;
import java.util.stream.Stream;

@Suite
@IncludeEngines("cucumber")
public class CalculatorTest {

    @TestFactory
    public Stream<DynamicTest> runCucumberTests(Stream<DynamicTest> scenarios) {
        List<DynamicTest> tests = scenarios.toList();
        return tests.stream();
    }
}

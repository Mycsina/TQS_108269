import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSteps {
    private Calculator calculator;

    @Given("a calculator I just turned on")
    public void a_calculator_I_just_turned_on() {
        calculator = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        calculator.push(arg1);
        calculator.push(arg2);
        calculator.push("+");
    }

    @When("I subtract {int} to {int}")
    public void subtract(int arg1, int arg2) {
        calculator.push(arg1);
        calculator.push(arg2);
        calculator.push("-");
    }

    @When("I multiply {int} and {int}")
    public void multiply(int arg1, int arg2) {
        calculator.push(arg1);
        calculator.push(arg2);
        calculator.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int arg1, int arg2) {
        calculator.push(arg1);
        calculator.push(arg2);
        calculator.push("/");
    }

    @Then("the result is {int}")
    public void the_result_is(double expected) {
        assertEquals(expected, calculator.value());
    }
}

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "Steps",
        plugin = {"pretty", "json:target/runner.json", "junit:target/junit.xml"}
)
public class Runner { }

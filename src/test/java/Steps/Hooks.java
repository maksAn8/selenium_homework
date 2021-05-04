package Steps;

import io.cucumber.java.After;
import utils.DriverSingleton;

public class Hooks {
    @After
    public void afterScenario() {
        DriverSingleton.quit();
    }
}

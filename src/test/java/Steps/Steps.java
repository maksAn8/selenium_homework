package Steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverSingleton;

public class Steps {
    protected final Logger log = LogManager.getLogger(this.getClass());
    protected final WebDriverWait wait = new WebDriverWait(DriverSingleton.getDriver(), 20);
}

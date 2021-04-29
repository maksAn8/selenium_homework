package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    private Properties property;
    private InputStream is;

    public Property() {
        property = new Properties();
        is = Property.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            property.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getChromeDriver() {
        return property.getProperty("driver.chrome");
    }

    public String getAppMainPageUrl() {
        return property.getProperty("app.mainPageUrl");
    }
}

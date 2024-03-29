package DemoqaTests.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;

    static {
        try {
            fileInputStream = new FileInputStream("src/test/java/DemoqaTests/Config/conf.properties");
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                if (fileInputStream != null) try {
                    fileInputStream.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
    }

    public static String GetProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}

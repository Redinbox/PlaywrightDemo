package config;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

public class PropertyUtil {
    public static void updateConfigurationProperty(String propertyName, String propertyValue) {
        // Update the specified property in MyConfiguration
        ProjectConfig configuration = ConfigCache.getOrCreate(ProjectConfig.class);
    }
}

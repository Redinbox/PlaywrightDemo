package config;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

public final class ConfigurationManager {

    private ConfigurationManager() {}

    public static ProjectConfig config() {
        return ConfigCache.getOrCreate(ProjectConfig.class);
    }
}

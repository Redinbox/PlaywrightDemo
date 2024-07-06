package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(Config.LoadType.MERGE)
@Sources({"system:properties", "classpath:general.properties", "classpath:allure.properties"})
@Config.HotReload
public interface ProjectConfig extends Config {

    @Key("browser")
    @DefaultValue("chromium")
    String browser();

    @Key("base.url")
    String baseUrl();

    @Key("item.url")
    String itemUrl();

    @Key("headless")
    @DefaultValue("true")
    boolean isHeadless();

    @Key("slow.motion")
    int slowMotion();

    @Key("browser.timeout")
    @DefaultValue("30")
    int timeout();

    @Key("videoRecord")
    boolean isVideoRecord();

    @Key("enable.trace")
    boolean enableTrace();

    @Key("base.test.data.path")
    String baseTestDataPath();

    @Key("allure.results.directory")
    String allureResultsDir();

    @Key("proxymode")
    boolean getProxyMode();

    @Key("stg.proxy")
    String stgProxy();

    @Key("stg.bypass")
    String stgByPass();

    @Key("environment")
    String environment();

    @Key("username")
    String getUserName();

    @Key("password")
    String getPassword();

}

package config;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;
import java.util.Objects;

public class WebDriverProvider {
    private static WebDriverConfig config;

    public static void configure() {
        getConfig();
        Configuration.browser = config.browserName().toString();
        Configuration.browserVersion = config.browserVersion();
        Configuration.browserSize = config.browserSize();
        Configuration.baseUrl = config.baseUrl();
        Configuration.timeout = config.timeout();

        if (config.isRemote()) {
            Configuration.remote = getRemoteUrl();
        }

        Configuration.browserCapabilities = getCapabilities();
    }

    private static String getRemoteUrl() {
        var url = config.remoteUrl();
        if (Objects.nonNull(config.remoteUsername()) && Objects.nonNull(config.remotePassword())) {
            url = url.replace("://", "://" + config.remoteUsername() + ":" + config.remotePassword() + "@");
        }
        return url;
    }

    private static DesiredCapabilities getCapabilities() {
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", Configuration.browser);
        capabilities.setCapability("browserVersion", Configuration.browserVersion);

        if (config.isRemote()) {
            capabilities.setCapability(
                    "selenoid:options",
                    Map.of(
                            "enableVNC", config.enableVnc(),
                            "enableVideo", config.enableVideo()
                    )
            );
        }

        return capabilities;
    }

    public static WebDriverConfig getConfig() {
        if (Objects.isNull(config)) {
            config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
        }
        return config;
    }
}

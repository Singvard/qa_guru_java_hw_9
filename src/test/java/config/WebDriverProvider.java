package config;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;
import java.util.Objects;

public class WebDriverProvider {
    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    public WebDriverConfig getConfig() {
        return config;
    }

    public void configure() {
        Configuration.browser = config.browserName().toString();

        var browserVersion = config.browserVersion();
        if (Objects.nonNull(browserVersion)) {
            Configuration.browserVersion = config.browserVersion();
        }

        Configuration.browserSize = config.browserSize();
        Configuration.baseUrl = config.baseUrl();
        Configuration.timeout = config.timeout();

        if (config.isRemote()) {
            Configuration.remote = getRemoteUrl();
            Configuration.browserCapabilities = getCapabilities();
        }


    }

    private String getRemoteUrl() {
        var url = config.remoteUrl();
        if (Objects.nonNull(config.remoteUsername()) && Objects.nonNull(config.remotePassword())) {
            url = url.replace("://", "://" + config.remoteUsername() + ":" + config.remotePassword() + "@");
        }
        return url;
    }

    private DesiredCapabilities getCapabilities() {
        var capabilities = new DesiredCapabilities();

        capabilities.setCapability(
                "selenoid:options",
                Map.of(
                        "enableVNC", config.enableVnc(),
                        "enableVideo", config.enableVideo()
                )
        );

        return capabilities;
    }
}

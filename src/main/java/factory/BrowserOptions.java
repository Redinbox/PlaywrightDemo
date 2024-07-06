package factory;

import lombok.Data;

@Data
public class BrowserOptions {
    private String browserName;
    private boolean headless;
    private int timeout;
}
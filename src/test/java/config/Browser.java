package config;

public enum Browser {
    FIREFOX,
    CHROME,
    EDGE,
    OPERA,
    SAFARI;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

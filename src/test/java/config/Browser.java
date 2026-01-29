package config;

public enum Browser {
    FIREFOX,
    CHROME,
    EDGE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

package fr.diginamic.qualiair.utils;

@Deprecated
public class UriBuilder {
    private String uri;

    public UriBuilder(String uri) {
        this.uri = uri;
    }

    public UriBuilder pathVariable(String value) {
        uri = uri + value;
        return this;
    }

    public UriBuilder requestParam(String key, String value) {
        uri = uri + "?" + key + "=" + value;
        return this;
    }

    public String build() {
        return uri;
    }
}

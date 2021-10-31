package net.pvytykac.admin.db;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class Preferences {

    private final String locale;
    private final String timezone;

    public Preferences(String locale, String timezone) {
        this.locale = locale;
        this.timezone = timezone;
    }

    public String getLocale() {
        return locale;
    }

    public String getTimezone() {
        return timezone;
    }
}

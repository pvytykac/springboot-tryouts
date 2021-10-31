package net.pvytykac.admin.controller.admin;

/**
 * @author Paly
 * @since 2021-10-30
 */
public class PreferencesDto {

    private final String locale;
    private final String timezone;

    public PreferencesDto(String locale, String timezone) {
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

package util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public enum Logging {;

    private static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").
            withLocale(Locale.getDefault()).withZone(ZoneOffset.UTC);
    public static void log(final String message) {
        final String date = YYYY_MM_DD_HH_MM_SS.format(Instant.now());
        System.out.printf("[%s] [INFO] %s%n", date, message);
    }

}

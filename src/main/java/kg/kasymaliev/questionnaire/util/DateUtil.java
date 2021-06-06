package kg.kasymaliev.questionnaire.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class DateUtil {
    public static LocalDateTime stringToLocalDateTime(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getDefault());
        try {
            return formatter.parse(date).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (ParseException | NullPointerException e) {
            log.error("#stringToLocalDateTime: [{}]", e.toString());
            return null;
        }
    }
}

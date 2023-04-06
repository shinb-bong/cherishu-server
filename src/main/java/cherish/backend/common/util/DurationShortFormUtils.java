package cherish.backend.common.util;

import cherish.backend.common.exception.DurationFormatException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DurationShortFormUtils {
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;

    /**
     * 기간을 나타내는 약어를 millisecond로 변환
     *
     * <p>
     *     <code>s</code> represents a second.<br>
     *     <code>30s</code> represents 30 seconds.<br>
     * </p>
     * <p>
     *     <code>m</code> represents a minute.<br>
     * </p>
     * <p>
     *     <code>h</code> represents an hour.<br>
     * </p>
     * <p>
     *     <code>d</code> represents a day.<br>
     * </p>
     *
     * @param shortForm a shortened form of duration e.g. 30s, 1m, 5d, etc.
     * @return milliseconds as long
     */
    public static long convertShortFormToMillis(String shortForm) throws DurationFormatException {
        try {
            if (shortForm.contains("s")) {
                return Integer.parseInt(shortForm.substring(0, shortForm.indexOf("s"))) * SECOND;
            }
            if (shortForm.contains("m")) {
                return Integer.parseInt(shortForm.substring(0, shortForm.indexOf("m"))) * MINUTE;
            }
            if (shortForm.contains("h")) {
                return Integer.parseInt(shortForm.substring(0, shortForm.indexOf("h"))) * HOUR;
            }
            if (shortForm.contains("d")) {
                return Integer.parseInt(shortForm.substring(0, shortForm.indexOf("d"))) * DAY;
            }
        } catch (NumberFormatException | ArithmeticException e) {
            log.error(e.getMessage(), e);
        }
        throw new DurationFormatException();
    }
}

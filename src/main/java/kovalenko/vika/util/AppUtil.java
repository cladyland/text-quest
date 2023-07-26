package kovalenko.vika.util;

import org.slf4j.Logger;

import static java.util.Objects.isNull;

public class AppUtil {
    private static final String CANNOT_BE_NULL = " cannot be null";

    private AppUtil() {
    }

    public static void ifNullAddLogAndThrowException(Object target, String targetName, Logger log, String logs) {
        if (isNull(target)) {
            log.error(logs);
            throw new NullPointerException(targetName + CANNOT_BE_NULL);
        }
    }
}

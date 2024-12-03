package com.s2f.s2fapi.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe utilitaire pour la gestion des logs.
 */
@Slf4j
public class LoggingUtil {

    /**
     * Logs an informational message.
     *
     * @param clazz      the class where the log is generated
     * @param methodName the method name
     * @param message    the log message
     */
    public static void logInfo(Class<?> clazz, String methodName, String message) {
        log.info("[{}][{}] {}", clazz.getSimpleName(), methodName, message);
    }

    /**
     * Logs an error message.
     *
     * @param clazz      the class where the log is generated
     * @param methodName the method name
     * @param message    the log message
     */
    public static void logError(Class<?> clazz, String methodName, String message) {
        log.error("[{}][{}] {}", clazz.getSimpleName(), methodName, message);
    }
}

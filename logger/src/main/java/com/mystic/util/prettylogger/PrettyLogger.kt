package com.mystic.util.prettylogger

import com.mystic.util.prettylogger.adapter.LogAdapter
import com.mystic.util.prettylogger.printer.LoggerPrinter
import com.mystic.util.prettylogger.printer.Printer
import com.mystic.util.prettylogger.strategy.format.FormatStrategy
import com.mystic.util.prettylogger.strategy.log.LogStrategy

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:34 AM
 */

/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ LOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 *
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   Logger.addLogAdapter(new AndroidLogAdapter());
 * </code></pre>
 *
 * And use the appropriate static Logger methods.
 *
 * <pre><code>
 *   Logger.d("debug");
 *   Logger.e("error");
 *   Logger.w("warning");
 *   Logger.v("verbose");
 *   Logger.i("information");
 *   Logger.wtf("What a Terrible Failure");
 * </code></pre>
 *
 * <h3>String format arguments are supported</h3>
 * <pre><code>
 *   Logger.d("hello %s", "world");
 * </code></pre>
 *
 * <h3>Collections are support ed(only available for debug logs)</h3>
 * <pre><code>
 *   Logger.d(MAP);
 *   Logger.d(SET);
 *   Logger.d(LIST);
 *   Logger.d(ARRAY);
 * </code></pre>
 *
 * <h3>Json and Xml support (output will be in debug level)</h3>
 * <pre><code>
 *   Logger.json(JSON_CONTENT);
 *   Logger.xml(XML_CONTENT);
 * </code></pre>
 *
 * <h3>Customize Logger</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 *   <li>Different {@link LogAdapter}</li>
 *   <li>Different {@link FormatStrategy}</li>
 *   <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see LogAdapter
 * @see FormatStrategy
 * @see LogStrategy
 */
interface PrettyLogger {

    /**
     * Returns the logger tag for the implementing class.
     */
    val loggerTag: String
        get() = getTag(javaClass)
}

/**
 * Singleton object implementing PrettyLogger.
 * Uses LoggerPrinter as the default Printer.
 */
object MysticLogger: PrettyLogger {
    var printer: Printer = LoggerPrinter()
}

/**
 * Extension function to get the current Printer instance.
 */
fun PrettyLogger.getPrinter(): Printer {
    return MysticLogger.printer
}

/**
 * Extension function to set a new Printer instance.
 */
fun PrettyLogger.setPrinter(printer: Printer) {
    MysticLogger.printer = printer
}

/**
 * Extension function to add a LogAdapter to the Printer.
 */
fun PrettyLogger.addAdapter(adapter: LogAdapter) {
    MysticLogger.printer.addAdapter(adapter)
}

/**
 * Extension function to clear all LogAdapters from the Printer.
 */
fun PrettyLogger.clearAdapters() {
    MysticLogger.printer.clearLogAdapters()
}

/**
 * Extension function to set a tag for the next log.
 * This tag is used once only.
 */
fun PrettyLogger.tag(tag: String?): Printer {
    return MysticLogger.printer.tag(tag)
}
/**
 * Extension function to log a message with a specific priority, tag, and optional throwable.
 */
fun PrettyLogger.log(priority: Int, tag: String?, message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.log(priority, tag, message, throwable)
}

/**
 * Extension function to log a debug message with an optional throwable.
 */
fun PrettyLogger.debug(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.debug(message, throwable)
}

/**
 * Extension function to log a verbose message.
 */
fun PrettyLogger.verbose(message: Any?) {
    MysticLogger.printer.verbose(message)
}

/**
 * Extension function to log an info message.
 */
fun PrettyLogger.info(message: Any?) {
    MysticLogger.printer.info(message)
}

/**
 * Extension function to log a warning message with an optional throwable.
 */
fun PrettyLogger.warn(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.warn(message, throwable)
}

/**
 * Extension function to log an error message with an optional throwable.
 */
fun PrettyLogger.error(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.error(message, throwable)
}

/**
 * Extension function to log a WTF (What a Terrible Failure) message with an optional throwable.
 */
fun PrettyLogger.wtf(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.wtf(message, throwable)
}

/**
 * Extension function to log a JSON string in debug priority.
 */
fun PrettyLogger.debugJson(json: String?) {
    MysticLogger.printer.debugJson(json)
}

/**
 * Extension function to log an XML string in debug priority.
 */
fun PrettyLogger.debugXml(xml: String?) {
    MysticLogger.printer.debugXml(xml)
}

/**
 * Extension function to log a verbose message using a lambda.
 */
fun PrettyLogger.verbose(message: () -> Any?) {
    MysticLogger.printer.verbose(message)
}

/**
 * Extension function to log a debug message using a lambda and an optional throwable.
 */
fun PrettyLogger.debug(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.debug(throwable, message)
}

/**
 * Extension function to log an info message using a lambda.
 */
fun PrettyLogger.info(message: () -> Any?) {
    MysticLogger.printer.info(message)
}

/**
 * Extension function to log a warning message using a lambda and an optional throwable.
 */
fun PrettyLogger.warn(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.warn(throwable, message)
}

/**
 * Extension function to log an error message using a lambda and an optional throwable.
 */
fun PrettyLogger.error(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.error(throwable, message)
}

/**
 * Tip: Use this for exceptional situations to log
 * ie: Unexpected errors etc
 */
/**
 * Extension function to log a WTF (What a Terrible Failure) message using a lambda and an optional throwable.
 * Tip: Use this for exceptional situations to log, e.g., unexpected errors.
 */
fun PrettyLogger.wtf(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.wtf(throwable, message)
}

/**
 * Formats the given json content and print it only in debug priority
 */
/**
 * Extension function to log a JSON string using a lambda in debug priority.
 */
fun PrettyLogger.debugJson(json: () -> String?) {
    MysticLogger.printer.debugJson(json)
}

/**
 * Formats the given xml content and print it only in debug priority
 */
/**
 * Extension function to log an XML string using a lambda in debug priority.
 */
fun PrettyLogger.debugXml(xml: () -> String?) {
    MysticLogger.printer.debugXml(xml)
}

/**
 * Helper function to get a tag for the given class.
 * Ensures the tag length does not exceed 23 characters.
 */
private fun getTag(jClass: Class<*>): String {
    val tag = jClass.simpleName
    return if (tag.length <= 23) tag else tag.substring(0, 23)
}
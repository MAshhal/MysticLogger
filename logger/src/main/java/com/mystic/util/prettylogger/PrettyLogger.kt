package com.mystic.util.prettylogger

import com.mystic.util.prettylogger.adapter.LogAdapter
import com.mystic.util.prettylogger.printer.LoggerPrinter
import com.mystic.util.prettylogger.printer.Printer

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:34 AM
 */

interface PrettyLogger {

    val loggerTag: String
        get() = getTag(javaClass)
}

object MysticLogger: PrettyLogger {
    var printer: Printer = LoggerPrinter()
}

fun PrettyLogger.getPrinter(): Printer {
    return MysticLogger.printer
}

fun PrettyLogger.setPrinter(printer: Printer) {
    MysticLogger.printer = printer
}

fun PrettyLogger.addAdapter(adapter: LogAdapter) {
    MysticLogger.printer.addAdapter(adapter)
}

fun PrettyLogger.clearAdapters() {
    MysticLogger.printer.clearLogAdapters()
}

/**
 * The tag for the coming log.
 * Note: this tag is used once only. For every log tag, you should config
 */
fun PrettyLogger.tag(tag: String?): Printer {
    return MysticLogger.printer.tag(tag)
}

fun PrettyLogger.log(priority: Int, tag: String?, message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.log(priority, tag, message, throwable)
}

fun PrettyLogger.debug(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.debug(message, throwable)
}

fun PrettyLogger.verbose(message: Any?) {
    MysticLogger.printer.verbose(message)
}

fun PrettyLogger.info(message: Any?) {
    MysticLogger.printer.info(message)
}

fun PrettyLogger.warn(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.warn(message, throwable)
}

fun PrettyLogger.error(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.error(message, throwable)
}

fun PrettyLogger.wtf(message: Any?, throwable: Throwable? = null) {
    MysticLogger.printer.wtf(message, throwable)
}

fun PrettyLogger.debugJson(json: String?) {
    MysticLogger.printer.debugJson(json)
}

fun PrettyLogger.debugXml(xml: String?) {
    MysticLogger.printer.debugXml(xml)
}

fun PrettyLogger.verbose(message: () -> Any?) {
    MysticLogger.printer.verbose(message)
}

fun PrettyLogger.debug(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.debug(throwable, message)
}

fun PrettyLogger.info(message: () -> Any?) {
    MysticLogger.printer.info(message)
}

fun PrettyLogger.warn(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.warn(throwable, message)
}

fun PrettyLogger.error(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.error(throwable, message)
}

fun PrettyLogger.wtf(throwable: Throwable? = null, message: () -> Any?) {
    MysticLogger.printer.wtf(throwable, message)
}

fun PrettyLogger.debugJson(json: () -> String?) {
    MysticLogger.printer.debugJson(json)
}

fun PrettyLogger.debugXml(xml: () -> String?) {
    MysticLogger.printer.debugXml(xml)
}

private fun getTag(jClass: Class<*>): String {
    val tag = jClass.simpleName
    return if (tag.length <= 23) tag else tag.substring(0, 23)
}
package com.mystic.util.prettylogger.util

import android.util.Printer
import com.mystic.util.prettylogger.PrettyLogger
import com.mystic.util.prettylogger.printer.LoggerPrinter
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:49 AM
 */

/**
 * Provides convenient methods to some common operations
 */
object LoggerUtils {
    private const val MIN_STACK_OFFSET = 5

    /**
     * Copied from "android.util.Log.getStackTraceString()" in order to avoid usage of Android stack
     * in unit tests.
     *
     * @return Stack trace in form of String
     */
    fun getStackTraceString(throwable: Throwable?): String {
        if (throwable == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var tr = throwable
        while (tr != null) {
            if (tr is UnknownHostException) {
                return ""
            }
            tr = tr.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    fun logLevel(priority: Int): String = when (priority) {
        android.util.Log.VERBOSE -> "VERBOSE"
        android.util.Log.DEBUG -> "DEBUG"
        android.util.Log.INFO -> "INFO"
        android.util.Log.WARN -> "WARN"
        android.util.Log.ERROR -> "ERROR"
        android.util.Log.ASSERT -> "ASSERT"
        else -> "UNKNOWN"
    }

    fun toString(obj: Any?): String {
        if (obj == null) return "null"
        if (!obj.javaClass.isArray) return obj.toString()
        if (obj is IntArray) return obj.contentToString()
        if (obj is LongArray) return obj.contentToString()
        if (obj is DoubleArray) return obj.contentToString()
        if (obj is FloatArray) return obj.contentToString()
        if (obj is BooleanArray) return obj.contentToString()
        if (obj is ByteArray) return obj.contentToString()
        if (obj is CharArray) return obj.contentToString()
        if (obj is ShortArray) return obj.contentToString()

        return if (obj is Array<*>) {
            obj.contentDeepToString()
        } else "Couldn't find a correct type for the array $obj"
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        val printerName = Printer::class.java.name
        val loggerPrinterName = LoggerPrinter::class.java.name
        val beautyLoggerName = PrettyLogger::class.java.name
        while (i < trace.size) {
            val e = trace[i]
            val name = e.className
            if (!name.startsWith(loggerPrinterName) &&
                !name.startsWith(printerName) &&
                !name.startsWith(beautyLoggerName)
            ) {
                return --i
            }
            i++
        }
        return -1
    }

    fun formatTag(defaultTag: String, passedInTag: String?): String {
        return if (!passedInTag.isNullOrEmpty() && defaultTag != passedInTag) {
            "$defaultTag-$passedInTag"
        } else defaultTag
    }

    fun getSimpleClassName(name: String): String {
        val lastIndex = name.lastIndexOf(".")
        return name.substring(lastIndex + 1)
    }

}
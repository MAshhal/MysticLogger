package com.mystic.util.prettylogger.strategy.format

import android.content.Context
import android.os.Environment
import android.os.HandlerThread
import com.mystic.util.prettylogger.strategy.log.DiskLogStrategy
import com.mystic.util.prettylogger.strategy.log.LogStrategy
import com.mystic.util.prettylogger.util.LoggerUtils
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:46 AM
 */

inline fun csvFormatStrategyBuilder(directoryPath: String, block: CsvFormatStrategy.Builder.() -> Unit): CsvFormatStrategy {
    return CsvFormatStrategy.Builder().apply(block).build(directoryPath)
}

/**
 *
 */
inline fun Context.csvFormatStrategyBuilder(block: CsvFormatStrategy.Builder.() -> Unit): CsvFormatStrategy {
    return csvFormatStrategyBuilder(filesDir.absolutePath, block)
}

class CsvFormatStrategy(
    private val methodCount: Int,
    private val methodOffset: Int,
    private var date: Date,
    private val dateFormat: SimpleDateFormat,
    private val logStrategy: LogStrategy,
    private val tag: String
) : FormatStrategy {

    private constructor(builder: Builder) : this(
        builder.methodCount,
        builder.methodOffset,
        Date(),
        builder.dateFormat!!,
        builder.logStrategy!!,
        builder.tag
    )

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun log(priority: Int, onceOnlyTag: String?, message: String) {
        val tag = LoggerUtils.formatTag(tag, onceOnlyTag)

        date.time = System.currentTimeMillis()

        val content = buildString {
            // Machine-readable date/time
            append(date.time)

            // Human-readable date/time
            append(SEPARATOR)
            append(dateFormat.format(date))

            // level
            append(SEPARATOR)
            append(LoggerUtils.logLevel(priority))

            // tag
            append(SEPARATOR)
            append(tag)

            // message
            append(SEPARATOR)
            if (message.contains(DOUBLE_QUOTES) || message.contains(SEPARATOR) || message.contains(NEW_LINE)) {
                append("$DOUBLE_QUOTES${message.replace(DOUBLE_QUOTES, APOSTROPHE)}$DOUBLE_QUOTES")
            } else {
                append(message)
            }

            // code location
            append(SEPARATOR)
            var myMethodCount = methodCount
            val trace = Thread.currentThread().stackTrace
            var level = ""
            val stackOffset = LoggerUtils.getStackOffset(trace) + methodOffset

            //corresponding method count with the current stack may exceeds the stack trace. Trims the count
            if (myMethodCount + stackOffset > trace.size) {
                myMethodCount = trace.size - stackOffset - 1
            }

            append(DOUBLE_QUOTES)
            for (i in myMethodCount downTo 1) {
                val stackIndex = i + stackOffset
                if (stackIndex >= trace.size) {
                    continue
                }
                append(level)
                append(LoggerUtils.getSimpleClassName(trace[stackIndex].className))
                append(".")
                append(trace[stackIndex].methodName)
                append(" ")
                append(" (")
                append(trace[stackIndex].fileName)
                append(":")
                append(trace[stackIndex].lineNumber)
                append(")")
                append(NEW_LINE)
                level += "   "
            }
            append(DOUBLE_QUOTES)

            // new line
            append(NEW_LINE)
        }

        logStrategy.log(priority, tag, content)
    }

    class Builder {
        var methodCount = 2
        var methodOffset = 0
        var dateFormat: SimpleDateFormat? = null
//        var dateFormat: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        var logStrategy: LogStrategy? = null
        var tag = "MYSTIC_LOGGER"


        fun build(directoryPath: String): CsvFormatStrategy {
            if (dateFormat == null) {
                dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
            }

            if (logStrategy == null) {
                val logDirectoryPath = directoryPath + File.separator + "MysticLogger"

                val thread = HandlerThread("AndroidFileLogger.$logDirectoryPath")
                thread.start()
                val handler = DiskLogStrategy.WriteHandler(thread.looper, logDirectoryPath, MAX_BYTES)
                logStrategy = DiskLogStrategy(handler)
            }

            return CsvFormatStrategy(this)
        }
    }

    companion object {
        private const val MAX_BYTES = 500 * 1024 // 500K averages to a 4000 lines per file

        private val NEW_LINE = System.lineSeparator()
        private const val DOUBLE_QUOTES = "\""
        private const val APOSTROPHE = "'"
        private const val SEPARATOR = ","
    }

}

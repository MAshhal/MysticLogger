package com.mystic.util.prettylogger.printer

import android.util.Log.*
import com.mystic.util.prettylogger.adapter.LogAdapter
import com.mystic.util.prettylogger.util.LoggerUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 10:15 AM
 */

class LoggerPrinter: Printer {

    companion object {
        /**
         * It is used for json pretty print
         */
        private const val JSON_INDENT = 2
    }

    override fun debug(message: Any?, vararg args: Any?) {
        log(DEBUG, null, message, *args)
    }

    override fun debug(message: Any?, throwable: Throwable?) {
        log(DEBUG, null, message, throwable)
    }

    override fun debug(throwable: Throwable?, message: () -> Any?) {
        log(DEBUG, null, throwable, message)
    }

    override fun error(message: Any?, vararg args: Any?) {
        log(ERROR, null, message, *args)
    }

    override fun error(message: Any?, throwable: Throwable?) {
        log(ERROR, null, message, throwable)
    }

    override fun error(throwable: Throwable?, message: () -> Any?) {
        log(ERROR, null, throwable, message)
    }

    override fun warn(message: Any?, vararg args: Any?) {
        log(WARN, null, message, *args)
    }

    override fun warn(message: Any?, throwable: Throwable?) {
        log(WARN, null, message, throwable)
    }

    override fun warn(throwable: Throwable?, message: () -> Any?) {
        log(WARN, null, throwable, message)
    }

    override fun info(message: Any?, vararg args: Any?) {
        log(INFO, null, message, *args)
    }

    override fun info(message: Any?) {
        log(INFO, null, LoggerUtils.toString(message))
    }

    override fun info(message: () -> Any?) {
        log(INFO, null, null, message)
    }

    override fun verbose(message: Any?, vararg args: Any?) {
        log(VERBOSE, null, message, *args)
    }

    override fun verbose(message: Any?) {
        log(VERBOSE, null, message)
    }

    override fun verbose(message: () -> Any?) {
        log(VERBOSE, null, null, message)
    }

    override fun wtf(message: Any?, vararg args: Any?) {
        log(ASSERT, null, message, *args)
    }

    override fun wtf(message: Any?, throwable: Throwable?) {
        log(ASSERT, null, message, throwable)
    }

    override fun wtf(throwable: Throwable?, message: () -> Any?) {
        log(ASSERT, null, throwable, message)
    }

    private val localTag = ThreadLocal<String>()

    private val logAdapters = ArrayList<LogAdapter>()

    override fun debugJson(json: String?) {
        if (!isLoggable(DEBUG)) return
        if (json.isNullOrEmpty()) {
            debug("Empty/Null json content")
            return
        }
        try {
            val myJson = json.trim { it <= ' ' }
            if (myJson.startsWith("{")) {
                val jsonObject = JSONObject(myJson)
                val message = jsonObject.toString(JSON_INDENT)
                debug(message)
                return
            }
            if (myJson.startsWith("[")) {
                val jsonArray = JSONArray(myJson)
                val message = jsonArray.toString(JSON_INDENT)
                debug(message)
                return
            }
            error("Invalid Json:$json")
        } catch (e: JSONException) {
            error("Invalid Json:$json")
        }
    }

    override fun debugJson(json: () -> String?) {
        if (!isLoggable(DEBUG)) {
            return
        }
        debugJson(json())
    }

    override fun debugXml(xml: String?) {
        if (!isLoggable(DEBUG)) {
            return
        }
        if (xml.isNullOrEmpty()) {
            debug("Empty/Null xml content")
            return
        }
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            with(transformer) {
                setOutputProperty(OutputKeys.INDENT, "yes")
                setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transform(xmlInput, xmlOutput)
            }
            debug(xmlOutput.writer.toString().replaceFirst(">", ">\n").trim())
        } catch (e: TransformerException) {
            error("Invalid xml")
        }
    }

    override fun debugXml(xml: () -> String?) {
        if (!isLoggable(DEBUG)) {
            return
        }
        debugXml(xml())
    }

    @Synchronized override fun log(priority: Int, tag: String?, message: Any?, throwable: Throwable?) {
        var msg: String
        val usingTag = tag ?: getTag()
        logAdapters
            .filter { adapter -> adapter.isLoggable(priority, usingTag) }
            .apply { msg = composeMessage(message, throwable) }
            .forEach { adapter -> adapter.log(priority, usingTag, msg) }
    }

    @Synchronized override fun log(priority: Int, tag: String?, throwable: Throwable?, message: () -> Any?) {
        var msg: String
        val usingTag = tag ?: getTag()
        logAdapters
            .filter { adapter -> adapter.isLoggable(priority, usingTag) }
            .apply { msg = composeMessage(message, throwable) }
            .forEach { adapter -> adapter.log(priority, usingTag, msg) }
    }

    @Synchronized
    override fun log(priority: Int, tag: String?, message: Any?, vararg args: Any?) {
        var msg: String
        val usingTag = tag ?: getTag()
        logAdapters
            .filter { adapter -> adapter.isLoggable(priority, usingTag) }
            .apply { msg = composeMessage(message, *args) }
            .forEach { adapter -> adapter.log(priority, usingTag, msg) }
    }

    private fun composeMessage(message: Any?, vararg args: Any?): String {
        return if (args.isEmpty()) {
            LoggerUtils.toString(message)
        } else {
            String.format(LoggerUtils.toString(message), *args)
        }
    }

    private fun composeMessage(message: Any?, throwable: Throwable?): String {
        return composeMessage({ message }, throwable)
    }

    private inline fun composeMessage(message: () -> Any?, throwable: Throwable?): String {
        return buildString {
            if (throwable != null && message() != null) {
                append(LoggerUtils.toString(message()))
                append(" : ")
                append(LoggerUtils.getStackTraceString(throwable))
            } else if (throwable != null && message() == null) {
                append(LoggerUtils.getStackTraceString(throwable))
            } else if (throwable == null && message() != null) {
                append(LoggerUtils.toString(message()))
            } else {
                append("Empty/NULL log message")
            }
        }
    }

    override fun clearLogAdapters() {
        logAdapters.clear()
    }

    override fun addAdapter(adapter: LogAdapter) {
        logAdapters.add(adapter)
    }

    override fun tag(tag: String?): Printer {
        if (tag != null) {
            localTag.set(tag)
        }
        return this
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private fun getTag(): String? {
        val tag = localTag.get()
        if (tag != null) {
            localTag.remove()
            return tag
        }
        return null
    }

    private fun isLoggable(priority: Int, tag: String? = null): Boolean {
        val myTag = tag ?: localTag.get()
        logAdapters.forEach { adapter ->
            if (adapter.isLoggable(priority, myTag)) {
                return true
            }
        }
        return false
    }

}
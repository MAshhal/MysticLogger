package com.mystic.util.prettylogger.printer

import android.util.Log.*
import com.mystic.util.prettylogger.adapter.LogAdapter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.ArgumentMatchers.isNull
import org.mockito.ArgumentMatchers.contains
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.MockitoAnnotations.openMocks

class LoggerPrinterTest {

    private val printer = LoggerPrinter()

    @Mock private lateinit var adapter: LogAdapter

    @Before fun setup() {
        openMocks(this)
        `when`(adapter.isLoggable(any(Int::class.java), any(String::class.java))).thenReturn(true)
        printer.addAdapter(adapter)
    }

    @Test fun logDebug() {
        printer.debug("message %s", "sent")

        verify(adapter).log(DEBUG, null, "message sent")
    }

    @Test fun logError() {
        printer.error("message %s", "sent")

        verify(adapter).log(ERROR, null, "message sent")
    }

    @Test fun logErrorWithThrowable() {
        val throwable = Throwable("exception")
        printer.error(throwable) { String.format("message %s", "sent") }

        verify(adapter).log(eq(ERROR), isNull(String::class.java), contains("message sent : java.lang.Throwable: exception"))
    }

    @Test fun logWarning() {
        printer.warn("message %s", "sent")

        verify(adapter).log(WARN, null, "message sent")
    }

    @Test fun logInfo() {
        printer.info("message %s", "sent")

        verify(adapter).log(INFO, null, "message sent")
    }

    @Test fun logWtf() {
        printer.wtf("message %s", "sent")

        verify(adapter).log(ASSERT, null, "message sent")
    }

    @Test fun logVerbose() {
        printer.verbose("message %s", "sent")

        verify(adapter).log(VERBOSE, null, "message sent")
    }

    @Test fun oneTimeTag() {
        printer.tag("tag").debug("message")

        verify(adapter).log(DEBUG, "tag", "message")
    }

    @Test fun logObject() {
        val `object` = "Test"

        printer.debug(`object`)

        verify(adapter).log(DEBUG, null, "Test")
    }

    @Test fun logArray() {
        val `object` = intArrayOf(1, 6, 7, 30, 33)

        printer.debug(`object`)

        verify(adapter).log(DEBUG, null, "[1, 6, 7, 30, 33]")
    }

    @Test fun logStringArray() {
        val `object` = arrayOf("a", "b", "c")

        printer.debug(`object`)

        verify(adapter).log(DEBUG, null, "[a, b, c]")
    }

    @Test fun logMultiDimensionArray() {
        val doubles = arrayOf(doubleArrayOf(1.0, 6.0), doubleArrayOf(1.2, 33.0))

        printer.debug(doubles)

        verify(adapter).log(DEBUG, null, "[[1.0, 6.0], [1.2, 33.0]]")
    }

    @Test fun logList() {
        val list = listOf("foo", "bar")
        printer.debug(list)

        verify(adapter).log(DEBUG, null, list.toString())
    }

    @Test fun logMap() {
        val map = HashMap<String, String>()
        map["key"] = "value"
        map["key2"] = "value2"

        printer.debug(map)

        verify(adapter).log(DEBUG, null, map.toString())
    }

    @Test fun logSet() {
        val set = HashSet<String>()
        set.add("key")
        set.add("key1")

        printer.debug(set)

        verify(adapter).log(DEBUG, null, set.toString())
    }

    @Test fun logJsonObject() {
        printer.debugJson("  {\"key\":3}")

        verify(adapter).log(DEBUG, null, "{\"key\": 3}")
    }

    @Test fun logJsonArray() {
        printer.debugJson("[{\"key\":3}]")

        verify(adapter).log(DEBUG, null, "[{\"key\": 3}]")
    }


    @Test fun logInvalidJsonObject() {
        printer.debug("no json")
        printer.debugJson("{ missing end")

        verify(adapter, times(2)).log(ERROR, null, "Invalid Json")
    }

    @Test fun jsonLogEmptyOrNull() {
        printer.debugJson(null)
        printer.debugJson("")

        verify(adapter, times(2)).log(DEBUG, null, "Empty/Null json content")
    }

    @Test fun xmlLog() {
        printer.debugXml("<xml>Test</xml>")

        verify(adapter).log(DEBUG, null,
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xml>Test</xml>\n")
    }

    @Test fun invalidXmlLog() {
        printer.debugXml("xml>Test</xml>")

        verify(adapter).log(ERROR, null, "Invalid xml")
    }

    @Test fun xmlLogNullOrEmpty() {
        printer.debugXml(null)
        printer.debugXml("")

        verify(adapter, times(2)).log(DEBUG, null, "Empty/Null xml content")
    }

    @Test fun clearLogAdapters() {
        printer.clearLogAdapters()

        printer.debug("")

        verifyNoInteractions(adapter)
    }

    @Test fun addAdapter() {
        printer.clearLogAdapters()
        val adapter1 = mock(LogAdapter::class.java)
        val adapter2 = mock(LogAdapter::class.java)

        printer.addAdapter(adapter1)
        printer.addAdapter(adapter2)

        printer.debug("message")

        verify(adapter1).isLoggable(DEBUG, null)
        verify(adapter2).isLoggable(DEBUG, null)
    }

    @Test fun doNotLogIfNotLoggable() {
        printer.clearLogAdapters()
        val adapter1 = mock(LogAdapter::class.java)
        `when`(adapter1.isLoggable(DEBUG, null)).thenReturn(false)

        val adapter2 = mock(LogAdapter::class.java)
        `when`(adapter2.isLoggable(DEBUG, null)).thenReturn(true)

        printer.addAdapter(adapter1)
        printer.addAdapter(adapter2)

        printer.debug("message")

        verify(adapter1, never()).log(DEBUG, null, "message")
        verify(adapter2).log(DEBUG, null, "message")
    }

    @Test fun logWithoutMessageAndThrowable() {
        printer.log(DEBUG, null, null, null)

        verify(adapter).log(DEBUG, null, "Empty/NULL log message")
    }

    @Test fun logWithOnlyThrowableWithoutMessage() {
        val throwable = Throwable("exception")
        printer.log(DEBUG, null, null, throwable)

        verify(adapter).log(eq(DEBUG), isNull(String::class.java), contains("java.lang.Throwable: exception"))
    }
}
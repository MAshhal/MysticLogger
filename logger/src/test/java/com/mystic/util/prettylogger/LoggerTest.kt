package com.mystic.util.prettylogger

import android.util.Log.*
import com.mystic.util.prettylogger.adapter.LogAdapter
import com.mystic.util.prettylogger.printer.Printer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks

class LoggerTest {

    @Mock private lateinit var printer: Printer

    @Before fun setup() {
        openMocks(this)

        MysticLogger.printer = printer
    }

    @Test fun log() {
        val throwable = Throwable()
        MysticLogger.log(VERBOSE, "tag", "message", throwable)

        verify(printer).log(VERBOSE, "tag", "message", throwable)
    }

    @Test fun debugLog() {
        MysticLogger.printer.debug("message %s", "arg")

        verify(printer).debug("message %s", "arg")
    }

    @Test fun verboseLog() {
        MysticLogger.printer.verbose("message %s", "arg")

        verify(printer).verbose("message %s", "arg")
    }

    @Test fun warningLog() {
        MysticLogger.printer.warn("message %s", "arg")

        verify(printer).warn("message %s", "arg")
    }

    @Test fun errorLog() {
        MysticLogger.printer.error("message %s", "arg")

        verify(printer).error("message %s", "arg")
    }

    @Test fun errorLogWithThrowable() {
        val throwable = Throwable("throwable")
        val message = String.format("message %s", "arg")

        MysticLogger.error(message, throwable)

        verify(printer).error(message, throwable)
    }

    @Test fun infoLog() {
        MysticLogger.printer.info("message %s", "arg")

        verify(printer).info("message %s", "arg")
    }

    @Test fun wtfLog() {
        MysticLogger.printer.wtf("message %s", "arg")

        verify(printer).wtf("message %s", "arg")
    }

    @Test fun logObject() {
        val `object` = Any()
        MysticLogger.debug(`object`)

        verify(printer).debug(`object`)
    }

    @Test fun jsonLog() {
        MysticLogger.debugJson("json")

        verify(printer).debugJson("json")
    }

    @Test fun xmlLog() {
        MysticLogger.debugXml("xml")

        verify(printer).debugXml("xml")
    }

    @Test fun oneTimeTag() {
        `when`(printer.tag("tag")).thenReturn(printer)

        MysticLogger.tag("tag").debug("message")

        verify(printer).tag("tag")
        verify(printer).debug("message")
    }

    @Test fun addAdapter() {
        val adapter = mock(LogAdapter::class.java)
        MysticLogger.addAdapter(adapter)

        verify(printer).addAdapter(adapter)
    }

    @Test fun clearLogAdapters() {
        MysticLogger.clearAdapters()

        verify(printer).clearLogAdapters()
    }
}
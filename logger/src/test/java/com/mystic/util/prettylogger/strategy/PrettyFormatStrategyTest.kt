package com.mystic.util.prettylogger.strategy

import android.util.Log.DEBUG
import com.mystic.util.prettylogger.LogAssert
import com.mystic.util.prettylogger.strategy.format.PrettyFormatStrategy
import com.mystic.util.prettylogger.strategy.log.LogStrategy
import org.junit.Test

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:43 PM
 */
class PrettyFormatStrategyTest {

    private val threadName = Thread.currentThread().name
    private val logStrategy = MockLogStrategy()
    private val builder = PrettyFormatStrategy.Builder().apply {
        logStrategy = this@PrettyFormatStrategyTest.logStrategy
    }

    //TODO: Check the actual method info
    @Test
    fun defaultLog() {
        val formatStrategy = builder.build()

        formatStrategy.log(DEBUG, null, "message")

        assertLog(DEBUG)
            .hasTopBorder()
            .hasThread(threadName)
            .hasMiddleBorder()
            .skip()
            .skip()
            .hasMiddleBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    @Test fun logWithoutThreadInfo() {
        val formatStrategy = builder.apply { showThreadInfo = false }.build()

        formatStrategy.log(DEBUG, null, "message")

        assertLog(DEBUG)
            .hasTopBorder()
            .skip()
            .skip()
            .hasMiddleBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    @Test fun logWithoutMethodInfo() {
        val formatStrategy = builder.apply { methodCount = 0 }.build()

        formatStrategy.log(DEBUG, null, "message")

        assertLog(DEBUG)
            .hasTopBorder()
            .hasThread(threadName)
            .hasMiddleBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    @Test fun logWithOnlyMessage() {
        val formatStrategy = builder.apply {
            methodCount = 0
            showThreadInfo = false
        }.build()

        formatStrategy.log(DEBUG, null, "message")

        assertLog(DEBUG)
            .hasTopBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    //TODO: Check the actual method info
    @Test fun logWithCustomMethodOffset() {
        val formatStrategy = builder.apply {
            methodOffset = 2
            showThreadInfo = false
        }.build()

        formatStrategy.log(DEBUG, null, "message")

        assertLog(DEBUG)
            .hasTopBorder()
            .skip()
            .skip()
            .hasMiddleBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    @Test fun logWithCustomTag() {
        val formatStrategy = builder.apply { tag = "custom" }.build()

        formatStrategy.log(DEBUG, null, "message")

        assertLog("custom", DEBUG)
            .hasTopBorder()
            .hasThread(threadName)
            .hasMiddleBorder()
            .skip()
            .skip()
            .hasMiddleBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    @Test fun logWithOneTimeTag() {
        val formatStrategy = builder.apply { tag = "custom" }.build()

        formatStrategy.log(DEBUG, "tag", "message")

        assertLog("custom-tag", DEBUG)
            .hasTopBorder()
            .hasThread(threadName)
            .hasMiddleBorder()
            .skip()
            .skip()
            .hasMiddleBorder()
            .hasMessage("message")
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    // TODO: assert values, for now this checks that Logger doesn't crash
    @Test fun logWithExceedingMethodCount() {
        val formatStrategy = builder.apply { methodCount = 50 }.build()

        formatStrategy.log(DEBUG, null, "message")
    }

    @Test fun logWithBigChunk() {
        val formatStrategy = builder.build()

        val chunk1 = StringBuilder()
        for (i in 0..399) {
            chunk1.append("1234567890")
        }
        val chunk2 = StringBuilder()
        for (i in 0..9) {
            chunk2.append("ABCDEFGD")
        }

        formatStrategy.log(DEBUG, null, chunk1.toString() + chunk2.toString())

        assertLog(DEBUG)
            .hasTopBorder()
            .hasThread(threadName)
            .hasMiddleBorder()
            .skip()
            .skip()
            .hasMiddleBorder()
            .hasMessage(chunk1.toString())
            .hasMessage(chunk2.toString())
            .hasBottomBorder()
            .hasNoMoreMessages()
    }

    private class MockLogStrategy : LogStrategy {
        var logItems: MutableList<LogAssert.LogItem> = ArrayList()

        override fun log(priority: Int, tag: String?, message: String) {
            logItems.add(LogAssert.LogItem(priority, tag ?: "", message))
        }
    }

    private fun assertLog(priority: Int): LogAssert {
        return assertLog(null, priority)
    }

    private fun assertLog(tag: String?, priority: Int): LogAssert {
        return LogAssert(logStrategy.logItems, tag, priority)
    }
}
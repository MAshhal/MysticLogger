package com.mystic.util.prettylogger.utils

import android.util.Log.*
import com.google.common.truth.Truth.assertThat
import com.mystic.util.prettylogger.util.LoggerUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.UnknownHostException

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:43 PM
 */
@RunWith(RobolectricTestRunner::class)
class UtilsTest {

    @Test fun getStackTraceString() {
        val throwable = Throwable("test")
        val androidTraceString = getStackTraceString(throwable)
        assertThat(LoggerUtils.getStackTraceString(throwable)).isEqualTo(androidTraceString)
    }

    @Test fun getStackTraceStringReturnsEmptyStringWithNull() {
        assertThat(LoggerUtils.getStackTraceString(null)).isEqualTo("")
    }

    @Test fun getStackTraceStringReturnEmptyStringWithUnknownHostException() {
        assertThat(LoggerUtils.getStackTraceString(UnknownHostException())).isEqualTo("")
    }

    @Test fun logLevels() {
        assertThat(LoggerUtils.logLevel(DEBUG)).isEqualTo("DEBUG")
        assertThat(LoggerUtils.logLevel(WARN)).isEqualTo("WARN")
        assertThat(LoggerUtils.logLevel(INFO)).isEqualTo("INFO")
        assertThat(LoggerUtils.logLevel(VERBOSE)).isEqualTo("VERBOSE")
        assertThat(LoggerUtils.logLevel(ASSERT)).isEqualTo("ASSERT")
        assertThat(LoggerUtils.logLevel(ERROR)).isEqualTo("ERROR")
        assertThat(LoggerUtils.logLevel(100)).isEqualTo("UNKNOWN")
    }

    @Test fun objectToString() {
        val `object` = "Test"

        assertThat(LoggerUtils.toString(`object`)).isEqualTo("Test")
    }

    @Test fun toStringWithNull() {
        assertThat(LoggerUtils.toString(null)).isEqualTo("null")
    }

    @Test fun primitiveArrayToString() {
        val booleanArray = booleanArrayOf(true, false, true)
        assertThat(LoggerUtils.toString(booleanArray)).isEqualTo("[true, false, true]")

        val byteArray = byteArrayOf(1, 0, 1)
        assertThat(LoggerUtils.toString(byteArray)).isEqualTo("[1, 0, 1]")

        val charArray = charArrayOf('a', 'b', 'c')
        assertThat(LoggerUtils.toString(charArray)).isEqualTo("[a, b, c]")

        val shortArray = shortArrayOf(1, 3, 5)
        assertThat(LoggerUtils.toString(shortArray)).isEqualTo("[1, 3, 5]")

        val intArray = intArrayOf(1, 3, 5)
        assertThat(LoggerUtils.toString(intArray)).isEqualTo("[1, 3, 5]")

        val longArray = longArrayOf(1, 3, 5)
        assertThat(LoggerUtils.toString(longArray)).isEqualTo("[1, 3, 5]")

        val floatArray = floatArrayOf(1f, 3f, 5f)
        assertThat(LoggerUtils.toString(floatArray)).isEqualTo("[1.0, 3.0, 5.0]")

        val doubleArray = doubleArrayOf(1.0, 3.0, 5.0)
        assertThat(LoggerUtils.toString(doubleArray)).isEqualTo("[1.0, 3.0, 5.0]")
    }

    @Test fun multiDimensionArrayToString() {
        val `object` = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6))

        assertThat(LoggerUtils.toString(`object`)).isEqualTo("[[1, 2, 3], [4, 5, 6]]")
    }
}
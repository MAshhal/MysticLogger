package com.mystic.util.prettylogger.strategy

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:43 PM
 */
import android.os.Environment
import android.util.Log.VERBOSE
import com.google.common.truth.Truth.assertThat
import com.mystic.util.prettylogger.strategy.format.csvFormatStrategyBuilder
import com.mystic.util.prettylogger.strategy.log.LogStrategy
import org.junit.Test

class CsvFormatStrategyTest {

    private val directoryPath =
        Environment.getExternalStorageDirectory().absolutePath + "/logger/logs"

    @Test
    fun log() {
        val formatStrategy = csvFormatStrategyBuilder(directoryPath) {
            logStrategy = object : LogStrategy {
                override fun log(priority: Int, tag: String?, message: String) {
                    assertThat(tag).isEqualTo("MYSTIC_LOGGER-tag")
                    assertThat(priority).isEqualTo(VERBOSE)
                    assertThat(message).contains("VERBOSE,MYSTIC_LOGGER-tag,message")
                }
            }
        }

        formatStrategy.log(VERBOSE, "tag", "message")
    }

    @Test
    fun defaultTag() {
        val formatStrategy = csvFormatStrategyBuilder(directoryPath) {
            logStrategy = object : LogStrategy {
                override fun log(priority: Int, tag: String?, message: String) {
                    assertThat(tag).isEqualTo("MYSTIC_LOGGER")
                }
            }
        }

        formatStrategy.log(VERBOSE, null, "message")
    }

    @Test
    fun customTag() {
        val formatStrategy = csvFormatStrategyBuilder(directoryPath) {
            tag = "custom"
            logStrategy = object : LogStrategy {
                override fun log(priority: Int, tag: String?, message: String) {
                    assertThat(tag).isEqualTo("custom")
                }
            }
        }

        formatStrategy.log(VERBOSE, null, "message")
    }
}
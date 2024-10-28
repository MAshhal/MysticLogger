package com.mystic.util.prettylogger.strategy

import android.util.Log.DEBUG
import com.google.common.truth.Truth.assertThat
import com.mystic.util.prettylogger.strategy.log.LogcatLogStrategy
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:43 PM
 */
@RunWith(RobolectricTestRunner::class)
class LogcatLogStrategyTest {

    @Test
    fun log() {
        val logStrategy = LogcatLogStrategy()

        logStrategy.log(DEBUG, "tag", "message")

        val logItems = ShadowLog.getLogs()
        assertThat(logItems[0].type).isEqualTo(DEBUG)
        assertThat(logItems[0].msg).isEqualTo("message")
        assertThat(logItems[0].tag).isEqualTo("tag")
    }

    @Test fun logWithNullTag() {
        val logStrategy = LogcatLogStrategy()

        logStrategy.log(DEBUG, null, "message")

        val logItems = ShadowLog.getLogs()
        assertThat(logItems[0].tag).isEqualTo("MYSTIC_LOGGER")
    }

}
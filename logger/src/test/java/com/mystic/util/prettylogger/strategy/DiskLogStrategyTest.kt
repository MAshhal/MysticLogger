package com.mystic.util.prettylogger.strategy

import android.os.Handler
import android.util.Log.DEBUG
import com.mystic.util.prettylogger.strategy.log.DiskLogStrategy
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:43 PM
 */
class DiskLogStrategyTest {

    @Test
    fun log() {
        val handler = mock(Handler::class.java)
        val logStrategy = DiskLogStrategy(handler)

        logStrategy.log(DEBUG, "tag", "message")

        verify(handler).sendMessage(handler.obtainMessage(DEBUG, "message"))
    }

}
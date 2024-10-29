package com.mystic.util.prettylogger.adapter

import android.util.Log.*
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.mystic.util.prettylogger.strategy.format.FormatStrategy
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:24 PM
 */
class AndroidLogAdapterTest {

    @Test fun isLoggable() {
        val logAdapter = AndroidLogAdapter()

        assertThat(logAdapter.isLoggable(DEBUG, "tag")).isTrue()
    }

    @Test fun log() {
        val formatStrategy = mock(FormatStrategy::class.java)
        val logAdapter = AndroidLogAdapter(formatStrategy)

        logAdapter.log(DEBUG, null, "message")

        verify(formatStrategy).log(DEBUG, null, "message")
    }

}
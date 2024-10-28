package com.mystic.util.prettylogger.adapter

import android.util.Log.*
import com.google.common.truth.Truth.assertThat
import com.mystic.util.prettylogger.strategy.format.FormatStrategy
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 1:38 PM
 */
class DiskLogAdapterTest {

    @Mock private lateinit var formatStrategy: FormatStrategy

    @Before fun setup() {
        openMocks(this)
    }

    @Test fun isLoggableTrue() {
        val logAdapter = DiskLogAdapter(formatStrategy)

        assertThat(logAdapter.isLoggable(VERBOSE, "tag")).isTrue()

    }

    @Test fun isLoggableFalse() {
        val logAdapter = object : DiskLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return false
            }
        }

        assertThat(logAdapter.isLoggable(VERBOSE, "tag")).isFalse()
    }

    @Test fun log() {
        val logAdapter = DiskLogAdapter(formatStrategy)

        logAdapter.log(VERBOSE, "tag", "message")

        verify(formatStrategy).log(VERBOSE, "tag", "message")
    }

}
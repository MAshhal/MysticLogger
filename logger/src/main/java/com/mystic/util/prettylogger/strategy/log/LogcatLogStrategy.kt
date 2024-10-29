package com.mystic.util.prettylogger.strategy.log

import android.util.Log.println as logcatPrint

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:42 AM
 */

/**
 * LogCat implementation for [LogStrategy]
 *
 * This simply prints out all logs to Logcat by using standard {@link Log} class.
 */
class LogcatLogStrategy: LogStrategy {
    override fun log(priority: Int, tag: String?, message: String) {
        val logTag = tag ?: DEFAULT_TAG
        logcatPrint(priority, logTag, message)
    }

    internal companion object { const val DEFAULT_TAG = "NO_TAG" }
}
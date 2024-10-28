package com.mystic.util.prettylogger.strategy.log

import android.util.Log.println as logcatPrint

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:42 AM
 */
class LogcatLogStrategy: LogStrategy {
    override fun log(priority: Int, tag: String?, message: String) { logcatPrint(priority, tag, message) }
}
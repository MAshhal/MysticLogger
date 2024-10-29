package com.mystic.util.prettylogger.strategy.log

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:41 AM
 */
fun interface LogStrategy {
    fun log(priority: Int, tag: String?, message: String)
}
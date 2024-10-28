package com.mystic.util.prettylogger.adapter

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:35 AM
 */

interface LogAdapter {

    fun isLoggable(priority: Int, tag: String?): Boolean

    fun log(priority: Int, tag: String?, message: String)

}
package com.mystic.util.prettylogger

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:34 AM
 */

interface PrettyLogger {

    val loggerTag: String
        get() = getTag(javaClass)
}

private fun getTag(jClass: Class<*>): String {
    val tag = jClass.simpleName
    return if (tag.length <= 23) tag else tag.substring(0, 23)
}
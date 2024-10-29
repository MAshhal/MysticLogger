package com.mystic.util.prettylogger.adapter

import com.mystic.util.prettylogger.strategy.format.FormatStrategy
import com.mystic.util.prettylogger.strategy.format.prettyFormatStrategyBuilder

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:36 AM
 */
class AndroidLogAdapter(
    private val formatStrategy: FormatStrategy
): LogAdapter {

    constructor(): this(prettyFormatStrategyBuilder { /* no-op */ })

    override fun isLoggable(priority: Int, tag: String?): Boolean = true

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy.log(priority, tag, message)
    }
}
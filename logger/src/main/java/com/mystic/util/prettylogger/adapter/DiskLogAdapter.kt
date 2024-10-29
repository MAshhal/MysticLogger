package com.mystic.util.prettylogger.adapter

import com.mystic.util.prettylogger.strategy.format.FormatStrategy
import com.mystic.util.prettylogger.strategy.format.CsvFormatStrategy
import com.mystic.util.prettylogger.strategy.format.csvFormatStrategyBuilder

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 10:14 AM
 */

/**
 * This is used to saves log messages to the disk.
 * By default it uses [CsvFormatStrategy] to translates text message into CSV format.
 */
open class DiskLogAdapter(
    private val formatStrategy: FormatStrategy
): LogAdapter {

    constructor(directoryPath: String): this(csvFormatStrategyBuilder(directoryPath))

    override fun isLoggable(priority: Int, tag: String?): Boolean = true

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy.log(priority, tag, message)
    }

}
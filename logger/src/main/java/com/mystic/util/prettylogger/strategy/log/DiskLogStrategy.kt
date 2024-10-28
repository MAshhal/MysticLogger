package com.mystic.util.prettylogger.strategy.log

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.io.File
import java.io.FileWriter
import java.io.IOException
import kotlin.jvm.Throws

/**
 * Created using Android Studio Ladybug
 * User: mE
 * Date: Mon, Oct 28, 2024
 * Time: 9:46 AM
 */
class DiskLogStrategy(private val handler: Handler) : LogStrategy {

    override fun log(priority: Int, tag: String?, message: String) {
        // Do nothing on the calling thread, simply pass the tag/msg to the background thread
        handler.sendMessage(handler.obtainMessage(priority, message))
    }

    class WriteHandler(
        looper: Looper,
        private val directory: String,
        private val maxFileSize: Int
    ) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            val content = msg.obj as String
            // Write the log to the file
            var fileWriter: FileWriter? = null
            try {
                val logFile = getLogFile(directory, "logs")
                fileWriter = FileWriter(logFile, true)

                fileWriter.use { writer ->
                    writeLog(writer, content)
                    writer.flush()
                }
            } catch (ex: IOException) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush()
                        fileWriter.close()
                    } catch (e: IOException) {
                        // Fail silently
                    }
                }
            }
        }

        /**
         *
         */
        @Throws(IOException::class)
        private fun writeLog(fileWriter: FileWriter, content: String) {
            fileWriter.append(content)
        }

        private fun getLogFile(directoryName: String, fileName: String): File {
            val folder = File(directoryName)
            if (!folder.exists() && !folder.mkdirs()) {
                throw RuntimeException(
                    """
                        Directory $directoryName could not be created
                        Make sure you have the permissions to write in external storage if the directory provided is `Environment.getExternalStorageDirectory()` 
                    """.trimIndent()
                )
            }

            var newFileCount = 0
            var newFile: File
            var existingFile: File? = null

            newFile = File(folder, "${fileName}_$newFileCount.csv")
            while (newFile.exists()) {
                existingFile = newFile
                newFileCount++
                newFile = File(folder, "${fileName}_$newFileCount.csv")
            }

            return if (existingFile != null) {
                if (existingFile.length() >= maxFileSize) newFile else existingFile
            } else newFile
        }
    }
}
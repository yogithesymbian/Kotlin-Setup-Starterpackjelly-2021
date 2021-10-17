package id.scodeid.kotlin_setup_starterpackjelly2021.utils

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FilenameFilter
import java.text.SimpleDateFormat
import java.util.*

// array of supported extensions (use a List if you prefer)
val extension = arrayOf(
    "gif", "png", "bmp", "jpg" // and other formats you need
)

// filter to identify images based on their extensions
val IMAGE_FILTER: FilenameFilter = object : FilenameFilter {
    override fun accept(dir: File?, name: String): Boolean {
        for (ext in extension) {
            if (name.endsWith(".$ext")) {
                return true
            }
        }
        return false
    }
}

val IMAGE_NO_FILTER: FilenameFilter = FilenameFilter { dir, name -> true }

// filter to identify images based on their extensions
val FOLDER_NAME_FILTER: FilenameFilter = object : FilenameFilter {
    override fun accept(dir: File?, name: String): Boolean {
        for (ext in extension) {
            if (name.contains(".$ext"))
                return false
        }
        return true
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showSnackbar(msgId: Int, length: Int) {
    showSnackbar(context.getString(msgId), length)
}

fun View.showSnackbar(msg: String, length: Int) {
    showSnackbar(msg, length, null) {}
}

fun View.showSnackbar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackbar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    }
}

@SuppressLint("SimpleDateFormat")
fun toGMTFormat(datetime: String?): Date? { // nrohman dicoding discuss
    val formatter = SimpleDateFormat("yy-MM-dd hh:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("GMT +8")
    val result = "$datetime"
    return formatter.parse(result)
}
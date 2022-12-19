package `in`.hahow.android_recruit_project.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 用來放置處理日期格式的各 method
 */
enum class DatePattern(val pattern: String) {
    yyyyMMddTmmhhss("yyyy-MM-dd'T'HH:mm:ss")
}

fun String?.convertStringToDate(datePattern: DatePattern): Date? {
    if (this.isNullOrEmpty()) return null
    val date = SimpleDateFormat(datePattern.pattern, Locale.TAIWAN).parse(this)
    return date ?: null
}
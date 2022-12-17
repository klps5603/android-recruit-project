package `in`.hahow.android_recruit_project.utils

import android.content.Context

/**
 * 一個放置共通常用 method
 */
class Util {

    /**
     * 讀取 assets 資料夾中的 file 文字
     */
    fun getTextFromAssets(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().readText()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}
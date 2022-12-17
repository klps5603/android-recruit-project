package `in`.hahow.android_recruit_project.utils

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 用來放置解析 json method
 */
class JsonUtil {

    inline fun <reified T> convertJsonToData(jsonString: String): T {
        return Gson().fromJson(jsonString, T::class.java)
    }

    inline fun <reified T> convertJsonToList(jsonString: String): ArrayList<T> {
        return Gson().fromJson(jsonString, ParameterizedTypeImpl(T::class.java))
    }

    class ParameterizedTypeImpl(private val clz: Class<*>) : ParameterizedType {
        override fun getRawType(): Type = List::class.java

        override fun getOwnerType(): Type? = null

        override fun getActualTypeArguments(): Array<Type> = arrayOf(clz)
    }

}
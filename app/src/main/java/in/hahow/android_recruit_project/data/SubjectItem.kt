package `in`.hahow.android_recruit_project.data

import com.google.gson.annotations.SerializedName

data class SubjectItem(
    @SerializedName("successCriteria")
    val successCriteria: SuccessCriteria,

    @SerializedName("status")
    val status: String,

    @SerializedName("proposalDueTime")
    val proposalDueTime: String,

    @SerializedName("coverImageUrl")
    val coverImageUrl: String,

    @SerializedName("title")
    val title: String

)

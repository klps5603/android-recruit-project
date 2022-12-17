package `in`.hahow.android_recruit_project.data

import com.google.gson.annotations.SerializedName

data class SubjectData(
    @SerializedName("data")
    val subjectList: List<SubjectItem>
)

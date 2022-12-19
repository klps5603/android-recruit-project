package `in`.hahow.android_recruit_project.viewModel

import `in`.hahow.android_recruit_project.data.SubjectItem
import `in`.hahow.android_recruit_project.enum.Status
import `in`.hahow.android_recruit_project.utils.DatePattern
import `in`.hahow.android_recruit_project.utils.convertStringToDate
import android.util.Log
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import java.util.*

/**
 * MvRx 開發框架，用以存放資料
 */
data class SubjectState(
    val originalSubjectList: List<SubjectItem> = listOf(),
    val filterSubjectList: List<SubjectItem> = listOf()
) : MvRxState

/**
 * MvRx 開發框架
 * 用以處理資料，最後更新至 state
 */
class SubjectViewModel(state: SubjectState) : BaseMvRxViewModel<SubjectState>(state, false) {

    /**
     * status 為募資成功或已開課，新增到課程列表
     * status 為募資中，判斷募資時間未過期，才新增到課程列表
     * status 為預期外的資料時，不新增到課程列表，並 log 出來
     */
    fun setSubjectList(subjectList: List<SubjectItem>) {
        val list = mutableListOf<SubjectItem>()
        subjectList.forEach { subjectItem ->
            val status = Status.values().find { it.name == subjectItem.status }
                ?: Status.NONE
            when (status) {
                Status.INCUBATING -> {
                    val proposalDueDate =
                        subjectItem.proposalDueTime.convertStringToDate(DatePattern.yyyyMMddTmmhhss)
                    if (proposalDueDate != null && proposalDueDate.time > Date().time) {
                        list.add(subjectItem)
                    }
                }
                Status.PUBLISHED,
                Status.SUCCESS -> {
                    list.add(subjectItem)
                }
                else -> {
                    Log.e(
                        "status error", "title = ${subjectItem.title} , status = " +
                                subjectItem.status
                    )
                }
            }
        }

        setState {
            copy(originalSubjectList = list, filterSubjectList = list)
        }
    }

    /**
     * 依據 status 篩選課程
     */
    fun filterSubjectList(text: String) {
        setState {
            var list = listOf<SubjectItem>()
            val status = Status.values().find { it.text == text }
            status?.let {
                list = if (status == Status.ALL) {
                    originalSubjectList
                } else {
                    originalSubjectList.filter { it.status == status.name }
                }
            }

            copy(
                filterSubjectList = list
            )
        }
    }

}
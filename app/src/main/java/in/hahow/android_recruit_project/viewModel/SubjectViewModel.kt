package `in`.hahow.android_recruit_project.viewModel

import `in`.hahow.android_recruit_project.adapter.SubjectAdapter
import `in`.hahow.android_recruit_project.data.SubjectItem
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
    val subjectList: List<SubjectItem> = listOf()
) : MvRxState

/**
 * MvRx 開發框架，如同 MVVM 的 viewModel
 * 用以處理資料，最後更新至 state
 */
class SubjectViewModel(state: SubjectState) : BaseMvRxViewModel<SubjectState>(state, false) {

    /**
     * status 為募資成功或已開課，新增到 subjectList
     * status 為募資中，判斷募資時間未過期，才新增到 subjectList
     * status 為預期外的資料時，不新增到 subjectList，並 log 出來
     */
    fun setSubjectList(subjectList: List<SubjectItem>) {
        val list = mutableListOf<SubjectItem>()
        subjectList.forEach { subjectItem ->
            val status = SubjectAdapter.Status.values().find { it.name == subjectItem.status }
                ?: SubjectAdapter.Status.NONE
            when (status) {
                SubjectAdapter.Status.INCUBATING -> {
                    val proposalDueDate =
                        subjectItem.proposalDueTime.convertStringToDate(DatePattern.yyyyMMddTmmhhss)
                    if (proposalDueDate != null && proposalDueDate.time > Date().time) {
                        list.add(subjectItem)
                    }
                }
                SubjectAdapter.Status.PUBLISHED,
                SubjectAdapter.Status.SUCCESS -> {
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
            copy(subjectList = list)
        }
    }

}
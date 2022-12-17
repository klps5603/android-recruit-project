package `in`.hahow.android_recruit_project.viewModel

import `in`.hahow.android_recruit_project.data.SubjectItem
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

data class SubjectState(
    val list: List<SubjectItem> = listOf()
) : MvRxState

class SubjectViewModel(state: SubjectState) : BaseMvRxViewModel<SubjectState>(state, true) {

    fun setList(list: List<SubjectItem>) {
        setState {
            copy(list = list)
        }
    }

}
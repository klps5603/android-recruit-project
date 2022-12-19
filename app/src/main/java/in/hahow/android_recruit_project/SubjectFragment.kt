package `in`.hahow.android_recruit_project

import `in`.hahow.android_recruit_project.adapter.SubjectAdapter
import `in`.hahow.android_recruit_project.base.BaseFragment
import `in`.hahow.android_recruit_project.base.baseActivity
import `in`.hahow.android_recruit_project.data.SubjectData
import `in`.hahow.android_recruit_project.databinding.FragmentSubjectBinding
import `in`.hahow.android_recruit_project.utils.JsonUtil
import `in`.hahow.android_recruit_project.utils.Util
import `in`.hahow.android_recruit_project.viewModel.SubjectViewModel
import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState

class SubjectFragment : BaseFragment(R.layout.fragment_subject) {

    private val viewModel: SubjectViewModel by fragmentViewModel()
    private lateinit var binding: FragmentSubjectBinding
    private val subjectAdapter = SubjectAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubjectBinding.bind(view)
        binding.subjectRecyclerView.adapter = subjectAdapter

        baseActivity()?.let {
            it.title = getString(R.string.subject)
        }
    }

    /**
     * 在此實作讀取 json file
     * 取得 json 資料後，解析並轉型成 list
     * 最後交由 viewModel 處理資料，刷新畫面
     */
    override fun loadJsonFile() {
        context?.let { context ->
            Util().getTextFromAssets(context, "data.json")?.let {
                val data = JsonUtil().convertJsonToData<SubjectData>(it)
                viewModel.setSubjectList(data.subjectList)
            }
        }
    }

    /**
     * 當 viewModel state 中任一資料變更時，都會呼叫此 method，用以刷新畫面
     */
    override fun invalidate() {
        withState(viewModel) {
            subjectAdapter.list = it.subjectList
        }
    }

}
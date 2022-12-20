package `in`.hahow.android_recruit_project.fragment

import `in`.hahow.android_recruit_project.R
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
import android.view.View.OnClickListener
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab

class SubjectFragment : BaseFragment(R.layout.fragment_subject) {

    private val viewModel: SubjectViewModel by fragmentViewModel()
    private lateinit var binding: FragmentSubjectBinding
    private val subjectAdapter = SubjectAdapter()

    /**
     * 點選上方的分類頁籤
     * 依據 status 篩選課程並置頂列表
     */
    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: Tab?) {
            val text = tab?.text as String
            binding.scrollView.scrollTo(0, 0)
            binding.subjectRecyclerView.scrollToPosition(0)
            viewModel.filterSubjectList(text)
        }

        override fun onTabUnselected(tab: Tab?) {

        }

        override fun onTabReselected(tab: Tab?) {

        }

    }

    /**
     * 當滑動列表超過一定位置，顯示 TOP 按鈕，否則隱藏
     */
    private val onScrollChangeListener =
        OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > 500) {
                binding.topButton.visibility = View.VISIBLE
            } else {
                binding.topButton.visibility = View.GONE
            }
        }

    /**
     * 點選 TOP 按鈕，置頂課程列表
     */
    private val onTopClickListener = OnClickListener {
        binding.topButton.visibility = View.GONE
        binding.scrollView.smoothScrollTo(0, 0)
        binding.subjectRecyclerView.smoothScrollToPosition(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity()?.let {
            it.title = getString(R.string.subject)
        }

        binding = FragmentSubjectBinding.bind(view)
        binding.subjectRecyclerView.adapter = subjectAdapter
        binding.statusTabLayout.addOnTabSelectedListener(onTabSelectedListener)
        binding.scrollView.setOnScrollChangeListener(onScrollChangeListener)
        binding.topButton.setOnClickListener(onTopClickListener)

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
     * 沒有任何資料變更時，不會呼叫此 method
     */
    override fun invalidate() {
        withState(viewModel) {
            subjectAdapter.list = it.filterSubjectList
        }
    }

}
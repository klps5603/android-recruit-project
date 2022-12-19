package `in`.hahow.android_recruit_project.base

import `in`.hahow.android_recruit_project.databinding.ActivityBaseBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * 底層 Activity，提供給所有　Activity（實作實際畫面）繼承
 */
abstract class BaseActivity(@LayoutRes val layoutRes: Int) : AppCompatActivity() {

    private val binding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

    /**
     * 設定標題文字
     */
    var title: String = ""
        set(value) {
            field = value
            binding.titleTextView.text = field
        }

    /**
     * 用以顯示所有子 Activity 畫面
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewStub.run {
            layoutResource = layoutRes
            inflate()
        }
    }
}

/**
 * 使繼承 BaseFragment 的 fragment 可以取得 BaseActivity
 * 主要是為了設定標題文字
 */
fun BaseFragment.baseActivity(): BaseActivity? {
    return (activity as? BaseActivity)
}
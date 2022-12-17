package `in`.hahow.android_recruit_project.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.airbnb.mvrx.BaseMvRxFragment

abstract class BaseFragment(@LayoutRes val layoutRes: Int) : BaseMvRxFragment() {

    /**
     * 依題目需求寫一個 Data Loader 的抽象層
     */
    abstract fun loadJsonFile()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadJsonFile()
    }

}
package `in`.hahow.android_recruit_project

import `in`.hahow.android_recruit_project.base.BaseActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private var lastBackPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, SubjectFragment())
            .commit()
    }

    /**
     * 為避免使用者誤觸返回，要在兩秒內再按一次返回，才能結束APP
     */
    override fun onBackPressed() {
        val nowTime = Date().time
        if (lastBackPressedTime != 0L && nowTime - lastBackPressedTime < 2000) {
            lastBackPressedTime = 0L
            super.onBackPressed()
        } else {
            lastBackPressedTime = nowTime
            Toast.makeText(this, getString(R.string.exit_check), Toast.LENGTH_SHORT).show()
        }
    }

}
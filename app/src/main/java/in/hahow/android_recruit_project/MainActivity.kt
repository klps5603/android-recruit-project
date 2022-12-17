package `in`.hahow.android_recruit_project

import `in`.hahow.android_recruit_project.base.BaseActivity
import android.os.Bundle

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.app_name) //先給個預設標題文字

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, SubjectFragment())
            .commit()
    }

}
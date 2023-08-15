package com.example.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.ActivityPath

@Route(path = ActivityPath.PAGER_LOGIN)
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        findViewById<Button>(R.id.btnlogin).setOnClickListener {
            ARouter.getInstance().build(ActivityPath.PAGER_HOME).navigation()
        }
    }
}
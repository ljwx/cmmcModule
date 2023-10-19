package com.ljwx.basemodule

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.ljwx.baseactivity.fast.QuickMainActivity
import com.ljwx.baseapp.extensions.TAG_CLASS
import com.ljwx.baseapp.extensions.visibleGone
import com.ljwx.baseeventbus.flow.FlowEventBus
import com.ljwx.basemodule.databinding.ActivityMainBinding
import com.ljwx.basemodule.fragments.*
import com.ljwx.basemodule.vm.TestViewModel
import com.ljwx.basenotification.BaseNotificationDndUtils
import com.ljwx.basenotification.BaseNotificationUtils
import com.ljwx.baseswitchenv.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity :
    QuickMainActivity<ActivityMainBinding, TestViewModel>(R.layout.activity_main) {

    private val dialog by lazy {
        TestDialog()
    }

    override fun getTabLayout() = mBinding.tabLayout

    override fun getViewPager2() = mBinding.viewPager

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        addTabFragment("staterefresh", StateRefreshFragment())
        addTabFragment("basefragment", BaseFragmentTest())
//        addTabFragment("mvvmFragment", LoginFragment())
//        addTabFragment("toolbar", BaseToolbarFragment())
        addTabFragment("loadmore", LoadMoreFragment())
//        addTabFragment("vmFragment", ViewModelFragment())
//        addTabFragment("javaTest", TestJavaFragment(0))

        registerFinishBroadcast("test")

        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000)
//            routerTo("/path/router_test").withInt("abc", 1).start()
            withContext(Dispatchers.Main) {
                ARouter.getInstance().build("/app/router_test").navigation()
            }
        }


    }

    override fun onBroadcastPageFinish() {
        Log.d("ljwx2", "接收到结束广播")
//        super.onBroadcastPageFinish()
    }

    override fun onBroadcastOther(action: String?) {
        super.onBroadcastOther(action)
        Log.d("ljwx2", "收到广播")
    }

    override fun getScreenOrientation() = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("ljwx2", "保存数据")
        outState.putString("save", "saveCache")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("ljwx2", "恢复数据:" + savedInstanceState.getString("save", "空的"))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ljwx2", "$TAG_CLASS:onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
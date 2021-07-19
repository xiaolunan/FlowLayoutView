package com.renchunlin.flowlayoutview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.renchunlin.flowlayout.FlowLayout
import com.renchunlin.flowlayout.bean.Item
import com.renchunlin.flowlayoutview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        //数据源
        val list = mutableListOf(
            "1月",
            "2月",
            "3月",
            "4月",
            "个人个规而改为如果微软歌热舞Greg我隔热隔热我为退热贴各位热舞挺好",
            "6月",
            "7月",
            "8月",
            "9月",
            "10月",
            "11月",
            "12月"
        )

        //设置文字颜色在设置数据之前
        binding.mFlowLayout.textColor = Color.BLACK
        //设置数据源
        binding.mFlowLayout.textList(list)
        //设置点击事件的回调
        binding.mFlowLayout.setItemClickListener(object : FlowLayout.ItemClickListener {
            override fun itemClick(v: View, content: String) {
                Log.i("chen", "itemClick: $content")
            }
        })
    }
}
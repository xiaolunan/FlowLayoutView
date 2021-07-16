package com.renchunlin.flowlayoutview

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

        val list = mutableListOf(
            Item("1月", false),
            Item("2月", false),
            Item("3月", false),
            Item("4月", false),
            Item("个人个人我个人哥哥违规而改为如果微软歌热舞Greg我隔热隔热我为退热贴各位热舞挺好", false),
            Item("6月", false),
            Item("7月", false),
            Item("8月", false),
            Item("9月", false),
            Item("10月", false),
            Item("11月", false),
            Item("12月", false)
        )

        binding.mFlowLayout.textList(list)

        binding.mFlowLayout.setItemClickListener(object : FlowLayout.ItemClickListener {
            override fun itemClick(v: View, content: String) {
                Log.i("chen", "itemClick: $content")
            }
        })
    }
}
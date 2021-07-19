# FlowLayoutView

#### 介绍
kotlin版自定义流式布局，自定义ViewGroup，自动换行热门搜索，搜索历史标签，你可以java代码调用也可以使用kotlin调用，自定义化程度高

[![](https://jitpack.io/v/xiaolunan/FlowLayoutView.svg)](https://jitpack.io/#xiaolunan/FlowLayoutView)

![FlowLayout](https://github.com/xiaolunan/img-folder/blob/master/FlowLayoutView/n76y1-sjhp9.gif "JAVA")

### 属性介绍 attrs.xml

```
<resources>
    <declare-styleable name="FlowLayout">
        <!--最大的行数-->
        <attr name="maxLines" format="integer" />
        <!--item左右间距水平方向-->
        <attr name="itemHorizontalMargin" format="dimension" />
        <!--item上下间距垂直方向-->
        <attr name="itemVerticalMargin" format="dimension" />
        <!--最大文字数量-->
        <attr name="textMaxLength" format="integer" />
        <!--默认字体颜色-->
        <attr name="textColor" format="color|reference" />
        <!--选中字体颜色-->
        <attr name="selectedTextColor" format="color|reference" />
        <!--设置选中的shape 背景色 边框色 圆角大小-->
        <attr name="selectedBgShape" format="reference" />
        <!--设置未选中的shape 背景色 边框色 圆角大小-->
        <attr name="bgShape" format="reference" />
    </declare-styleable>
</resources>
```

### 使用方法
#### 添加依赖
##### build.gradle

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
#### add的build.gradle

```
dependencies {
	implementation 'com.github.xiaolunan:FlowLayoutView:1.2'
}
```
#### 布局中引用

```
        <com.renchunlin.flowlayout.FlowLayout
        android:id="@+id/mFlowLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:bgShape="@drawable/text_bg_normal"
        app:layout_constraintTop_toTopOf="parent"
        app:selectedBgShape="@drawable/text_bg_press"
        app:selectedTextColor="#ffffff"
        app:textColor="#888585" />
```
#### 代码中使用
```
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
```

package com.renchunlin.flowlayout

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.renchunlin.flowlayout.bean.Item
import com.renchunlin.flowlayout.utils.SizeUtils

/*
 * class title: 
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2021/7/15.
 * PS: Not easy to write code, please indicate.
 */
class FlowLayout : ViewGroup {
    companion object {
        const val DEFAULT_LINE = -1

        //后面需要转单位，目前是px不适配
        const val DEFAULT_HORIZONTAL_MARGIN = 5f
        const val DEFAULT_VERTICAL_MARGIN = 5f
        const val DEFAULT_TEXT_MAX_LENGTH = -1
        const val DEFAULT_BORDER_RADIUS = 20f
        const val TAG = "FlowLayout"
    }

    private var mData: MutableList<Item>
    var maxLine: Int = 0
    var horizontalMargin: Int = 0
    var verticalMargin: Int = 0
    var textMaxLength: Int = 0
    var textColor: Int = 0
    var selectedTextColor: Int = 0
    var selectedBgShape: Int = 0
    var bgShape: Int = 0
    private lateinit var itemClickListener: ItemClickListener

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //获取属性
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)

        maxLine = typeArray.getInt(R.styleable.FlowLayout_maxLines, DEFAULT_LINE)
        if (maxLine != -1 && maxLine < 1) {
            throw IllegalArgumentException("maxLine can not less then 1.")
        }
        horizontalMargin = typeArray.getDimension(
            R.styleable.FlowLayout_itemHorizontalMargin,
            SizeUtils.dip2px(DEFAULT_HORIZONTAL_MARGIN)
        ).toInt()
        verticalMargin = typeArray.getDimension(
            R.styleable.FlowLayout_itemVerticalMargin,
            SizeUtils.dip2px(DEFAULT_VERTICAL_MARGIN)
        ).toInt()
        textMaxLength =
            typeArray.getInt(R.styleable.FlowLayout_textMaxLength, DEFAULT_TEXT_MAX_LENGTH)
        if (textMaxLength != DEFAULT_TEXT_MAX_LENGTH && textMaxLength < 0) {
            throw IllegalArgumentException("text length must be max then 0.")
        }
        textColor = typeArray.getColor(
            R.styleable.FlowLayout_textColor,
            ContextCompat.getColor(context, R.color.text_grey)
        )
        selectedTextColor = typeArray.getColor(
            R.styleable.FlowLayout_selectedTextColor,
            ContextCompat.getColor(context, R.color.text_white)
        )

        //选中shape
        selectedBgShape = typeArray.getResourceId(
            R.styleable.FlowLayout_selectedBgShape,
            R.drawable.shape_flow_text_bg_press
        )

        //未选中shape
        bgShape = typeArray.getResourceId(
            R.styleable.FlowLayout_bgShape,
            R.drawable.shape_flow_text_bg_normal
        )
        typeArray.recycle()
        mData = arrayListOf()
    }

    //管理孩子，摆放孩子
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val firstChild = getChildAt(0)
        var currentLeft = verticalMargin + paddingLeft
        var currentTop = verticalMargin + paddingTop
        var currentRight = verticalMargin + paddingLeft
        var currentBottom = firstChild.measuredHeight + verticalMargin + paddingTop
        for (line in lines) {
            for (view in line) {
                //布局每一行
                val width = view.measuredWidth
                currentRight += width
                //判断最右边的边界
                if (currentRight > measuredWidth - horizontalMargin) {
                    currentRight = measuredWidth - horizontalMargin
                }
                view.layout(currentLeft, currentTop, currentRight, currentBottom)
                //horizontalMargin 左右间距
                currentLeft = currentRight + horizontalMargin
                currentRight += horizontalMargin
            }
            currentLeft = verticalMargin + paddingLeft
            currentRight = verticalMargin + paddingLeft
            currentBottom += firstChild.measuredHeight + verticalMargin
            currentTop += firstChild.measuredHeight + verticalMargin
        }
    }

    fun textList(data: List<String>) {
        mData.clear()
        for (datum in data) {
            mData.add(Item(datum, false))
        }
        //根据布局创建子View，并且添加进来
        setUpChildren()
    }

    private fun setUpChildren() {
        //先清空原有的内容
        removeAllViews()
        //添加子View进来
        for (i in 0 until mData.size) {
            //val text = TextView(context)
            val text = LayoutInflater.from(context)
                .inflate(R.layout.item_flow_text, this, false) as TextView
            if (textMaxLength != DEFAULT_TEXT_MAX_LENGTH) {
                //设置TextView的最长内容长度
                //TextView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(300) });
                text.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(textMaxLength))
            }
            text.text = mData[i].content
            //设置TextView的相关属性：边距，颜色，border之类...
            //Log.i(TAG, "setUpChildren: ${mData[i]}")
            if (mData[i].isSelected) {
                text.setBackgroundResource(selectedBgShape)
                text.setTextColor(selectedTextColor)
            } else {
                text.setBackgroundResource(bgShape)
                text.setTextColor(textColor)
            }
            text.setOnClickListener {
                if (::itemClickListener.isInitialized) {
                    this.itemClickListener.itemClick(it, mData[i].content)
                }
                for (mDatum in mData) {
                    mDatum.isSelected = mDatum.content == mData[i].content
                }
                setUpChildren()
            }
            addView(text)
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //点击事件
    interface ItemClickListener {
        fun itemClick(v: View, content: String)
    }

    private val lines = mutableListOf<List<View>>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //val mode = MeasureSpec.getMode(widthMeasureSpec)
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeightSize = MeasureSpec.getSize(heightMeasureSpec)

        //孩子的个数
        val childCount = childCount
        if (childCount == 0) {
            return
        }

        //先清空
        lines.clear()
        //添加默认行
        var line = mutableListOf<View>()
        lines.add(line)

        //测量孩子
        val childWidthSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.AT_MOST)
        val childHeightSpec = MeasureSpec.makeMeasureSpec(parentHeightSize, MeasureSpec.AT_MOST)

        for (i in 0 until childCount) {
            val child = getChildAt(i) //拿到单独的一个孩子
            if (child.visibility != VISIBLE) {
                continue
            }
            //测量孩子
            //measureChild(child, childWidthSpec, childHeightSpec)
            child.measure(childWidthSpec, childHeightSpec)
            if (line.size == 0) {
                //可以添加
                line.add(child)
            } else {
                //判断是否可以添加到当前行
                val canBeAdd: Boolean = checkChildCanBeAdd(line, child, parentWidthSize)
                if (!canBeAdd) {
                    if (maxLine != -1 && lines.size >= maxLine) {
                        //跳出循环，不再添加
                        break
                    }
                    line = mutableListOf()
                    lines.add(line)
                }
                line.add(child)
            }
        }

        //根据尺寸计算所有行高
        val childAt = getChildAt(0)
        //孩子高度
        val childHeight = childAt.measuredHeight
        //加上上下间距 verticalMargin
        val parentHeightTargetSize =
            childHeight * lines.size + verticalMargin * (lines.size + 1) + paddingTop + paddingBottom
        setMeasuredDimension(parentWidthSize, parentHeightTargetSize)
    }

    /**
     * 判断是否可以添加到当前行
     */
    private fun checkChildCanBeAdd(
        line: MutableList<View>,
        child: View,
        parentWidthSize: Int
    ): Boolean {
        val measuredWidth = child.measuredWidth + paddingRight
        var totalWidth = horizontalMargin
        for (view in line) {
            //horizontalMargin 左右间距
            totalWidth += view.measuredWidth + horizontalMargin
        }
        //horizontalMargin 左右间距
        totalWidth += measuredWidth + horizontalMargin + paddingLeft
        //如果超出限制宽度则换行，则不可以在添加
        //否则可以添加
        return totalWidth <= parentWidthSize
    }
}
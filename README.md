# FlowLayoutView

#### 介绍
kotlin版自定义流式布局，自定义ViewGroup，自动换行热门搜索，搜索历史标签，你可以java代码调用也可以使用kotlin调用，自定义化程度高

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
        <!--字体颜色-->
        <attr name="textColor" format="color|reference" />
        <!--item边框颜色-->
        <attr name="borderColor" format="color|reference" />
        <!--item边框圆角度-->
        <attr name="borderRadius" format="dimension" />
    </declare-styleable>
</resources>
```

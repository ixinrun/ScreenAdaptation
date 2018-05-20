# ScreenAdaptation
One-step screen adaptation.

大家都知道Android屏幕适配是件非常头疼的事，目前全世界安卓设备的大大小小分辨率，大大小小的尺寸，最终形成的设备屏幕大小种类不计其数，但就这一项就给开发者造成了不少困难，总是照顾住这种屏幕照顾不了那种屏幕。

先来放一种很熟悉的图：
<img src="screenshots/0.png" width="480" hight="300">

针对Android屏幕适配，除了我们按照“灵活运用布局”、“尺寸限定符”、“布局相关属性”、“.9图”、“屏幕密度适配”等官方标准，但是却发现还远远不够，要么添加很多图片(从加载性能说似乎是必要的)使得apk变得很大，要么有多套布局工作量变大难以维护。针对屏幕适配，google官方也是系统默认支持的是采用屏幕像素密度来进行匹配相关资源和长度的，这一块不做过多的阐述了，建议参看官方文档。

这里我提供一个最牛、最简单、一步到位的方案，大概其优点如下：

- 只需要提供一套最优图片，用于减小apk包大小。当然为了加载性能和图片更精细化显示采用多套也可以。
- 无论大小屏如果布局确定只需要一套布局，当然如果横竖屏变换不同的屏幕展示，多套也是必要的。
- 没有重叠的现象发生，按照默认布局方法可能在大屏上适配很好，到小屏上却出现重叠的现象，很常见，但这里我不允许他有！
- 无论大小屏整体看过去布局几乎相同，因为是按比例来的。
- 在大屏上展示不会随着完全比例放大而显得傻、大、憨。

其原理也很简单，就是根据基准屏幕像素密度来进行适当缩放后得到相对屏幕像素，然后给系统的像素密度重新赋值。

准备工作：
确定一套基准屏幕参数，然后布局时根据这套参数布局，单位仍然采用dp，并尽可能确定其布局控件的长度。
```java
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 1080f;
        final float DESIGN_HEIGHT = 1920f;
        final float DESTGN_INCH = 5.0f;
```

三步走：
1. 确定放大缩小比例
2. 确定参考屏幕密度
3. 确定相对屏幕密度并重新赋值给系统的像素密度

主要方法：
```
    /**
     * 重置屏幕密度
     */
    private void resetDensity() {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 1080f;
        final float DESIGN_HEIGHT = 1920f;
        final float DESTGN_INCH = 5.0f;
        //大屏调节因子，范围0~1，因屏幕同比例放大视图显示非常傻大憨，用于调节感官度
        final float BIG_SCREEN_FACTOR = 0.2f;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        //确定放大缩小比率
        float rate = Math.min(dm.widthPixels, dm.heightPixels) / Math.min(DESIGN_WIDTH, DESIGN_HEIGHT);
        //确定参照屏幕密度
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / 160;
        //确定最终屏幕密度
        float relativeDensity = referenceDensity * rate;
        if (relativeDensity > dm.density) {
            relativeDensity = relativeDensity - (relativeDensity - dm.density) * BIG_SCREEN_FACTOR;
        }
        dm.density = relativeDensity;
    }
```
注意放大缩小比例屏幕的长宽和参考屏幕的长宽对应设定的，这样计算出来的屏幕密度是固定的。这些所做的工作都在application的初始的生命周期中，一旦app启动优先处理这件事，其他均按照默认处理，就是这么简单。

**使用和不使用截图直观感受**
采用默认布局方式截图对比：
<img src="screenshots/1.png" width="800" hight="480">

采用此方案截图对比：
<img src="screenshots/2.png" width="800" hight="480">

转载请注明出处，我是toperc.


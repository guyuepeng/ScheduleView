# ScheduleView
使用RecyclerView实现的自定义课程表View

USE RECYCLERVIEW
***
最近打算依托学校教务系统做一个Android平台的客户端，需要一个课程表控件，在网上找了几个都不是很满意，感觉用起来灵活性不是很好，于是自己动手码了一个，贴出来记录一下，有问题欢迎Issue
***
###效果图
![显示周一到周五](http://upload-images.jianshu.io/upload_images/1941207-4b67cea940dc32b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![正常显示一周](http://upload-images.jianshu.io/upload_images/1941207-bba0a926244ea027.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
***
#自定义View——ScheduleView
###结构
>LinearLayout
-- LinearLayout
--  RecyclerView    

层次很简单，最外层是一个LinearLayout，星期栏(header)是一个LinearLayout，下面整个是一个RecyclerView

###思路：
- 利用```GridLayoutManager.setSpanSizeLookUp(SpanSizeLookup)```方法，在Adapter中通过```getItemViewType()```区分两种不同类型item，实现不同的item（index和course）
- 从而控制最左侧的item(index)的宽度和正常的item(course)的宽度比例为1:TIMES（其中TIMES在Constant中定义）
- 同样的，在Header（星期栏）中使用```addView(View child, ViewGroup.LayoutParams params)```，通过设置weight来控制每个item的大小和位置，从而保证，header中的item和下面RecyclerView中的item位置上是对应的
- 将Header中一周显示几天交给调用者决定（大多数学校的周末是没有课的，所以可以一周只显示5天）
- 调用者传入的是课程信息，但是RecyclerView的item中包含了index类型的item，所以需要将传入的`List<CourseBean>`做处理成`List<Data4RvItem>`，将index信息插入到合适位置，具体实现往下看
- RecyclerView在获取到数据之后才开始初始化加载，因此等待用户调用```fillData(...)```方法后才开始进行初始化操作
- 在CourseViewHolder中对itemView添加点击事件

###实现
说了这么多，不如直接撸代码：
```
public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        root = inflate(context, R.layout.schedule_view, this);
        scheduleHeadLL = (LinearLayout) root.findViewById(R.id.ll_schedule_head);
        scheduleContentRV = (RecyclerView) root.findViewById(R.id.rv_schedule_content);
    }
...
在获取到用户数据后调用者调用fillData(...)之后对RecyclerView的初始化：
private void initRecyclerView(ScheduleBean mScheduleBean, int dayInWeek) {
        final List<Data4RvItem> datas = mScheduleBean.getDatas4show();
        //+1加上最左边的课程号
        GridLayoutManager manager = new GridLayoutManager(context, TIMES * dayInWeek + 1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Data4RvItem data = datas.get(position);
                switch (data.getType()) {
                    case TYPE_COURSE_INDEX:
                        return 1;//index这个item的宽度的整个宽度的1/TIMES*daysInWeek+1
                    case TYPE_COURSE:
                        return TIMES;
                }
                return 0;
            }
        });
        scheduleContentRV.setLayoutManager(manager);
        scheduleContentRV.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(1, 1, 1, 1);
            }

        });
        scheduleContentRV.setAdapter(new ScheduleContentRvAdapter(context
                , mScheduleBean, dayInWeek));
    }
```

fillData(...)方法中的实现：
```
    public void fillData(int[] startYMD, int daysInWeek, List<CourseBean> courses) {
        if (startYMD.length != 3)
            throw new RuntimeException("参数startYMD：   本周起始年月日-->数组长度为3，分别为year,month,day，传入参数异常");
        if (daysInWeek < 0 || daysInWeek > 7)
            throw new RuntimeException("daysInWeek参数异常\nFunction fillData(List,int) got an incorrect param(daysInWeek)");
        this.daysInWeek = daysInWeek;
*       ScheduleBean mScheduleBean = new ScheduleBean(courses, daysInWeek);
        initHeader(startYMD, daysInWeek);
        initRecyclerView(mScheduleBean, daysInWeek);

    }
```
在ScheduleBean的构造函数中对List<CourseBean>进行了操作，转换成了List<Data4RvItem>
```
/**
 * Created by GG on 2016/11/22.
 * Email:gu.yuepeng@foxmail.com
 * <p>
 * 课程表数据bean
 */
public class ScheduleBean {
    /**
     * 传进来的本周课程列表，要求横向添加CourseBean
     * 即周一的1,2节课，周二的1,2节……周五的1,2节；
     * 周一的3,4节，周二的3,4节……
     */
    private List<CourseBean> courses;
    /**
     * 课程表每周显示几天
     */
    private int dayInWeek;

    /**
     * 实际填充在recyclerview中的数据源
     * 包含已经处理好的index item
     */
    private List<Data4RvItem> datas4show;

    public ScheduleBean(List<CourseBean> courses, int dayInWeek) {
        this.courses = courses;
        this.dayInWeek = dayInWeek;
        prepareData4show();
    }



    public List<Data4RvItem> getDatas4show() {
        if (datas4show == null)
            Log.e(TAG, "getDatas4show: datas4show==null");
        return datas4show;
    }

    /**
     * 将index和课程整合到一起
     * 准备用于填充布局的数据源
     */
    private void prepareData4show() {
        datas4show = new ArrayList();
        for (int i = 0; i < courses.size(); i++) {
            //每次应该添加周一的课程之前先将view左侧的课程节数添加进去
            if (i % dayInWeek == 0) {
                datas4show.add(new Data4RvItem(i / dayInWeek));
            }
            datas4show.add(new Data4RvItem(courses.get(i)));
        }
    }
}
```
Adapter中的通过处理好的List<Data4RvItem>中的数据type来区分item的类型：
```

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case TYPE_COURSE_INDEX:
                mView = LayoutInflater.from(context)
                        .inflate(R.layout.item_schedule_rv_course_index, null);
                mView.setClickable(false);//作为index的item不可接受点击事件
                return new IndexViewHolder(context,mView);
            case TYPE_COURSE:
                mView = LayoutInflater.from(context)
                        .inflate(R.layout.item_schedule_rv_course, null);

                return new CourseViewHolder(context,mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Data4RvItem mData = datas4show.get(position);
        //此处不做处理，交给各自的Holder做处理
        holder.fillData(mData);
    }

    @Override
    public int getItemViewType(int position) {
        return datas4show.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return datas4show.size();
    }
```
BaseViewHolder:
```
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected View root;
    protected Context context;

    public BaseViewHolder(Context context,View itemView) {
        super(itemView);
        this.context=context;
        root=itemView;
    }

    /**
     * 在此方法中将根据各自类型将传入的data填充到布局中
     * @param data4RvItem
     */
    public abstract void fillData(Data4RvItem data4RvItem);
}
```
###参数说明
```
    public void fillData(int[] startYMD, int daysInWeek, List<CourseBean> courses) 
```
- startYMD:一个length为3的int数组，用来指定课程表的周一对应的日期，数组中分别应该是year,month,day.
- daysInWeek:Header中预期显示到周几，即一周有几天
- courses:课程表信息，即使没有课也需要占位，courses应和daysInWeek对应，以便后续操作转换成Data4RvItem

###总结
- 中间曾想过使用StaggeredGridLayoutManager实现一个横向的瀑布流效果，最终还是用GridLayoutManager来做的，写完之后感觉还不错,不过bug应该不少，待发掘
- 感觉使用RecyclerView来做整个层次结构简单清晰了很多

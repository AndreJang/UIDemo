#本文参考一位薄荷程序媛[Kay Wu的博客](http://kaywu.github.io/2015/04/03/DoubanDemo/)
#一.先看看初次模仿的UI效果图，整个练习的代码在[github](https://github.com/AndreJang/UIDemo)
![](http://upload-images.jianshu.io/upload_images/1529182-90699b51fc70d2cc.gif?imageMogr2/auto-orient/strip)

#二.通过这次简单的UI模仿练习可以学习到哪些技术点的简单使用
 - 1.ViewPager
 - 2.PagerSlidingTabStrip
 - 3.SwipeRefreshLayout

#三.先来看看项目的结构：
![](http://upload-images.jianshu.io/upload_images/1529182-1af01b8ddd018cac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#四.来看看这技术点的简单介绍
--ViewPager
　ViewPager是v4包里提供的，可以通过PagerAdapter根据左右的滑动产生不同的page。PagerAdapter分为FragmentPagerAdapter, FragmentStatePagerAdapter。
　如何选择PagerAdapter呢？FragmentPagerAdapter：在同级屏幕(sibling screen)只有少量的几个固定页面时， 使用这个最好。FragmentStatePagerAdapter：当根据对象集的数量来划分页面， 即一开始页面的数量未确定时， 使用这个最好。 当用户切换到其他页面时， fragment会被销毁来降低内存消耗。
　ViewPager的使用方法类似ListView, 都是使用Adapter来提供内容。而ListView中getItem( )返回的是View对象, 而PagerAdapter中的getItem( )返回的是Fragment对象。


--PagerSlidingTabStrip
　这是一个与ViewPager的各页面进行交互的指示器，github在[这里](https://github.com/astuetz/PagerSlidingTabStrip)

--SwipeRefreshLayout
　SwipeRefreshLayout是在v4包里的，它提供了下拉刷新的功能以及对应的动画效果，使用起来非常简便。但是其中只能包含一个View，且该View必须是可滑动的（不可滑动的话动画显示有bug），如ListView。

#五.制作这个简单UI的执行顺序
　在MainActivity中搞一个ViewPager出来，然后在ViewPager中第一个page里面的Fragment中添加一个ListView，最后两页都是Fragment里面嵌套着一个TextView。在MainActivity中套上PagerSlidingTabStrip作为一个Tab。最后在ViewPager中第一个page里面的Fragment（带有一个ListView）套上SwipeRefreshLayout来做一个下拉刷新。

#六.逐个模块来实现
- 1先在MainActivity弄个ViewPager：
　MainActivity中的代码
```java
private static int PAGE_NUM = 3;
private ViewPager mViewPager;
@Overrideprotected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);
    mViewPager = (ViewPager) findViewById(R.id.viewPager);   
}
```

　因为ViewPager需要PagerAdapter来做适配器，所以这步需要自定义一个PagerAdapter，上面说过了，FragmentPagerAdapter适合少量的几个固定页面，所以这里的PagerAdapter就选择继承FragmentPagerAdapter。
```java
private class SlidePageAdapter extends FragmentPagerAdapter{
    public SlidePageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                return fragment = new ListRefreshFragment();
            case 1:
                return fragment = new Fragment2();
            case 2:
                return fragment = new Fragment3();
            default:
                return new Fragment();
        }
    }
    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
```
于是乎就可以在onCreate( )中添加自定义的SlidePageAdapter了。
```java
private ViewPager mViewPager;
@Overrideprotected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);
    mViewPager = (ViewPager) findViewById(R.id.viewPager);
    mViewPager.setAdapter(new SlidePageAdapter(getSupportFragmentManager()));
}
```
- 2实现各个ViewPager页面上的Fragment
带有ListView功能并且带有下拉刷新的ListRefreshFragment
```java
private ListView lv;
private TextView textView;
private SwipeRefreshLayout mRefreshLayout;
@Nullable@Overridepublic View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.list_fragment,container,false);
    lv = (ListView) rootView.findViewById(R.id.lv);
    lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,initDatas()));
    
    return rootView;
}
private List<String> initDatas() {
    List<String> datas = new ArrayList<>();
    for (int i = 0; i < 20; i++){
        datas.add(" " + i);
    }
    return datas;
}
```
　剩下的2个Fragment基本一样
```java
public class Fragment2 extends Fragment {
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2,container,false);
        textView = (TextView) rootView.findViewById(R.id.tv);
        textView.setText("2");
        return rootView;
    }
}
```
- 3使用PagerSlidingTabStrip在MainActivity中添加头部的页面指示器，此时要复写FragmentPagerAdapter的getPageTitle( )方法，否则程序会崩溃
```java
private PagerSlidingTabStrip tabs;
@Overrideprotected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);
    mViewPager = (ViewPager) findViewById(R.id.viewPager);
    mViewPager.setAdapter(new SlidePageAdapter(getSupportFragmentManager()));
    tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
    tabs.setViewPager(mViewPager);
}
private class SlidePageAdapter extends FragmentPagerAdapter{
     private final String[] TITLES = getResources().getStringArray(R.array.title_array);
    public SlidePageAdapter(FragmentManager fm) {
        super(fm);
    }
     @Override
     public CharSequence getPageTitle(int position) {
         return TITLES[position];
     }
     @Override
    public Fragment getItem(int position) {
      ...........
    }
    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
```
- 4使用SwipeRefreshLayout来为首页添加一个下拉刷新功能
```java
private SwipeRefreshLayout mRefreshLayout;
private static final int REFRESH_TIME = 3000;
@Nullable
@Overridepublic
 View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    
    View rootView = inflater.inflate(R.layout.list_fragment,container,false);
    lv = (ListView) rootView.findViewById(R.id.lv);
    lv.setAdapter(new ArrayAdapter<String>getActivity(),android.R.layout.simple_list_item_1,initDatas()));    
    mRefreshLayout = (SwipeRefreshLayout) (SwipeRefreshLayout) rootView.findViewById(R.id.refrsh_layout);
    mRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_red_light
            );
    mRefreshLayout.setOnRefreshListener(this);
    return rootView;
}
@Override
public void onRefresh() {
    mRefreshLayout.setRefreshing(true);
    new Handler().postDelayed(new Runnable() {
       @Override
       public void run() {
           mRefreshLayout.setRefreshing(false);
           Toast.makeText(getContext(), "已完成刷新!", Toast.LENGTH_SHORT).show();
       }
   },REFRESH_TIME);
}
```

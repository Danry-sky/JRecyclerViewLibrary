[![](https://jitpack.io/v/WuXuBaiYang/JRecyclerViewLibrary.svg)](https://jitpack.io/#WuXuBaiYang/JRecyclerViewLibrary)
# JRecyclerView
JRecyclerView是一个继承自Recyclerview的扩展类库，简化了viewholder的用法，增加了滑动到底部加载更多，一行代码开启item拖动滑动以及适配器中的数据操作。堪称是居家旅行，开发必备之良品，当然，这个项目很久之前就开始开发了，一直拖到2017年初才开始写readme，实在是够懒！
#集成方法
step1：在根build.gradle中加入

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
step2：在项目中加入

	dependencies {
	        compile 'com.github.WuXuBaiYang:JRecyclerViewLibrary:1.7.3'
	}
#下拉刷新&加载更多
该项目并没有将下拉刷新与加载更多集成一体，也没有做成overscroll的效果，所以需要同时使用这两种功能的小伙伴需要使用这种嵌套的方式去实现
，当然，也可以拆开使用
```xml
    <com.jtech.view.RefreshLayout
        android:id="@+id/refreshlayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.jtech.view.JRecyclerView
            android:id="@+id/jrecyclerview"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </com.jtech.view.RefreshLayout>
```
##下拉刷新
copy了官方的SwipeRefreshLayout代码修复了其中的bug（官方代码中出现的重复下拉的bug已经修复，并不会出现）并且加入了新的功能
###主动发起下拉刷新并设置监听
```java
refreshLayout.startRefreshing();
refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                
            }
        });
```
###刷新完成时调用的状态还原方法
```java
refreshLayout.refreshingComplete();
```
##滑动到底部加载更多
该方法通过嵌套适配器的方式实现了一个footer，通用于三种布局，并且footer可以实现自定义效果
###开启加载更多并且设置监听
```java
jRecyclerView.setLoadMore(true);
jRecyclerView.setOnLoadListener(new OnLoadListener() {
            @Override
            public void loadMore() {

            }
        });
```
###自定义footer
自定义footer需要在设置适配器的同时传递进去，通过适配器的方式完成(simpleloadfooteradapter是默认的footer适配器实现，不传递的时候就会调用他)
```java
SimpleLoadFooterAdapter simpleLoadFooterAdapter = new SimpleLoadFooterAdapter(context);
jRecyclerView.setAdapter(testAdapter, simpleLoadFooterAdapter);
```
###自定义footer适配器实现，继承自LoadFooterAdapter
```java
public class SimpleLoadFooterAdapter extends LoadFooterAdapter {
    
    @Override
    public View getFooterView(LayoutInflater layoutInflater, ViewGroup parent) {
        //获取footer的视图
        return null;
    }
    
    @Override
    public void loadFailState(RecyclerHolder recyclerHolder) {
        //加载失败状态，需要用户调用jRecyclerView.setLoadFailState();
    }
    
    @Override
    public void loadingState(RecyclerHolder recyclerHolder) {
        //加载中状态
    }
    
    @Override
    public void noMoreDataState(RecyclerHolder recyclerHolder) {
        //无更多数据状态，需要用户调用jRecyclerView.setLoadFinishState();
    }
    
    @Override
    public void normalState(RecyclerHolder recyclerHolder) {
        //默认状态，无动作
    }
}
```
##item点击事件（item中无拦截的时候生效）
与listview的使用方法相同，只是返回的参数不同了
```java
jRecyclerView.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(RecyclerHolder holder, View view, int position) {
        
    }
});
```
##item长点击事件(item中无拦截的时候生效，不过与下面要说的长点击拖动换位功能可能会出现冲突，需要自己抉择)
```java
jRecyclerView.setOnItemLongClickListener(new OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(RecyclerHolder holder, View view, int position) {
        return false;
    }
});
```
##长点击拖动或者拖动换位
```java
//longPressDragEnabled为是否开启长点击拖动换位，也可以用户手动触发该功能；dragFlags为动作标记，onItemViewMoveListener为监听
//最底层的拖动方法，一般情况下可以不使用
jRecyclerView.setMove(longPressDragEnabled, dragFlags, onItemViewMoveListener);
//自由拖动，上下左右皆可拖动
jRecyclerView.setMoveFree(longPressDragEnabled, onItemViewMoveListener);
//左右拖动，顾名思义
jRecyclerView.setMoveLeftRight(longPressDragEnabled, onItemViewMoveListener);
//上♂下♀拖动，千万别乱想
jRecyclerView.setMoveUpDown(longPressDragEnabled, onItemViewMoveListener);
//也可以单独设置监听,上面几个方法可以传null
jRecyclerView.setOnItemViewMoveListener(onItemViewMoveListener);
```
###手动触发拖动监听事件
比如点击item中的某个图标实现拖动换位的功能,需要传递当前item的viewholder
```java
jRecyclerView.startDrag(viewHolder);
```
##滑动删除或归档
```java
//swipeEnabled为是否开启滑动，也可以用户手动触发该功能；swipeFlags为动作标记，onItemViewSwipeListener为监听
//最底层的滑动方法，一般情况下可以不使用
jRecyclerView.setSwipe(swipeEnabled,swipeFlags,onItemViewSwipeListener);
//自由滑动
jRecyclerView.setSwipeFree(swipeEnabled,onItemViewSwipeListener);
//从左到右滑动
jRecyclerView.setSwipeStart(swipeEnabled,onItemViewSwipeListener);
//从右到左滑动
jRecyclerView.setSwipeEnd(swipeEnabled,onItemViewSwipeListener);
//依然可以单独设置监听,上面方法设置为null
jRecyclerView.setOnItemViewSwipeListener(onItemViewSwipeListener);
```
###手动触发滑动
并不知道为什么要搞这个功能，可能是为了对称？至少我是没发现使用场景
```java
jRecyclerView.startSwipe(viewHolder);
```

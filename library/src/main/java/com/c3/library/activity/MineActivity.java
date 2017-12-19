package com.c3.library.activity;

import android.content.Intent;
import android.support.annotation.LayoutRes;

import com.c3.library.R;
import com.c3.library.constant.SceneType;
import com.c3.library.view.title.CustomBodyView;
import com.c3.library.view.title.CustomTitleView;
import com.c3.library.view.title.IsTitleView;

import kotlin.TuplesKt;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 作者： LYJ
 * 功能： Activity不滑动的基类
 * 创建日期： 2017/11/7
 */

public abstract class MineActivity extends BaseActivity {
    private int theSceneType;//设置默认的进出场动画类型
    protected IsTitleView mTitleView;//标题栏
    private CustomBodyView.TitleShowType titleShowType;//标题栏摆放类型
    private int titleBarHeight;//标题栏高度
    /**
     * 设置Fragment进出场动画
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 设置入场动画
     * @param intent
     */
    protected final void startActivity(Intent intent,int sceneType) {
        startActivity(intent);
        setSceneType(sceneType);
    }
    /**
     * 设置入场动画
     * @param intent
     */
    protected final void startActivityForResult(Intent intent, int requestCode,int sceneType) {
        startActivityForResult(intent,requestCode);
        setSceneType(sceneType);
    }

    /**
     * 设置布局
     * @param bodyID
     */
    protected void setBodyView(@LayoutRes int bodyID){
        CustomBodyView bodyView = new CustomBodyView(this);
        mTitleView = setTitleBarView();//设置标题栏
        bodyView.initBodyView(bodyID)//添加布局
                .setTitleShowType(titleShowType = setTitleBarShowType())//默认是添加标题栏的
                .initTitleView(mTitleView, titleBarHeight = setTitleBarViewHeight())//添加标题栏
                .combination();//组合
        setContentView(bodyView);//设置布局
        theSceneType = setTheSceneType();
        initSetting();//初始化设置
    }

    protected abstract void initSetting();

    /**
     * 设置默认的过场动画
     * @return
     */
    protected int setTheSceneType() {
        return SceneType.DEFAULT_TYPE;
    }

    /**
     * 设置标题栏显示效果
     * @return
     */
    protected CustomBodyView.TitleShowType setTitleBarShowType() {
        return CustomBodyView.TitleShowType.ARRANGE;//默认布局效果
    }

    /**
     * 设置标题栏高度
     * @return
     */
    protected Integer setTitleBarViewHeight() {
        return getResources().getDimensionPixelSize(R.dimen.dp_43);//默认标题栏高度
    }

    /**
     * 设置标题栏
     * @return
     */
    public IsTitleView setTitleBarView() {
        return new CustomTitleView(this);//默认标题栏效果
    }

    /**
     * 修改进出场动画
     * @param sceneType
     */
    protected final void changeSceneType(int sceneType) {
        this.theSceneType = sceneType;
    }

    /**
     * 设置出场动画
     */
    @Override
    public void finish() {
        super.finish();
        if (afterFinishAnimation()){
            setSceneType(theSceneType);
        }
    }

    /**
     * 是否开启退出动画
     * @return
     */
    protected boolean afterFinishAnimation(){
        return true;
    }
    /**
     * 设置进出场动画
     * @param sceneType
     */
    public void setSceneType(int sceneType){
        if (sceneType == SceneType.CUSTOM_TYPE){
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }else if (sceneType == SceneType.DEFAULT_TYPE){
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }else {
            overridePendingTransition(0, 0);
        }
    }
    /**
     * 获取标题栏的高度
     * @return
     */
    public int getTitleBarHeight() {
        return titleBarHeight;
    }
}

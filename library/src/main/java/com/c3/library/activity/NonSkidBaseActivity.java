package com.c3.library.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.c3.library.R;
import com.c3.library.constant.SceneType;
import com.c3.library.view.CustomBodyView;
import com.c3.library.view.CustomTitleView;
import com.c3.library.view.IsTitleView;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 作者： LYJ
 * 功能： Activity不滑动的基类
 * 创建日期： 2017/11/7
 */

public class NonSkidBaseActivity extends SupportActivity {
    private int theSceneType;//设置默认的进出场动画类型
    protected IsTitleView mTitleView;//标题栏
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
                .initTitleView(mTitleView, setTitleBarViewHeight())//添加标题栏
                .combination(setTitleBarShowType());//默认是添加标题栏的

        mTitleView.getSelf().setBackgroundColor(Color.YELLOW);
        TextView testText = new TextView(this);
        testText.setText("测试");
        testText.setTextColor(Color.RED);
        mTitleView.initCenterView(testText);


        setContentView(bodyView);//设置布局
        theSceneType = setTheSceneType();
    }

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
        return getResources().getDimensionPixelSize(R.dimen.dp_46);//默认标题栏高度
    }

    /**
     * 设置标题栏
     * @return
     */
    protected IsTitleView setTitleBarView() {
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
        setSceneType(theSceneType);
    }


    /**
     * 设置进出场动画
     * @param sceneType
     */
    private void setSceneType(int sceneType){
        if (sceneType == SceneType.CUSTOM_TYPE){
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }else {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }
}

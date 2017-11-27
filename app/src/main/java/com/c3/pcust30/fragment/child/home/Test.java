package com.c3.pcust30.fragment.child.home;

import android.os.Bundle;

import com.c3.pcust30.base.frag.TopFragment;
import com.c3.pcust30.http.tool.TradingTool;

import org.jetbrains.annotations.Nullable;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * 作者： LYJ
 * 功能：
 * 创建日期： 2017/11/27
 */

public class Test extends TopFragment{


    @Override
    public int setFragmentView() {
        return 0;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.zip(TradingTool.Companion.commitTrading("1"), TradingTool.Companion.commitTrading("2"),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        return s + s2;
                    }
                });
    }

}

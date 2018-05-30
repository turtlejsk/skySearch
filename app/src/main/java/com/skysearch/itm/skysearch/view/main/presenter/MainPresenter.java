package com.skysearch.itm.skysearch.view.main.presenter;


import android.content.Context;

import com.skysearch.itm.skysearch.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by tae-hwan on 12/22/16.
 */

public class MainPresenter implements MainContract.Presenter, OnItemClickListener {

    private MainContract.View view;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }



    @Override
    public void loadItems(Context context, final boolean isClear) {


    }


    @Override
    public void onItemClick(int position) {

    }
}
